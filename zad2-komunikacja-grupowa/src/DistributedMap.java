import org.jgroups.*;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.Protocol;
import org.jgroups.util.Util;

import java.io.*;
import java.net.InetAddress;
import java.util.*;

import static org.jgroups.util.Util.close;

public class DistributedMap extends ReceiverAdapter implements SimpleStringMap
{
    private JChannel channel;
    private final Map<String, Integer> map = new HashMap<>();

    public DistributedMap() throws Exception
    {
        System.setProperty("java.net.preferIPv4Stack", "true");
        channel = new JChannel(getProtocolStack());
        channel.setReceiver(this);
        channel.connect("channelNameDifferentThanOthers");
        channel.getState(null, 10000);
    }

    public boolean containsKey(String key)
    {
        return map.containsKey(key);
    }

    public Integer get(String key)
    {
        return map.get(key);
    }

    public void put(String key, Integer value)
    {
        Operation op = new Operation(key, value);
        Message msg = new Message(null, op);
        try
        {
            channel.send(msg);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        map.put(key, value);
    }

    public Integer remove(String key)
    {
        Operation op = new Operation(key);
        Message msg = new Message(null, op);
        try
        {
            channel.send(msg);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return map.remove(key);
    }

    public void closeMap()
    {
        close(channel);
    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
        System.out.print("> "); System.out.flush();
        handleView(channel, new_view);
    }

    @Override
    public void receive(Message msg)
    {
        Operation op = msg.getObject();

        synchronized(map) {
            if (op.getType() == Operation.OperationType.ADD)
            {
                map.put(op.getKey(), op.getValue());
            }
            else if(op.getType() == Operation.OperationType.REMOVE)
            {
                map.remove(op.getKey());
            }
        }
    }

    @Override
    public void setState(InputStream input) throws Exception
    {
        Map<String, Integer> map;
        map =  Util.objectFromStream(new DataInputStream(input));
        synchronized(this.map) {
            this.map.clear();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                this.map.put((String) pair.getKey(), (Integer) pair.getValue());
                it.remove();
            }
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception
    {
        synchronized (map)
        {
            Util.objectToStream(map, new DataOutputStream(output));
        }
    }

    @Override
    public String toString()
    {
        String string = "MAP: ";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            string += (pair.getKey() + "->" + pair.getValue() + " ; ");
        }
        return string;
    }

    private Protocol[] getProtocolStack() throws Exception
    {
        Protocol[] prot_stack = {
                new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.100.200.163")),
                new PING(),
                new MERGE3(),
                new FD_SOCK(),
                new FD_ALL(),
                new VERIFY_SUSPECT(),
                new BARRIER(),
                new NAKACK2(),
                new UNICAST3(),
                new STABLE(),
                new GMS(),
                new UFC(),
                new MFC(),
                new FRAG2(),
                new STATE_TRANSFER()};

        return prot_stack;
    }

    private static void handleView(JChannel ch, View new_view)
    {
        if(new_view instanceof MergeView)
        {
            ViewHandler handler = new ViewHandler(ch, (MergeView)new_view);
            // requires separate thread as we don't want to block JGroups
            handler.start();
        }
    }

    private static class ViewHandler extends Thread
    {
        JChannel ch;
        MergeView view;

        private ViewHandler(JChannel ch, MergeView view)
        {
            this.ch = ch;
            this.view = view;
        }

        public void run()
        {
            List<View> subgroups = view.getSubgroups();
            View tmp_view = subgroups.get(0);
            Address local_addr = ch.getAddress();
            if (!tmp_view.getMembers().contains(local_addr))
            {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will re-acquire the state");
                try
                {
                    ch.getState(null, 30000);
                } catch (Exception ignored) { }
            }
            else
            {
                System.out.println("Member of the new primary partition ("
                        + tmp_view + "), will do nothing");
            }
        }
    }
}

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.zookeeper.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executor;

public class Main
{
    private static Process process = null;
    private static String watchedZnode = "/z";

    public static void main(String[] args) throws Exception
    {
        ZooKeeper zoo;
        ZKConnection connection = new ZKConnection();
        zoo = connection.connect("127.0.0.1:2181");
//        zoo.create("/b", "abc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        Watcher childrenWatcher = new Watcher() {
            @Override
            public void process(WatchedEvent we) {
                if (we.getType() == Event.EventType.NodeChildrenChanged){
                    try
                    {
                        System.out.println(watchedZnode + " children count: " + zoo.getChildren(watchedZnode, false).size());
                    }catch (Exception ignored) {}
                }
                try
                {
                    zoo.getChildren(watchedZnode, this);
                }catch (Exception ignored) {}
            }
        };

        Watcher createWatcher = new Watcher() {
            public void process(WatchedEvent we) {
                if (we.getType() == Event.EventType.NodeCreated)
                {
                    open_app();
                    try
                    {
                        zoo.getChildren(watchedZnode, childrenWatcher);
                    } catch (Exception ignored) {}
                } else if (we.getType() == Event.EventType.NodeDeleted)
                {
                    close_app();
                }
                try {
                    zoo.exists(watchedZnode, this);
                } catch (Exception ignored) {}
            }
        };

        zoo.exists(watchedZnode, createWatcher);
        try {
            zoo.getChildren(watchedZnode, childrenWatcher);
        } catch (Exception ignored) {}

        while(true)
        {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("q"))
            {
                break;
            } else if (input.equals("ls"))
            {
                print_structure(zoo, "/", "");
            }
        }
    }

    private static void open_app()
    {
        File file = new File("C:\\Program Files\\Notepad++\\notepad++.exe");
        try {
            process = Runtime.getRuntime().exec("C:\\Program Files\\Notepad++\\notepad++.exe");
        } catch (IOException ignored) {}
    }

    private static void close_app()
    {
        if (process != null)
        {
            process.destroy();
        }
        process = null;
    }

    private static void print_structure(ZooKeeper zoo, String path, String prefix) throws Exception
    {
        for (String znode: zoo.getChildren(path, false))
        {
            System.out.println(prefix + znode);
            if (path.equals("/"))
                print_structure(zoo, path + znode, prefix + "|  ");
            else
                print_structure(zoo, path + "/" + znode, prefix + "|  ");
        }
    }
}

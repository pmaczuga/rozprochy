package client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import api.Find;
import api.Order;
import api.StreamRequest;

public class ClientActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String serverSystem = "server_system";
    private String serverPort = "3552";
    private String serverName = "server";
    private String serverPath = String.format("akka.tcp://%s@localhost:%s/user/%s", serverSystem, serverPort, serverName);

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s -> {
                    System.out.println(s);
                })
                .match(Request.class, r -> {
                    String[] parts = r.getMessage().split("\\s+", 2);
                    if (parts[0].equals("find"))
                    {
                        getContext().actorSelection(serverPath).tell(new Find(parts[1]), getSelf());
                    }
                    else if (parts[0].equals("order"))
                    {
                        getContext().actorSelection(serverPath).tell(new Order(parts[1]), getSelf());
                    }
                    else if (parts[0].equals("stream"))
                    {
                        getContext().actorSelection(serverPath).tell(new StreamRequest(parts[1]), getSelf());
                    }
                })
                .matchAny(o -> {
                    log.info("Unknown message of type " + o.getClass());
                })
                .build();
    }
}

package server.database;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.BackoffSupervisor;
import scala.concurrent.duration.Duration;

import java.io.IOException;

public class DatabaseMainActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final String database1 = "resources/database1.txt";
    private final String database2 = "resources/database2.txt";

    private ActorRef client;
    private String titleToFind;
    private int notFoundCount = 0;
    private final int DATABASE_COUNT = 2;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s-> {
                    client = getSender();
                    titleToFind = s;
                    context().child("database1_actor").get().tell(s, getSelf());
                    context().child("database2_actor").get().tell(s, getSelf());
                })
                .match(Book.class, b-> {
                    client.tell(b, null);
                    context().stop(getSelf());
                })
                .match(NotFound.class, n -> {
                    notFoundCount++;
                    if (notFoundCount == DATABASE_COUNT)
                    {
                        client.tell(n, null);
                        context().stop(getSelf());
                    }
                })
                .match(ResendRequest.class, rr -> {
                    getSender().tell(titleToFind, getSelf());
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(DatabaseActor.class, database1), "database1_actor");
        context().actorOf(Props.create(DatabaseActor.class, database2), "database2_actor");
    }

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(IOException.class, e -> SupervisorStrategy.restart())
            .matchAny(o -> SupervisorStrategy.resume())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}

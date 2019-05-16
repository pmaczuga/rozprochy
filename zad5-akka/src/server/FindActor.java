package server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import server.database.Book;
import server.database.DatabaseActor;
import server.database.DatabaseMainActor;
import server.database.NotFound;

public class FindActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef client;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s -> {
                    client = getSender();
                    context().actorOf(Props.create(DatabaseMainActor.class)).tell(s, getSelf());
                })
                .match(Book.class, b -> {
                    client.tell("Found book. Price: " + b.getPrice(), getSelf());
                    context().stop(getSelf());
                })
                .match(NotFound.class, n -> {
                    client.tell("Book not found.", getSelf());
                    context().stop(getSelf());
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }
}

package server;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;
import server.database.Book;
import server.database.DatabaseMainActor;
import server.database.NotFound;

public class OrderActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef client;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s-> {
                    client = getSender();
                    context().actorOf(Props.create(DatabaseMainActor.class)).tell(s, getSelf());
                })
                .match(Book.class, b -> {
                    context().actorOf(Props.create(OrderWriterActor.class)).tell(b.getTitle(), getSelf());
                })
                .match(NotFound.class, n -> {
                    client.tell("Book not found.", getSelf());
                    context().stop(getSelf());
                })
                .match(Confirmation.class, c -> {
                    client.tell("Order confirmed.", getSelf());
                    context().stop(getSelf());
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
                .matchAny(o -> {
                    client.tell("Problem ordering", getSelf());
                    getContext().stop(getSelf());
                    return SupervisorStrategy.stop();
                })
                .build());
    }
}

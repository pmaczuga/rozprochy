package server;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import api.Find;
import api.Order;
import api.StreamRequest;

public class ServerActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Find.class, f -> {
                    context().actorOf(Props.create(FindActor.class)).tell(f.getTitle(), getSender());
                })
                .match(Order.class, o -> {
                    context().actorOf(Props.create(OrderActor.class)).tell(o.getTitle(), getSender());
                })
                .match(StreamRequest.class, sr -> {
                    context().actorOf(Props.create(StreamActor.class)).tell(sr.getTitle(), getSender());
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }
}

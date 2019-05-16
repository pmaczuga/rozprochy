import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import server.database.DatabaseActor;
import server.database.DatabaseMainActor;

public class TestActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s -> {
                    context().actorOf(Props.create(DatabaseMainActor.class)).tell(s, getSelf());
                })
                .matchAny(o -> System.out.println(o))
                .build();
    }
}

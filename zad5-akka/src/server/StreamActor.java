package server;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.function.Creator;
import akka.japi.pf.ReceiveBuilder;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Flow;
import akka.util.ByteString;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.concurrent.duration.FiniteDuration;
import server.database.NotFound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class StreamActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s -> {
                    final Materializer materializer = ActorMaterializer.create(getContext());

                    try {
                        Stream<String> stream = Files.lines(Paths.get("resources/" + s + ".txt"));
                        Source<String, NotUsed> source = Source.fromIterator((Creator<Iterator<String>>) () -> stream.iterator());
                        Sink<String, NotUsed> sink = Sink.actorRef(getSender(), "END OF STREAM!");

                        source
                                .throttle(1, new FiniteDuration(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
                                .runWith(sink, materializer);
                    } catch (IOException e) {
                        getSender().tell("Book not found.", getSelf());
                    }
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }
}

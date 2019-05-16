package server;

import akka.actor.AbstractActor;
import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;
import server.database.Book;
import server.database.DatabaseActor;
import server.database.NotFound;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderWriterActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final String filename = "resources/orders.txt";

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s-> {
                    writeToFile(s);
                    getSender().tell(new Confirmation(), getSelf());
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }

    private void writeToFile(String s) throws IOException
    {
        PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
        writer.println(s);
        writer.close();
    }
}

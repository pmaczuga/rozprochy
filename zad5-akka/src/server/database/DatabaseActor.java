package server.database;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.Option;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseActor extends AbstractActor
{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String databaseName;

    public DatabaseActor(String databaseName)
    {
        this.databaseName = databaseName;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, s-> {
                    Book result = searchDatabase(s);
                    if (result != null)
                        getSender().tell(result, getSelf());
                    else
                        getSender().tell(new NotFound(), getSelf());
                })
                .matchAny(o -> log.info("Unknown message"))
                .build();
    }

    private Book searchDatabase(String title) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(databaseName));
        String line = reader.readLine();
        while(line != null)
        {
            String[] parts = line.split(";");
            if (parts[0].equals(title))
            {
                reader.close();
                return new Book(title, Integer.parseInt(parts[1]));
            }
            line = reader.readLine();
        }
        reader.close();
        return null;
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Thread.sleep(500);
        getContext().getParent().tell(new ResendRequest(), getSelf());
    }
}

package client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ClientApp
{
    public static void main(String[] args) throws Exception
    {
        File configFile = new File("resources/client.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("client_system", config);
        final ActorRef actor = system.actorOf(Props.create(ClientActor.class), "client");
        System.out.println("Started. Commands:\n'find [book title]'\n'order [book title]'\n'stream [book title]'\n'q'");

        // read line & send to actor
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            actor.tell(new Request(line), null);     // send message to actor
        }

        // finish
        system.terminate();
    }
}

package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import client.Request;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ServerApp
{
    public static void main(String[] args) throws Exception
    {

        File configFile = new File("resources/server.conf");
        Config config = ConfigFactory.parseFile(configFile);

        // create actor system & actors
        final ActorSystem system = ActorSystem.create("server_system", config);
        final ActorRef actor = system.actorOf(Props.create(ServerActor.class), "server");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
//            actor.tell(new Request(line), null);
        }

        system.terminate();
    }
}

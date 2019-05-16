import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception
    {
        final ActorSystem system = ActorSystem.create("client_system");
        final ActorRef actor = system.actorOf(Props.create(TestActor.class), "test");

        // read line & send to actor
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            actor.tell(line, null);     // send message to actor
        }

        // finish
        system.terminate();
    }
}

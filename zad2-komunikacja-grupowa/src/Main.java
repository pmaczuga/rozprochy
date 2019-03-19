import org.jgroups.JChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Commands: \n" +
                "put key value\n" +
                "get key\n" +
                "remove key\n" +
                "contains key\n" +
                "print");

        DistributedMap map = new DistributedMap();

        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            System.out.print("> "); System.out.flush();
            String line = in.readLine().toLowerCase();
            if(line.startsWith("quit") || line.startsWith("exit"))
            {
                map.closeMap();
                break;
            }

            String[] parts = line.split("\\s+");
            String key;
            int value;
            switch (parts[0])
            {
                case "put":
                    if (Array.getLength(parts) < 3) { System.out.println("Wrong input"); break; }
                    key = parts[1];
                    value = Integer.parseInt(parts[2]);
                    map.put(key, value);
                    break;

                case "get":
                    if (Array.getLength(parts) < 2) { System.out.print("Wrong input"); break; }
                    key = parts[1];
                    System.out.println("Got: " + map.get(key));

                    break;

                case "remove":
                    if (Array.getLength(parts) < 2) { System.out.print("Wrong input"); break; }
                    key = parts[1];
                    System.out.println("Got: " + map.remove(key));

                    break;

                case "contains":
                    if (Array.getLength(parts) < 2) { System.out.print("Wrong input"); break; }
                    key = parts[1];
                    System.out.println("Got: " + map.containsKey(key));

                    break;

                case "print":
                    System.out.println(map.toString());

                    break;

                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }
}

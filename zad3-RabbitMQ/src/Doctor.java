import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Doctor
{
    public static List<String> types = Arrays.asList("hip", "knee", "elbow");

    public static void main(String[] argv) throws Exception
    {
        // info
        System.out.println("DOCTOR");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String EXCHANGE_NAME = "my_exchange";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String LOG_EXCHANGE_NAME = "log_exchange";
        channel.exchangeDeclare(LOG_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // Receive info from admin
        new InfoReceiver(channel).myConsume();

        // callback
        String callbackQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(callbackQueueName, EXCHANGE_NAME, callbackQueueName);
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .replyTo(callbackQueueName)
                .build();

        //consume (callback)
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("Received - " + message);
                channel.basicAck(deliveryTag, false);
            }
        };
        channel.basicConsume(callbackQueueName, false, "my_tag", consumer);

        System.out.println("Enter \"[type] [name]\"");
        System.out.println("\"exit\" to exit (sic!)");
        while (true)
        {
            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            String actualMessage;
            String type;
            try
            {
                actualMessage = getMessage(message);
                type = getType(message);
            } catch (RuntimeException e) {
                System.out.println("Wrong input!");
                continue;
            }

            // publish
            channel.basicPublish(EXCHANGE_NAME, type, props, actualMessage.getBytes("UTF-8"));
            channel.basicPublish(LOG_EXCHANGE_NAME, "", null, actualMessage.getBytes("UTF-8"));
            System.out.println("Sent: \"" + actualMessage + "\" to \"" + type + "\"engineer");
        }

        channel.close();
    }

    private static String getType(String msg)
    {
        String[] parts = msg.split("\\s+", 2);
        if (!types.contains(parts[0])) throw new RuntimeException();
        return parts[0];
    }

    private static String getMessage(String msg)
    {
        String[] parts = msg.split("\\s+", 2);
        if (parts.length < 2) throw new RuntimeException();
        return parts[1];
    }
}

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Admin
{
    public static void main(String[] argv) throws Exception
    {
        // info
        System.out.println("ADMIN");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String LOG_EXCHANGE_NAME = "log_exchange";
        channel.exchangeDeclare(LOG_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String INFO_EXCHANGE_NAME = "info_exchange";
        channel.exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, LOG_EXCHANGE_NAME, "");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                System.out.println("Log - " + message);
            }
        };
        channel.basicConsume(queue, false, consumer);

        while (true)
        {
            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            channel.basicPublish(INFO_EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        }
    }
}

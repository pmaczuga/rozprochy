import com.rabbitmq.client.*;

import java.io.IOException;

public class Engineer
{
    public static void main(String[] argv) throws Exception
    {
        // info
        if (argv.length != 2) { System.out.println("Required 2 arguments"); return; }
        System.out.println("ENGINEER");
        System.out.println("Specialist in: \"" + argv[0] + "\" and \"" + argv[1] + "\"\n");

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

        // queue & bind
        String QUEUE_NAME = "my_queue";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (String type: argv)
        {
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, type);
        }

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                String type = envelope.getRoutingKey();
                String sender = properties.getReplyTo();
                String formatMessage = "Name: \"" + message + "\" Type: \"" + type + "\"";
                String returnMessage = formatMessage + " - DONE!";
                System.out.println("Received - " + message + " : " + envelope.getRoutingKey());
                channel.basicPublish(EXCHANGE_NAME, sender, null, returnMessage.getBytes("UTF-8"));
                channel.basicPublish(LOG_EXCHANGE_NAME, "", null, returnMessage.getBytes("UTF-8"));
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
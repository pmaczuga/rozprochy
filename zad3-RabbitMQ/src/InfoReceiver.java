import com.rabbitmq.client.*;

import java.io.IOException;

public class InfoReceiver extends DefaultConsumer
{
    private String INFO_EXCHANGE_NAME = "info_exchange";

    InfoReceiver(Channel channel) throws Exception
    {
        super(channel);
        getChannel().exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    }

    public void myConsume() throws Exception
    {
        String infoQueue = getChannel().queueDeclare().getQueue();
        getChannel().queueBind(infoQueue, INFO_EXCHANGE_NAME, "");
        getChannel().basicConsume(infoQueue, false, this);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
    {
        String message = new String(body, "UTF-8");
        System.out.println("Info - " + message);
        long deliveryTag = envelope.getDeliveryTag();
        getChannel().basicAck(deliveryTag, false);
    }
}

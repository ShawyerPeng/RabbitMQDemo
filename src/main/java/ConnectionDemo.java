import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class ConnectionDemo {
    private final static String EXCHANGE_NAME = "mastering.rabbitmq";
    private final static String QUEUE_NAME ="mastering.rabbitmq";

    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        // 1. 分别设置
        // factory.setHost("localhost");
        // factory.setUsername("guest");
        // factory.setPassword("guest");
        // 2. 通过Uri设置
        factory.setUri("amqp://guest:guest@localhost");

        // Connection
        Connection conn = factory.newConnection();
        // Channel
        Channel channel = conn.createChannel();
        // Exchanges
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        // Queues (Bind the queue to the exchange without routing key)
        channel.queueBind(channel.queueDeclare().getQueue(), "mastering.rabbitmq","");

        // Publishing messages
        String message ="Hello Mastering RabbitMQ!";
        // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        // System.out.println("Following Message Sent: "+ message);

        for (int i = 0; i < 20; i++) {
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.concat(i+1+"").getBytes());
            System.out.println(" [x] Sent '" + message + (i + 1) + "'  " + (i + 1) + " times");
        }

        channel.close();
        conn.close();
    }

}

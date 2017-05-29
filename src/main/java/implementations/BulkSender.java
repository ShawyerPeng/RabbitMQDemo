package implementations;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BulkSender {
    private final static String EXCHANGE_NAME = "mastering_rabbitmq_bulk";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Message msg = new Message();
        msg.setFrom("John");
        msg.setTo("Nicky");
        msg.setHeader("Hello World");
        msg.setContent("Hello World Again");

        for(int i = 0; i < 5; i++) {
            msg.setMsgNo(i + 1);
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.toJSON().getBytes());
            System.out.println("Message is sent: " + msg.toString());
        }

        channel.close();
        connection.close();
    }
}
/**
bulk message sends a message to all of the clients; however, routed message sends messages to the group of clients that are defined.

In message broker terms, bulk message is defined as PubSub, which is the abbreviation of publish and subscribe.
Sender behaves like a publisher, which publishes messages to all the receivers that are called subscribers:
https://www.packtpub.com/graphics/9781783981526/graphics/B02099_09_08.jpg
**/
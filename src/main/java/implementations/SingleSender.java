package implementations;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SingleSender {
    private final static String QUEUE_NAME = "mastering_rabbitmq";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Message msg = new Message();
        msg.setFrom("John");
        msg.setTo("Nicky");
        msg.setHeader("Hello World");
        msg.setContent("Hello World Again");

        for(int i = 0; i < 5; i++) {
            msg.setMsgNo(i + 1);
            channel.basicPublish("", QUEUE_NAME, null, msg.toJSON().getBytes());
            System.out.println("---------" + msg.toJSON());
            System.out.println("Message is sent: " + msg.toString());
        }

        channel.close();
        connection.close();
    }
}

package implementations;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class GroupSender {
    private final static String EXCHANGE_NAME = "mastering_rabbitmq_group";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        Message msg = new Message();
        msg.setFrom("John");
        msg.setTo("Nicky");
        msg.setHeader("Hello World");
        msg.setContent("Hello World Again");

        for(int i = 0; i < 5; i++) {
            msg.setMsgNo(i + 1);
            channel.basicPublish(EXCHANGE_NAME, "*.business.*", null, msg.toJSON().getBytes());
            System.out.println("Message is sent: " + msg.toString());
        }

        channel.close();
        connection.close();
    }
}
/**
 Another important use case of our collaboration app is to send message to the specific group;
 for instance, we are a group of fans of a football team and our group manager would like to send a message to the related group.
 In RabbitMQ terms, we should use routed exchanges to send a group message.
 In routed exchanges, sender sends message to the specific exchange providing a topic, such as a group name.
 Then, all receivers within the same group are able to fetch the messages, as shown in the following screenshot:
 https://www.packtpub.com/graphics/9781783981526/graphics/B02099_09_07.jpg
 **/
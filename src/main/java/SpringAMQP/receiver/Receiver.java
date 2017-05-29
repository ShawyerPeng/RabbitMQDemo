package SpringAMQP.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

public class Receiver implements ChannelAwareMessageListener {

    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("A message is received : Receiver");
        String msgBody = new String(message.getBody());
        System.out.println("Message: " + msgBody);
    }
}

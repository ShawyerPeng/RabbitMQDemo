package SpringAMQP.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Sender {
    @Autowired
    private volatile AmqpTemplate amqpTemplate;

    /**
     * Sends new Message using AmqpTemplate
     */
    public void sendMessage(){
        amqpTemplate.convertAndSend("Hello World");
    }
}

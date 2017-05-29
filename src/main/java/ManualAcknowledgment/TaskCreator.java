package ManualAcknowledgment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import implementations.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class TaskCreator {
    private final static String QUEUE_NAME = "mastering_rabbitmq_task";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 2);

        Task task = new Task();
        task.setExpires(cal.getTime());
        task.setCommand("dd if=//dev//zero of=output.dat  bs=1024 count=1024000");
        System.out.println(task.toJSON());

        for(int i = 0; i < 5; i++) {
            channel.basicPublish("", QUEUE_NAME, null, task.toJSON().getBytes());
            System.out.println("Task Request is sent: " + task.toString());
        }

        channel.close();
        connection.close();
    }
}

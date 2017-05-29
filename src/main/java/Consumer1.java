import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    private static final String TASK_QUEUE_NAME = "mastering.rabbitmq";

    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("Worker1  Waiting for messages");

        //每次从队列获取的数量
        channel.basicQos(1);
        // 消息消费完成确认
        boolean autoAck = false;
        // Asynchronously receiving messages 异步接收
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)throws IOException {
                String msg = new String(body);
                System.out.println("Received Message：" + msg);
            }
        });
    }
    private static void doWork(String task) {
        try {
            Thread.sleep(500); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}

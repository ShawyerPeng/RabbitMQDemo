// package ManualAcknowledgment;
//
// import com.rabbitmq.client.Channel;
// import com.rabbitmq.client.Connection;
// import com.rabbitmq.client.ConnectionFactory;
// import com.rabbitmq.client.QueueingConsumer;
// import implementations.Task;
//
// import java.io.IOException;
// import java.util.concurrent.TimeoutException;
//
// public class TaskHandler {
//     private final static String QUEUE_NAME = "mastering_rabbitmq_task";
//
//     public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//         ConnectionFactory factory = new ConnectionFactory();
//         factory.setHost("localhost");
//
//         Connection connection = factory.newConnection();
//         Channel channel = connection.createChannel();
//
//         channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//
//         System.out.println("Waiting for the tasks.........");
//
//         QueueingConsumer consumer = new QueueingConsumer(channel);
//         channel.basicConsume(QUEUE_NAME, false, consumer);
//
//         while (true) {
//             QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//             String msg = new String(delivery.getBody());
//
//             System.out.println(msg);
//
//             Task task = Task.fromJSON(msg);
//
//             System.out.println("Received: " + task.toString());
//             TaskRunner.runTask(task);
//             System.out.println("Task is Done: " + task.toString());
//
//             // after finishing the execution of the task, channel acknowledges manually, which notifies the RabbitMQ Server
//             channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//     }
// }

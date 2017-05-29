package implementations;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class RPCServer {
    private final static String QUEUE_NAME = "mastering_rabbitmq_rpc";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        System.out.println(" [x] Waiting RPC requests");

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            AMQP.BasicProperties props = delivery.getProperties();
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(props.getCorrelationId()).build();

            String message = new String(delivery.getBody());
            int n = Integer.parseInt(message);

            System.out.println(" [.] nthPrimeList(" + message + ")");
            String response = "" + nthPrimeList(n);

            channel.basicPublish("", props.getReplyTo(), replyProps, response.getBytes());

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    public static String nthPrimeList(int n) {
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        for (int number = 2; number <= n; number++) {
            if (isPrime(number)) {
                primeList.add(number);
            }
        }
        return primeList.toString();
    }

    /**
     * @param number
     * @return boolean
     */
    public static boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false; // number is divisible so its not prime
            }
        }
        return true; // number is prime now
    }
}

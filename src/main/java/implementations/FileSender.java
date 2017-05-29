package implementations;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

public class FileSender {
    private final static String QUEUE_NAME = "mastering_rabbitmq_file";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        FileMessage msg = new FileMessage();
        msg.setFrom("John");
        msg.setTo("Nicky");
        msg.setHeader("Hello World");
        msg.setFile_content(readBytesFromFile(new File("/Users/emrahayanoglu/Desktop/Desktop/RequiredComputers.txt")));

        for (int i = 0; i < 5; i++) {
            msg.setMsgNo(i + 1);
            channel.basicPublish("", QUEUE_NAME, null, msg.toJSON().getBytes());
            System.out.println("File Message is sent: " + msg.toString());
        }

        channel.close();
        connection.close();
    }


    public static byte[] readBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new IOException("Could not completely read file "+ file.getName() + " as it is too long (" + length+ " bytes, max supported " + Integer.MAX_VALUE + ")");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+ file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}

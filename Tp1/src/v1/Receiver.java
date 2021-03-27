package v1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import java.awt.*;

public class Receiver{
    private final static String QUEUE_NAME = "queue";



    public static void main(String[] argv)throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection1 = factory.newConnection();
        Channel channel1 = connection1.createChannel();

        channel1.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            area.setText(area.getText() + message + "\n");

            System.out.println(" [x] Received '" + message + "'");
        };


        channel1.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import java.awt.*;

public class recv {
    private final static String QUEUE_NAME1 = "q1";
    private final static String QUEUE_NAME2 = "q2";

    public static void main(String[] argv) throws Exception {
        JFrame f= new JFrame("Receiver: ");
        JLabel label= new JLabel("");
        int y=20;
        label.setBounds(20,y, 300,30);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.DARK_GRAY);
        f.add(label);
        f.setSize(350,100);
        f.setLayout(null);
        f.setVisible(true);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection1 = factory.newConnection();
        Channel channel1 = connection1.createChannel();

        channel1.queueDeclare(QUEUE_NAME1, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            label.setText("Sender 1 : "+ message);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel1.basicConsume(QUEUE_NAME1, true, deliverCallback, consumerTag -> { });

        /*******************************************************************************************/
        Connection connection2 = factory.newConnection();
        Channel channel2 = connection2.createChannel();

        channel2.queueDeclare(QUEUE_NAME2,false,false,false,null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
           label.setText("Sender 2 : "+ message);

            System.out.println(" [x] Received '" + message + "'");
        };
        channel1.basicConsume(QUEUE_NAME2, true, deliverCallback2, consumerTag -> { });



    }
}

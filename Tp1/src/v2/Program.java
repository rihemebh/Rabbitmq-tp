package v2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class Program {

    private static String QUEUE_NAME = "queue" ;
    static int userNumber;
    static int user=0;
    int me;
    public JTextArea area;
    public Channel channel;


    Program(int n) throws IOException, TimeoutException {
        user++;
        me=user;
        userNumber = n;
      //  this.channel = channel;
        JTextArea area = new JTextArea("");
        JTextArea area1 = new JTextArea(20, 20);
        JFrame f = new JFrame("Sender "+user);
        area.setBounds(10, 50, 200, 40);
        // JTextComponent txt = new JTextComponent("Welcome! ") {};
        JLabel label = new JLabel(" Welcome! Enter your message ");
        label.setBounds(5, 1, 300, 40);
        label.setForeground(Color.DARK_GRAY);
        f.add(label);
        f.add(area);
        f.setSize(350, 150);
        f.setLayout(null);
        f.setVisible(true);
        JButton button = new JButton("Send");
        button.setBounds(220, 50, 70, 40);
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Sender sender = new Sender("user "+me+" : "+area.getText());
                        try {
                            for(int i=0;i<userNumber;i++)
                            sender.sendMessages();

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
        );
        f.add(button);

        area1.setBounds(20, 150, 500, 300);
        area1.setBackground(Color.WHITE);
        area1.setForeground(Color.DARK_GRAY);
        area1.setEditable(false);
        f.add(area1);

        JButton onEdit = new JButton("Edit");
        onEdit.setBounds(220, 500, 70, 40);
        onEdit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        area1.setEditable(true);

                    }});
        f.add(onEdit);

        JButton onSave = new JButton("Save");
        onSave.setBounds(320, 500, 70, 40);
        onSave.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        area1.setEditable(false);
                    }
                }
        );
        f.add(onSave);
      //  new Receiver(area1);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection1 = factory.newConnection();
        Channel channel = connection1.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            area1.setText(area1.getText() +message + "\n");
            System.out.println(" [x] Received to user "+this.userNumber+" "+ message + "");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });


    }

    public static void main(String[] argv)throws Exception{

        for(int i=1; i<=Integer.parseInt(argv[0]);i++)
            new Program(Integer.parseInt(argv[0]));
    }



}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTwo {
    private final static String QUEUE_NAME="q2";
   UserTwo() {
       JFrame f= new JFrame("Sender2: ");
       JTextArea area=new JTextArea("");
       area.setBounds(10,50, 200,40);
       JLabel label = new JLabel(" Welcome! Enter your message  ");
       label.setBounds(5,1, 300,40);
       label.setForeground(Color.DARK_GRAY);
       f.add(label);
       f.add(area);
       f.setSize(350,150);
       f.setLayout(null);
       f.setVisible(true);
       JButton button = new JButton("Send");
       button.setBounds(220,50,70,40);
       button.addActionListener(
               new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       send sender = new send(area.getText(),QUEUE_NAME);
                       try {
                           sender.sendMessages();
                       } catch (Exception exception) {
                           exception.printStackTrace();
                       }
                   }
               }
       );
       f.add(button);

   }


    public static void main(String[] args) {
        new UserTwo();
    }
}

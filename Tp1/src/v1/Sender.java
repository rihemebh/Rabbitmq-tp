package v1;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Sender {
    private String QUEUE_NAME = "queue";
    private String message;
    Sender(String message){
        this.message = message;
    }
    public void sendMessages () throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
            channel.queueDeclare(this.QUEUE_NAME, false, false, false, null);
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(" [x] send : "+message );
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class send {
    private String QUEUE_NAME="hello";
    private String message;
send(String m , String q){
    this.QUEUE_NAME = q;
    this.message =m;
}
    public void sendMessages () throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println(" [x] send : "+message );

    }
}
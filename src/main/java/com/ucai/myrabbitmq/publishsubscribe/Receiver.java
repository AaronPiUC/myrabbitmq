package com.ucai.myrabbitmq.publishsubscribe;

import com.rabbitmq.client.*;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-5 21:53
 * @Description:
 */
public class Receiver {

    public static final String QUEUE_NAME = "test_queue_fanout_email";

    public static final String EXCHAGE_NAME = "test_exchange_fanout";


    public static void main(String[] args) throws IOException, TimeoutException {
        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false,false, null);

        channel.queueBind(QUEUE_NAME, EXCHAGE_NAME, "");
        //一次分发一个
        channel.basicQos(1);

        Consumer cs = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                String msg = new String(bytes, StandardCharsets.UTF_8);
                System.out.println(msg);

                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, cs);

        channel.close();
        conn.close();
    }
}

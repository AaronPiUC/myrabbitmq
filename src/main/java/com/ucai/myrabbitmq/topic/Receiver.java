package com.ucai.myrabbitmq.topic;

import com.rabbitmq.client.*;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-3 22:19
 * @Description:
 */
public class Receiver {
    private static final String QUEUE_NAME = "test_topic_queue";
    private static final String EXCANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection con = ConnectionUtil.getConnection();
        Channel ch = con.createChannel();
        ch.queueDeclare(QUEUE_NAME, false, false, false,null);
        ch.queueBind(QUEUE_NAME, EXCANGE_NAME, "goods.add");
        ch.basicQos(1);
        //定义消费者
        Consumer cs = new DefaultConsumer(ch){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(msg);
                System.out.println("msg done");
                ch.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        boolean autoAck = true;
        ch.basicConsume(QUEUE_NAME, autoAck, cs);
    }
}

package com.ucai.myrabbitmq.simple;

import com.rabbitmq.client.*;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-1 10:18
 * @Description:
 */
public class Receiver {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel =connection.createChannel();
        //设置最多接收数量
        channel.basicQos(64);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义对列消费者
        Consumer cs = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println(new String(bytes));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }

        };
        //监听队列
        channel.basicConsume(QUEUE_NAME, cs);
        channel.close();
        connection.close();
    }
}

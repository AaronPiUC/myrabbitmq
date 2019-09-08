package com.ucai.myrabbitmq.publishsubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-5 21:28
 * @Description: 订阅发布模式
 */
public class Sender {
    public static final String EXCHAGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHAGE_NAME, BuiltinExchangeType.FANOUT);

        String msg = "hello ps";

        channel.basicPublish(EXCHAGE_NAME, "", null ,msg.getBytes());

        System.out.println(msg);

        channel.close();
        conn.close();
    }
}

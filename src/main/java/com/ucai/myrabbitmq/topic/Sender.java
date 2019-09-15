package com.ucai.myrabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    private static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msgString = "商品";
        channel.basicPublish(EXCHANGE_NAME, "goods.add", null, msgString.getBytes());
        System.out.println("---send"+msgString);

        channel.close();
        connection.close();
    }
}

package com.ucai.myrabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-1 10:16
 * @Description:
 */
public class Sender {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //从连接中获取一个通道
        Channel channel  = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //定义消息
        String msg = "hello simple";
        for (int i = 0; i < 100000; i++) {
            //发送
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        //关连接
        channel.close();
        connection.close();
    }
}

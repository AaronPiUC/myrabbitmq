package com.ucai.myrabbitmq.workqueuefair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ucai.myrabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-3 22:14
 * @Description: 工作队列
 */
public class Sender {
    private static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection con = ConnectionUtil.getConnection();

        Channel channel = con.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //告诉队列限制每次发送不得超过一条
        int perfetchCount = 1;

        channel.basicQos(perfetchCount);

        for (int i = 0; i < 50; i++) {
            String msg = "hello"+i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i*20);
        }

        channel.close();
        con.close();
    }
}

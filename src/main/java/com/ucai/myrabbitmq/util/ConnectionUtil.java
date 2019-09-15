package com.ucai.myrabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: AaronPi
 * @Date: 2019-9-1 10:05
 * @Description:
 */
public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("139.9.74.150");
        //设置协议和端口，amqp对应5672
        factory.setPort(5672);
        //设置数据库
        factory.setVirtualHost("/mytest_vhost");
        //设置用户和密码
        factory.setUsername("admin");
        factory.setPassword("123456");
        return factory.newConnection();
    }
}

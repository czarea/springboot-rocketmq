package com.czarea.springboot.rocketmq;

import com.czarea.springboot.rocketmq.config.RocketProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouzx
 */
@RestController
@SpringBootApplication
public class SpringbootRocketMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRocketMQApplication.class, args);
    }

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private RocketProperties properties;

    @RequestMapping("/test")
    public String test(Integer id) {
        Message msg = new Message(properties.getTopic(), properties.getTag(), ("controller msg " + id).getBytes());
        try {
            producer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}

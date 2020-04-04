package com.czarea.springboot.rocketmq.schedule;

import java.util.stream.IntStream;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhouzx
 */
@Component
public class InitProducer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitProducer.class);

    private final DefaultMQProducer producer;

    public InitProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IntStream.range(1, 50).forEach(i -> {
            Message msg = new Message("test", "tag1", ("msg " + i).getBytes());
            try {
                //logger.info("send msg {}", i);
                //producer.send(msg);
            } catch (Exception e) {
                logger.error("", e);
            }
        });
    }
}

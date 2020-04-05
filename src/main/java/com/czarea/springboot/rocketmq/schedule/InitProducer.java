package com.czarea.springboot.rocketmq.schedule;

import java.util.stream.IntStream;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
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
                if (i % 7 == 0) {
                    //messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
                    msg.setDelayTimeLevel(3);
                }

                msg.putUserProperty("a", String.valueOf(i));
                producer.send(msg, (mqs, msg1, arg) -> {
                    if (i % 2 == 0) {
                        return mqs.get(0);
                    }
                    return mqs.get(i % 2);
                }, i);
                producer.send(msg);
            } catch (Exception e) {
                logger.error("", e);
            }
        });
    }
}

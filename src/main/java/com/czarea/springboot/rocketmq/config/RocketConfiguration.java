package com.czarea.springboot.rocketmq.config;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouzx
 */
@Configuration
public class RocketConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RocketConfiguration.class);

    @Bean
    public DefaultMQProducer producer(RocketProperties config) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("test");
        producer.setNamesrvAddr(config.getNameServerAddress());
        producer.setVipChannelEnabled(true);
        producer.start();
        return producer;
    }

    @Bean
    public DefaultMQPushConsumer consumer1(RocketProperties properties) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");
        consumer.setConsumeThreadMin(properties.getMinThread());
        consumer.setConsumeThreadMax(properties.getMaxThread());
        consumer.setNamesrvAddr(properties.getNameServerAddress());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        try {
            consumer.subscribe(properties.getTopic(),properties.getTag());
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            msgs.forEach(msg -> {
                logger.info("consumer1 received " + new String(msg.getBody()));
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        return consumer;
    }

    @Bean
    public DefaultMQPushConsumer consumer2(RocketProperties properties) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test2");
        consumer.setConsumeThreadMin(properties.getMinThread());
        consumer.setConsumeThreadMax(properties.getMaxThread());
        consumer.setNamesrvAddr(properties.getNameServerAddress());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        try {
            consumer.subscribe(properties.getTopic(),properties.getTag());
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            msgs.forEach(msg -> {
                logger.info("consumer2 received " + new String(msg.getBody()));
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        return consumer;
    }
}

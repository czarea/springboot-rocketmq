package com.czarea.springboot.rocketmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouzx
 */
@Configuration
@ConfigurationProperties(prefix = "rocket")
public class RocketProperties {
    private String nameServerAddress;
    private Integer minThread;
    private Integer maxThread;
    private String topic;
    private String tag;

    public String getNameServerAddress() {
        return nameServerAddress;
    }

    public void setNameServerAddress(String nameServerAddress) {
        this.nameServerAddress = nameServerAddress;
    }

    public Integer getMinThread() {
        return minThread;
    }

    public void setMinThread(Integer minThread) {
        this.minThread = minThread;
    }

    public Integer getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(Integer maxThread) {
        this.maxThread = maxThread;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

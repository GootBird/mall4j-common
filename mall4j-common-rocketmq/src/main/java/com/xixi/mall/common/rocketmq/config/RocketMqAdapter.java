package com.xixi.mall.common.rocketmq.config;

import com.xixi.mall.common.rocketmq.constant.RocketMqConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@RefreshScope
@Configuration
public class RocketMqAdapter {

    @Resource
    private RocketMQMessageConverter rocketMqMessageConverter;

    @Value("${rocketmq.name-server:}")
    private String nameServer;

    public RocketMQTemplate getTemplateByTopicName(String topic) {

        RocketMQTemplate mqTemplate = new RocketMQTemplate();

        DefaultMQProducer producer = new DefaultMQProducer(topic);
        producer.setNamesrvAddr(nameServer);
        producer.setRetryTimesWhenSendFailed(RocketMqConstant.retryTimesWhenSendFailed);
        producer.setSendMsgTimeout(RocketMqConstant.TIMEOUT);

        mqTemplate.setProducer(producer);
        mqTemplate.setMessageConverter(rocketMqMessageConverter.getMessageConverter());

        return mqTemplate;
    }

}

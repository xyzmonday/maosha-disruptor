package com.yff.maosha.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.converter.SimpleMessageConverter;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class JmsConfig {

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("maosha.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("maosha.topic");
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate() {
        JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate(connectionFactory);
        jmsMessagingTemplate.setMessageConverter(new SimpleMessageConverter());
        return jmsMessagingTemplate;
    }

}

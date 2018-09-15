package com.yff.maosha.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.converter.SimpleMessageConverter;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActiveMqConfig {

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("miaosha.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("miaosha.topic");
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate() {
        JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate(connectionFactory);
        jmsMessagingTemplate.setMessageConverter(new SimpleMessageConverter());
        return jmsMessagingTemplate;
    }

    /**
     * topic模式的ListenerContainer
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ActiveMQConnectionFactory connectionFactor=new ActiveMQConnectionFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactor);
        return factory;
    }

    /**
     * queue模式的ListenerContainer
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ActiveMQConnectionFactory connectionFactor=new ActiveMQConnectionFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactor);
        return factory;
    }

}

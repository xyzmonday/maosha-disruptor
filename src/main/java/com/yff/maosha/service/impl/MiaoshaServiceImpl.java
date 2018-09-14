package com.yff.maosha.service.impl;

import com.yff.maosha.model.RequestDto;
import com.yff.maosha.service.MiaoshaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Override
    public void sendMiaoshaRequest(RequestDto request) {
        jmsMessagingTemplate.convertAndSend(queue, request);
    }
}

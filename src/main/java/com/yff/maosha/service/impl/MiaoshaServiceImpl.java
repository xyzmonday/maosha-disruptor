package com.yff.maosha.service.impl;

import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;
import com.yff.maosha.service.MiaoshaService;
import com.yff.maosha.service.ResponseCacheService;
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

    @Autowired
    private ResponseCacheService responseCacheService;

    @Override
    public void sendMiaoshaRequest(RequestDto request) {
        jmsMessagingTemplate.convertAndSend(queue, request);
    }

    @Override
    public ResponseDto getResponse(String requestId) {
        return responseCacheService.getResponse(requestId);
    }
}

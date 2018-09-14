package com.yff.maosha.service;

import com.yff.maosha.model.RequestDto;

import javax.jms.JMSException;


public interface MiaoshaService {

    void sendMiaoshaRequest(RequestDto request);

}

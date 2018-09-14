package com.yff.maosha.service;

import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;

import javax.jms.JMSException;


public interface MiaoshaService {

    void sendMiaoshaRequest(RequestDto request);

    ResponseDto getResponse(String requestId);
}

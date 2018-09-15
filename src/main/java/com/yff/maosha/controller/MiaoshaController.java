package com.yff.maosha.controller;

import com.yff.maosha.disruptor.request.RequestEventProducer;
import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;
import com.yff.maosha.service.MiaoshaService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.jms.JMSException;

/**
 * 商品秒杀web接口
 */
@Controller
public class MiaoshaController {

    @Autowired
    private MiaoshaService maoshaService;


    @Autowired
    private RequestEventProducer requestEventProducer;


    @RequestMapping(value = "/item/miaosha",method = RequestMethod.POST)
    @ResponseBody
    public RequestDto miaosha(@RequestBody RequestDto requestDto) throws JMSException {
        requestDto.setUserId(getUser());
        //maoshaService.sendMiaoshaRequest(requestDto);
        requestEventProducer.publish(requestDto);
        return requestDto;
    }

    /**
     * 获得下单结果
     *
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/order/result", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getOrderResult(@RequestParam("requestId") String requestId) {
        return maoshaService.getResponse(requestId);
    }

    /**
     *获得当前用户名的方法
     *
     * @return
     */
    private String getUser() {
        return RandomStringUtils.randomAlphabetic(5);
    }
}

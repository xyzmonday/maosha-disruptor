package com.yff.maosha.web;

import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;
import com.yff.maosha.service.MiaoshaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;

/**
 * 商品秒杀web接口
 */
@Controller
public class MiaoshaController {

    @Autowired
    private MiaoshaService maoshaService;


    @RequestMapping("/item/miaosha")
    @ResponseBody
    public ResponseDto miaosha(@RequestBody RequestDto requestDto) throws JMSException {
        maoshaService.sendMiaoshaRequest(requestDto);
        ResponseDto responseDto = new ResponseDto(requestDto.getId());
        responseDto.setSuccess(true);
        return responseDto;
    }
}

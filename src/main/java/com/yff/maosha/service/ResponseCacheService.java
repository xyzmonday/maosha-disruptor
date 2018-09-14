package com.yff.maosha.service;

import com.yff.maosha.model.ResponseDto;

/**
 * 秒杀响应缓存服务
 */
public interface ResponseCacheService {

    void setResponse(ResponseDto resposne);

    /**
     * 获取请求的响应，注意如果获取成功需要清除该缓存
     * @param requestId
     * @return
     */
    ResponseDto getResponse(String requestId);
}

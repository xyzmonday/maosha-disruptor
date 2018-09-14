package com.yff.maosha.service.impl;

import com.yff.maosha.model.ResponseDto;
import com.yff.maosha.service.ResponseCacheService;
import com.yff.maosha.utils.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ResponseCacheServiceImpl implements ResponseCacheService {

    private final static Logger logger = LoggerFactory.getLogger(ResponseCacheServiceImpl.class);

    //缓存过期时间
    private static final Long ONE_MINUTE = 1000 * 60L;
    //内存缓存
    private final ConcurrentMap<String, Pair<ResponseDto, Long>> responseCache = new ConcurrentHashMap<>();

    @Override
    public void setResponse(ResponseDto response) {
        logger.info("接受到服务端的响应 : {}", response);
        if (response != null) {
            responseCache.put(response.getRequestId(), Pair.of(response, ONE_MINUTE));
        }
    }

    @Override
    public ResponseDto getResponse(String requestId) {
        ResponseDto responseDto = null;
        if (StringUtils.isNotEmpty(requestId)) {
            Pair<ResponseDto, Long> pair = responseCache.remove(requestId);
            responseDto = pair.getFirst();
        }
        return responseDto;
    }

    /**
     * 定时清理缓存, 5分钟
     */
    @Scheduled(fixedDelay = 1000 * 60L * 5)
    public void flush() {
        Long now = System.currentTimeMillis();
        responseCache.keySet().stream()
                .filter(s -> responseCache.get(s).getSecond() < now)
                .forEach(responseCache::remove);
    }


}

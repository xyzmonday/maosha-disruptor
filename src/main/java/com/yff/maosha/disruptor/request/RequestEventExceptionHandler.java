package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.ExceptionHandler;
import com.yff.maosha.model.ResponseDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求事件异常消费者
 */
public class RequestEventExceptionHandler implements ExceptionHandler<RequestEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestEventExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, RequestEvent event) {
        // 遇到异常要记录日志的
        ResponseDto responseDto = new ResponseDto(event.getRequest().getId());
        responseDto.setErrorMessage(ExceptionUtils.getStackTrace(ex));
        responseDto.setSuccess(false);
        event.setResponse(responseDto);
        LOGGER.error("{} : {}. {} ", event.getRequest().getClass().getName(), event.getRequest().getId(), ExceptionUtils.getStackTrace(ex));
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        LOGGER.error("Exception during onStart()", ex);
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        LOGGER.error("Exception during onShutdown()", ex);
    }
}

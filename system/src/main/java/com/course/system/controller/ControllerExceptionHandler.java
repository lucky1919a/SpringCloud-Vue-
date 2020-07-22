package com.course.system.controller;

import com.course.server.common.ResponseServer;
import com.course.server.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseServer businessExceptionHandler(BusinessException e) {
        ResponseServer responseServer = new ResponseServer();
        responseServer.setSuccess(false);
        logger.error("业务异常：{}", e.getCode().getDesc());
        responseServer.setMessage(e.getCode().getDesc());
        return responseServer;
    }
}


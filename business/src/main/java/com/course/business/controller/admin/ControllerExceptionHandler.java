package com.course.business.controller.admin;

import com.course.server.common.ResponseServer;
import com.course.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseServer validatorExceptionHandler(ValidatorException e) {
        ResponseServer responseServer = new ResponseServer();
        responseServer.setSuccess(false);
        logger.warn(e.getMessage());
        responseServer.setMessage("请求参数异常！");
        return responseServer;
    }
}

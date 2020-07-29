package com.course.business.controller.web;

import com.course.server.common.ResponseServer;
import com.course.server.dto.SmsDto;
import com.course.server.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController("webSmsController")
@RequestMapping("/web/sms")
public class SmsController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseServer send(@RequestBody SmsDto smsDto) {
        logger.info("发送短信请求开始: {}", smsDto);
        ResponseServer responseServer = new ResponseServer();
        smsService.sendCode(smsDto);
        logger.info("发送短信请求结束");
        return responseServer;
    }
}

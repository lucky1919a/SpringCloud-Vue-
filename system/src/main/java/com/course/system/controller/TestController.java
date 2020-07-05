package com.course.system.controller;

import com.course.server.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public String success(){
        return "success";
    }

    @GetMapping("query.do")
    @ResponseBody
    public Object query(){
        return testService.list();
    }
}

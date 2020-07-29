package com.course.business.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.Member;
import com.course.server.dto.MemberDto;
import com.course.server.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/member")
public class MemberController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       memberService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }
}

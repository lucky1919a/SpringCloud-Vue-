package com.course.system.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.role_user;
import com.course.server.dto.role_userDto;
import com.course.server.service.role_userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/role_user")
public class role_userController {

    private static final Logger LOG = LoggerFactory.getLogger(role_userController.class);

    public static final String BUSINESS_NAME = "角色用户关联";

    @Resource
    private role_userService role_userService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       role_userService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody role_userDto role_userDto) {
        LOG.info("role_userDto: {}", role_userDto);
        // 保存校验
        ValidatorUtil.require(role_userDto.getRoleId(), "角色");
        ValidatorUtil.require(role_userDto.getUserId(), "用户");
        ResponseServer responseServer = new ResponseServer();
        role_userService.save(role_userDto);
        responseServer.setContent(role_userDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        role_userService.delete(id);
        return responseServer;
    }
}

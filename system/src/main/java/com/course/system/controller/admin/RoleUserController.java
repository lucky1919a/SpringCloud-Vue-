package com.course.system.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.RoleUser;
import com.course.server.dto.RoleUserDto;
import com.course.server.service.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/roleUser")
public class RoleUserController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);

    public static final String BUSINESS_NAME = "角色用户关联";

    @Resource
    private RoleUserService roleUserService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       roleUserService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody RoleUserDto roleUserDto) {
        LOG.info("roleUserDto: {}", roleUserDto);
        // 保存校验
        ValidatorUtil.require(roleUserDto.getRoleId(), "角色");
        ValidatorUtil.require(roleUserDto.getUserId(), "用户");
        ResponseServer responseServer = new ResponseServer();
        roleUserService.save(roleUserDto);
        responseServer.setContent(roleUserDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        roleUserService.delete(id);
        return responseServer;
    }
}

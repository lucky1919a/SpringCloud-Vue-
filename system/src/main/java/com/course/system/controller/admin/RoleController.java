package com.course.system.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.Role;
import com.course.server.dto.RoleDto;
import com.course.server.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    public static final String BUSINESS_NAME = "角色";

    @Resource
    private RoleService roleService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       roleService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody RoleDto roleDto) {
        LOG.info("roleDto: {}", roleDto);
        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);
        ResponseServer responseServer = new ResponseServer();
        roleService.save(roleDto);
        responseServer.setContent(roleDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        roleService.delete(id);
        return responseServer;
    }
}

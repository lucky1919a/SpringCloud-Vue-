package com.course.system.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.RoleResource;
import com.course.server.dto.RoleResourceDto;
import com.course.server.service.RoleResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleResourceController.class);

    public static final String BUSINESS_NAME = "角色资源关联";

    @Resource
    private RoleResourceService roleResourceService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       roleResourceService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody RoleResourceDto roleResourceDto) {
        LOG.info("roleResourceDto: {}", roleResourceDto);
        // 保存校验
        ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");
        ResponseServer responseServer = new ResponseServer();
        roleResourceService.save(roleResourceDto);
        responseServer.setContent(roleResourceDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        roleResourceService.delete(id);
        return responseServer;
    }
}

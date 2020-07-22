package com.course.system.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.dto.ResourceDto;
import com.course.server.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    public static final String BUSINESS_NAME = "资源";

    @javax.annotation.Resource
    private ResourceService resourceService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
        resourceService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody String jsonStr) {
        // 保存校验
        ValidatorUtil.require(jsonStr, "资源");
        ResponseServer responseServer = new ResponseServer();
        resourceService.saveJson(jsonStr);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer delete(@PathVariable String id) {
        logger.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        resourceService.delete(id);
        return responseServer;
    }

    /**
     * 资源树查询
     */
    @GetMapping("/load-tree")
    public ResponseServer loadTree() {
        ResponseServer responseServer = new ResponseServer();
        List<ResourceDto> resourceDtoList = resourceService.loadTree();
        responseServer.setContent(resourceDtoList);
        return responseServer;
    }
}

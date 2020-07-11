package com.course.business.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.CourseCategory;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.service.CourseCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/courseCategory")
public class CourseCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseCategoryController.class);

    public static final String BUSINESS_NAME = "课程分类";

    @Resource
    private CourseCategoryService courseCategoryService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
        courseCategoryService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody CourseCategoryDto courseCategoryDto) {
        LOG.info("courseCategoryDto: {}", courseCategoryDto);
        // 保存校验
        ResponseServer responseServer = new ResponseServer();
        courseCategoryService.save(courseCategoryDto);
        responseServer.setContent(courseCategoryDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        courseCategoryService.delete(id);
        return responseServer;
    }
}

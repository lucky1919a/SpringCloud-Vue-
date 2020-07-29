package com.course.business.controller.web;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.dto.CourseDto;
import com.course.server.dto.CoursePageDto;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController("webCourseController")
@RequestMapping("/web/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    /**
     * 列表查询，查询最新的3门已发布的课程
     */
    @GetMapping("/list-new")
    public ResponseServer listNew() {
        Page page = new Page();
        page.setPage(1);
        page.setSize(3);
        ResponseServer responseServer = new ResponseServer();
        List<CourseDto> courseDtoList = courseService.listNew(page);
        responseServer.setContent(courseDtoList);
        return responseServer;
    }

    /**
     * 列表查询，查询已发布的
     */
    @PostMapping("/list")
    public ResponseServer list(@RequestBody CoursePageDto pageDto) {
        ResponseServer responseServer = new ResponseServer();
        pageDto.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.list(pageDto);
        responseServer.setContent(pageDto);
        return responseServer;
    }

    @GetMapping("/find/{id}")
    public ResponseServer findCourse(@PathVariable String id) {
        LOG.info("查找课程开始：{}", id);
        ResponseServer responseServer = new ResponseServer();
        CourseDto courseDto = courseService.findCourse(id);
        responseServer.setContent(courseDto);
        LOG.info("查找课程结束：{}", responseServer);
        return responseServer;
    }
}
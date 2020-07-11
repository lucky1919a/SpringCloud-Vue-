package com.course.business.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.Section;
import com.course.server.dto.SectionDto;
import com.course.server.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);

    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
        sectionService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody SectionDto sectionDto) {
        LOG.info("sectionDto: {}", sectionDto);
        // 保存校验
              ValidatorUtil.require(sectionDto.getTitle(), "标题");
              ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
              ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);


        ResponseServer responseServer = new ResponseServer();
        sectionService.save(sectionDto);
        responseServer.setContent(sectionDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        sectionService.delete(id);
        return responseServer;
    }
}

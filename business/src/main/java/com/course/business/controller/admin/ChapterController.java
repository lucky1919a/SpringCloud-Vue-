package com.course.business.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody ChapterPageDto chapterPageDto) {
        ResponseServer responseServer = new ResponseServer();
        ValidatorUtil.require(chapterPageDto.getCourseId(), "课程ID");  //验证
        chapterService.list(chapterPageDto);
        responseServer.setContent(chapterPageDto);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody ChapterDto chapterDto) {
        LOG.info("chapterDto: {}", chapterDto);
        // 保存校验
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);

        ResponseServer responseServer = new ResponseServer();
        chapterService.save(chapterDto);
        responseServer.setContent(chapterDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        chapterService.delete(id);
        return responseServer;
    }
}

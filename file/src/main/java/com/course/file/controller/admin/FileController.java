package com.course.file.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.File;
import com.course.server.dto.FileDto;
import com.course.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    public static final String BUSINESS_NAME = "文件";

    @Resource
    private FileService fileService;

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
       fileService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody FileDto fileDto) {
        LOG.info("fileDto: {}", fileDto);
        // 保存校验
        ValidatorUtil.require(fileDto.getPath(), "相对路径");
        ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);
        ValidatorUtil.length(fileDto.getKey(), "文件标识", 1, 32);
        ResponseServer responseServer = new ResponseServer();
        fileService.save(fileDto);
        responseServer.setContent(fileDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        fileService.delete(id);
        return responseServer;
    }
}

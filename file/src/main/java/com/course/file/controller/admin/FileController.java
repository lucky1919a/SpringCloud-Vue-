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
}

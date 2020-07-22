package com.course.file.controller.admin;

import com.course.server.common.ResponseServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @Value("${file.path}")
    private String FILE_PATH; //保存路径

    @Resource
    private FileUploadService fileUploadService;

    @RequestMapping("/upload")
    public ResponseServer upload(@RequestParam MultipartFile file,String use) throws IOException {
        logger.info("上传文件开始：{}", file);
        logger.info(file.getOriginalFilename());
        logger.info(String.valueOf(file.getSize()));
        try {
            File upload = new File(FILE_PATH);
            return fileUploadService.upload(file,upload,use);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

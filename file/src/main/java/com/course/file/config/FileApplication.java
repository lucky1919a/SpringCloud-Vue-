package com.course.file.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

public class FileApplication {

    private static final Logger logger = LoggerFactory.getLogger(FileApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FileApplication.class);
        Environment env = app.run(args).getEnvironment();
        logger.info("启动成功！！");
        logger.info("File地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}

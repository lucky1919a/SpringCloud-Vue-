package com.course.file.controller.admin;

import com.course.server.common.ResponseServer;
import com.course.server.dto.FileDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class FileUploadService {


    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Resource
    private FileService fileService;

    public ResponseServer upload(MultipartFile file, File upload, String use){
        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
            Long currentTime = System.currentTimeMillis();
            String uploadFileName = currentTime + new Random().nextInt(100)+"."+ fileExtensionName;  //给新文件命名，时间戳+随机数
            /*String path = "teacher/" +uploadFileName;*/
            FileUseEnum useEnum = FileUseEnum.getByCode(use);

            //如果文件夹不存在则创建
            String dir = useEnum.name().toLowerCase();
            File fullDir = new File(upload,dir);
            if (!fullDir.exists()) {
                fullDir.mkdir();
            }
            String path = dir + File.separator+uploadFileName;

            Path path2 = Paths.get(upload.getAbsolutePath()+File.separator+path);
            Files.write(path2, bytes);

            //保存文件记录
            FileDto fileDto = new FileDto();
            fileDto.setPath(path);
            fileDto.setName(fileName);
            fileDto.setSize(Math.toIntExact(file.getSize()));
            fileDto.setSuffix(fileExtensionName);
            fileDto.setUse(use);
            fileService.save(fileDto);

            ResponseServer responseServer = new ResponseServer();
            fileDto.setPath(FILE_DOMAIN + path);
            responseServer.setCode(String.valueOf(HttpStatus.OK.value()));
            responseServer.setContent(fileDto);
            return responseServer;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

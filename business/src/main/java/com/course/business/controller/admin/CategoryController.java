package com.course.business.controller.admin;

import com.course.server.common.Page;
import com.course.server.common.ResponseServer;
import com.course.server.common.ValidatorUtil;
import com.course.server.domain.Category;
import com.course.server.dto.CategoryDto;
import com.course.server.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryService categoryService;


    /**
     * 列表查询
     */
    @PostMapping("/all")
    public ResponseServer all() {
        ResponseServer responseServer = new ResponseServer();
        List<CategoryDto> categoryDtoList = categoryService.all();
        responseServer.setContent(categoryDtoList);
        return responseServer;
    }

    @RequestMapping("/query.do")
    public ResponseServer query(@RequestBody Page page) {
        ResponseServer responseServer = new ResponseServer();
        categoryService.list(page);
        responseServer.setContent(page);
        return responseServer;
    }

    @RequestMapping("/save")
    public ResponseServer save(@RequestBody CategoryDto categoryDto) {
        LOG.info("categoryDto: {}", categoryDto);
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        ResponseServer responseServer = new ResponseServer();
        categoryService.save(categoryDto);
        responseServer.setContent(categoryDto);
        return responseServer;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseServer save(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseServer responseServer = new ResponseServer();
        categoryService.delete(id);
        return responseServer;
    }
}

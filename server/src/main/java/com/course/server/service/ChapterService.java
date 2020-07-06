package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public List<ChapterDto> list() {
        PageHelper.startPage(2, 1);
        ChapterExample chapterExample = new ChapterExample();
        /*chapterExample.createCriteria().andIdEqualTo("1");*/
        /*chapterExample.setOrderByClause("id desc");*/
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtoArrayList = new ArrayList<>();
        Iterator<Chapter> iterator = chapterList.iterator();
        while (iterator.hasNext()){
            Chapter chapter =iterator.next();
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoArrayList.add(chapterDto);
        }
        return chapterDtoArrayList;
    }
}

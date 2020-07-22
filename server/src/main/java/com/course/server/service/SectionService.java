package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.common.Page;
import com.course.server.dto.SectionPageDao;
import com.course.server.mapper.SectionMapper;
import com.course.server.common.CopyUtil;
import com.course.server.common.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Resource
    private CourseService courseService;

    /**
     * 列表查询
     */
    public void list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        SectionExample sectionExample = new SectionExample();
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        page.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        page.setList(sectionDtoList);
    }

    public void list(SectionPageDao sectionPageDto) {
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        sectionPageDto.setList(sectionDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 新增
     */
    private void insert(Section section) {
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 更新
     */
    private void update(Section section) {
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
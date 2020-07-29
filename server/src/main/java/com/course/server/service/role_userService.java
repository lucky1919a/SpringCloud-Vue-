package com.course.server.service;

import com.course.server.domain.role_user;
import com.course.server.domain.role_userExample;
import com.course.server.dto.role_userDto;
import com.course.server.common.Page;
import com.course.server.mapper.role_userMapper;
import com.course.server.common.CopyUtil;
import com.course.server.common.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class role_userService {

    @Resource
    private role_userMapper role_userMapper;

    /**
     * 列表查询
     */
    public void list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        role_userExample role_userExample = new role_userExample();
        List<role_user> role_userList = role_userMapper.selectByExample(role_userExample);
        PageInfo<role_user> pageInfo = new PageInfo<>(role_userList);
        page.setTotal(pageInfo.getTotal());
        List<role_userDto> role_userDtoList = CopyUtil.copyList(role_userList, role_userDto.class);
        page.setList(role_userDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(role_userDto role_userDto) {
        role_user role_user = CopyUtil.copy(role_userDto, role_user.class);
        if (StringUtils.isEmpty(role_userDto.getId())) {
            this.insert(role_user);
        } else {
            this.update(role_user);
        }
    }

    /**
     * 新增
     */
    private void insert(role_user role_user) {
        role_user.setId(UuidUtil.getShortUuid());
        role_userMapper.insert(role_user);
    }

    /**
     * 更新
     */
    private void update(role_user role_user) {
        role_userMapper.updateByPrimaryKey(role_user);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        role_userMapper.deleteByPrimaryKey(id);
    }
}
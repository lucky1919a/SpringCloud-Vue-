package com.course.server.service;

import com.course.server.domain.RoleUser;
import com.course.server.domain.RoleUserExample;
import com.course.server.dto.RoleUserDto;
import com.course.server.common.Page;
import com.course.server.mapper.RoleUserMapper;
import com.course.server.common.CopyUtil;
import com.course.server.common.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 列表查询
     */
    public void list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        page.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
        page.setList(roleUserDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if (StringUtils.isEmpty(roleUserDto.getId())) {
            this.insert(roleUser);
        } else {
            this.update(roleUser);
        }
    }

    /**
     * 新增
     */
    private void insert(RoleUser roleUser) {
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }

    /**
     * 更新
     */
    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }
}
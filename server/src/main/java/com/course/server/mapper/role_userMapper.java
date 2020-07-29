package com.course.server.mapper;

import com.course.server.domain.role_user;
import com.course.server.domain.role_userExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface role_userMapper {
    long countByExample(role_userExample example);

    int deleteByExample(role_userExample example);

    int deleteByPrimaryKey(String id);

    int insert(role_user record);

    int insertSelective(role_user record);

    List<role_user> selectByExample(role_userExample example);

    role_user selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") role_user record, @Param("example") role_userExample example);

    int updateByExample(@Param("record") role_user record, @Param("example") role_userExample example);

    int updateByPrimaryKeySelective(role_user record);

    int updateByPrimaryKey(role_user record);
}
package com.sakura.dormitory.mapper;

import com.sakura.dormitory.pojo.manager;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Resource
@Mapper
public interface managerMapper {
    int deleteByPrimaryKey(String username);

    int insert(manager record);

    int insertSelective(manager record);

    manager selectByPrimaryKey(String username);
}
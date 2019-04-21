package com.sakura.dormitory.mapper;

import com.sakura.dormitory.pojo.userInfo;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Resource
@Mapper
public interface userInfoMapper {
    int deleteByPrimaryKey(Integer studentid);

    int insert(userInfo record);

    int insertSelective(userInfo record);

    userInfo selectByPrimaryKey(Integer studentid);
}
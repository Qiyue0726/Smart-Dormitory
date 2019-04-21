package com.sakura.dormitory.mapper;

import com.sakura.dormitory.pojo.room;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Resource
@Mapper
public interface roomMapper {
    int deleteByPrimaryKey(String room);

    int insert(room record);

    int insertSelective(room record);

    room selectByPrimaryKey(String room);

    String select(Integer id);

    int selectId(String room);
}
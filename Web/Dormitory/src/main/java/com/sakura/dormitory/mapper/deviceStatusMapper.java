package com.sakura.dormitory.mapper;

import com.sakura.dormitory.pojo.deviceStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Resource
@Mapper
public interface deviceStatusMapper {
    int deleteByPrimaryKey(Integer macid);

    int insert(deviceStatus record);

    int insertSelective(deviceStatus record);

    deviceStatus selectByPrimaryKey(Integer macid);

    int update(deviceStatus record);

    int appUpdate(@Param("macId")Integer macid,@Param("light_status")Integer light_status,@Param("window_status")Integer window_status);
}
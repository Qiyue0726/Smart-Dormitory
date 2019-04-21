package com.sakura.dormitory.mapper;

import com.sakura.dormitory.pojo.sensorData;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Mapper
public interface sensorDataMapper {
    int insert(sensorData record);

    int insertSelective(sensorData record);

    List<sensorData> selectOneDay(Integer macid);

    List<sensorData> selectOneWeek(Integer macid);
}
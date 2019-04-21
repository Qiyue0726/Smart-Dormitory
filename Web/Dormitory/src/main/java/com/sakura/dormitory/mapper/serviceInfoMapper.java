package com.sakura.dormitory.mapper;

import com.sakura.dormitory.pojo.serviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource
public interface serviceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(serviceInfo record);

    int insertSelective(serviceInfo record);

    serviceInfo selectByPrimaryKey(Integer id);

    serviceInfo select(String room);

    List<serviceInfo> selectAll();

    List<serviceInfo> selectFinish();

    List<serviceInfo> selectIgnore();

    int solve(@Param("id") Integer id);

    int ignore(@Param("id") Integer id);

    List<serviceInfo> androidSelect(String room);

    int add(@Param("room")String room,@Param("title")String title,@Param("content")String content,@Param("type")Integer type);

    int recovery(Integer id);

    int reply(@Param("id") Integer id, @Param("reply") String reply);

    String getReply(@Param("id") Integer id);
}
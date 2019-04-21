package com.sakura.dormitory.service;

import com.sakura.dormitory.pojo.serviceInfo;

import java.util.List;

public interface serviceService {
    List<serviceInfo> getFinishing();

    List<serviceInfo> getFinish();

    List<serviceInfo> getIgnore();

    boolean recoveryService(int id);

    boolean reply(Integer id,String reply);

    boolean deleteService(int id);

    String getReply(int id);
}

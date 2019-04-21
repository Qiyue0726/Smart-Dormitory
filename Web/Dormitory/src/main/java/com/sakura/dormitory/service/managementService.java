package com.sakura.dormitory.service;

import com.sakura.dormitory.pojo.macData;
import com.sakura.dormitory.pojo.serviceInfo;

import java.util.List;

public interface managementService {
    macData getMacData(String room);
    boolean controlDevice(String room,int light_status,int window_status,int all_status);

    boolean solveService(Integer id);

    boolean ignoreService(Integer id);

    boolean openDoor(String room);

    boolean control(String room, int light_status, int window_status);

    List<serviceInfo> getServiceInfo(String room);

    boolean addService(String room, String title, String content, String type);

    String isPerson(String room);

}

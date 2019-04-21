package com.sakura.dormitory.service.impl;

import com.sakura.dormitory.mapper.deviceStatusMapper;
import com.sakura.dormitory.mapper.roomMapper;
import com.sakura.dormitory.mapper.sensorDataMapper;
import com.sakura.dormitory.mapper.serviceInfoMapper;
import com.sakura.dormitory.pojo.*;
import com.sakura.dormitory.service.managementService;
import com.sakura.dormitory.utils.sendToDevice;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class managementServiceImpl implements managementService {

    private int macId;

    private int all;

    private int light;

    private int window;

    private ArrayList temps;

    private ArrayList hums;

    private ArrayList times;

    private int serviceStatus;

    private int serviceType;

    private String serviceTitle;

    private String serviceData;

    private int serviceId;

    private macData macData;

    Jedis jedis = new Jedis("localhost");

    @Resource
    public roomMapper roomMapper;

    @Resource
    public deviceStatusMapper deviceStatusMapper;

    @Resource
    private sensorDataMapper sensorDataMapper;

    @Resource
    private serviceInfoMapper serviceInfoMapper;

//    @Autowired
//    sendToDevice sendToDevice;

    /**
     * 返回设备状态和温湿度信息用以显示图表
     * */
    @Override
    public macData getMacData(String room) {

        room Room = roomMapper.selectByPrimaryKey(room);

        macId = Room.getMacid();

        deviceStatus status = deviceStatusMapper.selectByPrimaryKey(macId);
        all = status.getAllStatus();
        light = status.getLightStatus();
        window = status.getWindowStatus();
        List<sensorData> sensorDatas = sensorDataMapper.selectOneDay(macId);
        temps = new ArrayList();
        hums = new ArrayList();
        times = new ArrayList();
        for(int i = 0;i<sensorDatas.size();i++){
            int temp = sensorDatas.get(i).getTemp();
            int hum = sensorDatas.get(i).getHum();
            String time = sensorDatas.get(i).getTime().toString().substring(11,16);

            temps.add(i,temp);
            hums.add(i,hum);
            times.add(i,time);
        }
//        System.out.println(times);
        serviceInfo serviceInfo = serviceInfoMapper.select(room);
        if(serviceInfo != null){
            serviceType = serviceInfo.getServicetype();
            serviceStatus = serviceInfo.getServicestatus();
            serviceTitle = serviceInfo.getServicetitle();
            serviceData = serviceInfo.getServicedata();
            serviceId = serviceInfo.getId();
            macData = new macData(all,light,window,temps,hums,times,serviceType,serviceStatus,serviceTitle,serviceData,serviceId);
        }else{
            macData = new macData(all,light,window,temps,hums,times);
        }

        return macData;
    }

    @Override
    public boolean controlDevice(String room, int light_status, int window_status, int all_status) {
        System.out.println("room--->"+room+"   light--->"+light_status+"   window--->"+window_status+"  all--->"+all_status);
        room Room = roomMapper.selectByPrimaryKey(room);

        macId = Room.getMacid();

        deviceStatus status = deviceStatusMapper.selectByPrimaryKey(macId);
        all = status.getAllStatus();
        light = status.getLightStatus();
        window = status.getWindowStatus();
        boolean controlResult = false;
        boolean result = false;
        /**
         * 网页发送过来的状态与数据库的状态不同则将新状态发给底层，进行控制
         */
        if(light != light_status){
            //状态为1则开灯
            if(light_status == 1){
                //将开灯的指令发给底层并获取成功或失败
//                controlResult = sendToDevice.controlDevice(macId,55);

                sendToDevice myThread = new sendToDevice(macId,55);
                myThread.run();
                controlResult = myThread.isResult();

                //控制成功则将新值传给数据库，进行更新
                if (controlResult){
                    result = updateStatus(macId,light_status,window_status,all_status,room);
                }else{
                    result = false;
                }

            }else {
//                controlResult = sendToDevice.controlDevice(macId,44);
                sendToDevice myThread = new sendToDevice(macId,44);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateStatus(macId,light_status,window_status,all_status,room);
                }else{
                    result = false;
                }

            }

        }else if(window != window_status){
            if(window_status == 1){
//                controlResult = sendToDevice.controlDevice(macId,77);
                sendToDevice myThread = new sendToDevice(macId,77);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateStatus(macId,light_status,window_status,all_status,room);
                }else{
                    result = false;
                }

            }else {
//                controlResult = sendToDevice.controlDevice(macId,66);
                sendToDevice myThread = new sendToDevice(macId,66);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateStatus(macId,light_status,window_status,all_status,room);
                }else{
                    result = false;
                }

            }
        }else if(all != all_status){
            if(all_status == 1){
//                controlResult = sendToDevice.controlDevice(macId,22);
                sendToDevice myThread = new sendToDevice(macId,22);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateStatus(macId,light_status,window_status,all_status,room);
                }else{
                    result = false;
                }

            }else {
//                controlResult = sendToDevice.controlDevice(macId,11);
                sendToDevice myThread = new sendToDevice(macId,11);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateStatus(macId,light_status,window_status,all_status,room);
                }else{
                    result = false;
                }

            }

        }
        return result;
    }

    @Override
    public boolean solveService(Integer id) {

        int i = serviceInfoMapper.solve(id);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean ignoreService(Integer id) {
        int i = serviceInfoMapper.ignore(id);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean openDoor(String room) {
        room Room = roomMapper.selectByPrimaryKey(room);

        macId = Room.getMacid();

        boolean controlResult = false;

//        controlResult = sendToDevice.controlDevice(macId,33);
        sendToDevice myThread = new sendToDevice(macId,33);
        myThread.run();
        controlResult = myThread.isResult();

        return controlResult;
    }

    @Override
    public boolean control(String room, int light_status, int window_status) {

        room Room = roomMapper.selectByPrimaryKey(room);

        macId = Room.getMacid();

        deviceStatus status = deviceStatusMapper.selectByPrimaryKey(macId);
        light = status.getLightStatus();
        window = status.getWindowStatus();
        boolean controlResult = false;
        boolean result = false;

        /**
         * 移动端发送过来的状态与数据库的状态不同则将新状态发给底层，进行控制
         */
        if(light != light_status){
            //状态为1则开灯
            if(light_status == 1){
                //将开灯的指令发给底层并获取成功或失败
//                controlResult = sendToDevice.controlDevice(macId,55);
                sendToDevice myThread = new sendToDevice(macId,55);
                myThread.run();
                controlResult = myThread.isResult();
                //控制成功则将新值传给数据库，进行更新
                if (controlResult){
                    result = updateLightAndWindow(macId,light_status,window_status);
                }else{
                    result = false;
                }

            }else {
//                controlResult = sendToDevice.controlDevice(macId,44);
                sendToDevice myThread = new sendToDevice(macId,44);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateLightAndWindow(macId,light_status,window_status);
                }else{
                    result = false;
                }

            }

        }else if(window != window_status){
            if(window_status == 1){
//                controlResult = sendToDevice.controlDevice(macId,77);
                sendToDevice myThread = new sendToDevice(macId,77);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateLightAndWindow(macId,light_status,window_status);
                }else{
                    result = false;
                }

            }else {
//                controlResult = sendToDevice.controlDevice(macId,66);
                sendToDevice myThread = new sendToDevice(macId,66);
                myThread.run();
                controlResult = myThread.isResult();
                if (controlResult){
                    result = updateLightAndWindow(macId,light_status,window_status);
                }else{
                    result = false;
                }

            }
        }
        return result;
    }

    @Override
    public List<serviceInfo> getServiceInfo(String room) {
        List<serviceInfo> info = serviceInfoMapper.androidSelect(room);
        return info;
    }

    @Override
    public boolean addService(String room, String title, String content, String type) {
        int i = serviceInfoMapper.add(room,title,content,Integer.parseInt(type));
        if(i>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String isPerson(String room) {
        int id = roomMapper.selectId(room);
        sendToDevice myThread = new sendToDevice(macId,88);
        myThread.run();
        String res = myThread.have();
        return res;
    }

    public boolean updateStatus(int macId,int light_status, int window_status, int all_status,String room){

        deviceStatus deviceStatus = new deviceStatus(macId,room,all_status,light_status,window_status);
        int i = deviceStatusMapper.update(deviceStatus);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }

    public boolean updateLightAndWindow(int macId,int light_status, int window_status){
        int i = deviceStatusMapper.appUpdate(macId,light_status,window_status);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }
}

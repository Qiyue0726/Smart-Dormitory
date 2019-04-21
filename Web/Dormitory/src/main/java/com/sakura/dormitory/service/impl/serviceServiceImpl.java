package com.sakura.dormitory.service.impl;

import com.sakura.dormitory.mapper.serviceInfoMapper;
import com.sakura.dormitory.pojo.serviceInfo;
import com.sakura.dormitory.service.serviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class serviceServiceImpl implements serviceService {

    @Resource
    public serviceInfoMapper serviceInfoMapper;

    @Override
    public List<serviceInfo> getFinishing() {
       List<serviceInfo> serviceInfo = serviceInfoMapper.selectAll();

       return serviceInfo;
    }

    @Override
    public List<serviceInfo> getFinish() {
        List<serviceInfo> serviceInfo = serviceInfoMapper.selectFinish();
        return serviceInfo;
    }

    @Override
    public List<serviceInfo> getIgnore() {
        List<serviceInfo> serviceInfo = serviceInfoMapper.selectIgnore();
        return serviceInfo;
    }

    @Override
    public boolean recoveryService(int id) {
        int i = serviceInfoMapper.recovery(id);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean reply(Integer id, String reply) {
        int i = serviceInfoMapper.reply(id,reply);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteService(int id) {
        int i = serviceInfoMapper.deleteByPrimaryKey(id);
        if (i>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String getReply(int id) {
        String reply = serviceInfoMapper.getReply(id);
        return reply;
    }
}

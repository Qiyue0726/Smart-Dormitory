package com.sakura.dormitory.controller;

import com.sakura.dormitory.mapper.deviceStatusMapper;
import com.sakura.dormitory.mapper.sensorDataMapper;
import com.sakura.dormitory.pojo.*;
import com.sakura.dormitory.service.managementService;
import com.sakura.dormitory.utils.resposeBodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/management/ajax")
public class managementController {

    @Autowired
    private managementService managementService;

    @Resource
    private sensorDataMapper sensorDataMapper;
    @Resource
    private deviceStatusMapper deviceStatusMapper;

    @RequestMapping(value = "/data")
    public resposeBody<macData> ajax(HttpServletRequest request){
        System.out.println("room:"+request.getParameter("room"));
        macData data = managementService.getMacData(request.getParameter("room"));

        return resposeBodyUtil.success(data);
}

    @RequestMapping(value = "/control")
    public resposeBody control(HttpServletRequest request){
        String room = request.getParameter("room");
        int light = Integer.parseInt(request.getParameter("light"));
        int window = Integer.parseInt(request.getParameter("window"));
        int all = Integer.parseInt(request.getParameter("all"));
        boolean result = managementService.controlDevice(room,light,window,all);
        if(result){
            return  resposeBodyUtil.success("切换成功！");
        }else{
            return resposeBodyUtil.error(1,"切换失败！");
        }

    }


    @RequestMapping(value = "/solve")
    public resposeBody solve(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean result = managementService.solveService(id);
        if(result){
            return  resposeBodyUtil.success("操作成功！");
        }else{
            return resposeBodyUtil.error(1,"操作失败！");
        }
    }

    @RequestMapping(value = "/ignore")
    public resposeBody ignore(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean result = managementService.ignoreService(id);
        if(result){
            return  resposeBodyUtil.success("操作成功！");
        }else{
            return resposeBodyUtil.error(1,"操作失败！");
        }
    }

    @RequestMapping(value = "/serviceInfo")
    public resposeBody getServiceInfo(HttpServletRequest request){
        String room = request.getParameter("room");
        List<serviceInfo> serviceInfo = managementService.getServiceInfo(room);
        if(serviceInfo != null) {
            return resposeBodyUtil.success(serviceInfo);
        }else {
            return resposeBodyUtil.error(1,"刷新失败");
        }
    }

    @RequestMapping(value = "/isPerson")
    public resposeBody isPerson(HttpServletRequest request){
        String room = request.getParameter("room");
        String res = managementService.isPerson(room);
        if(res != ""){
            if(res.equals("true")){
                return resposeBodyUtil.success("当前宿舍有人");
            }
            else if(res.equals("false")){
                return resposeBodyUtil.success("当前宿舍无人");
            }else {
                return resposeBodyUtil.error(1,"获取信息失败");
            }

        }else {
            return resposeBodyUtil.error(1,"获取信息失败");
        }

    }



}

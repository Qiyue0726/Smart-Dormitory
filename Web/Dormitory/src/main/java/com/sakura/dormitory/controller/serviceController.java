package com.sakura.dormitory.controller;

import com.sakura.dormitory.pojo.resposeBody;
import com.sakura.dormitory.pojo.serviceInfo;
import com.sakura.dormitory.service.serviceService;
import com.sakura.dormitory.utils.resposeBodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/service")
public class serviceController {

    @Autowired
    private serviceService serviceService;

    @RequestMapping("/getFinishing")
    public resposeBody getFinishingInfo(){
        List<serviceInfo> serviceInfo = serviceService.getFinishing();
        if(serviceInfo != null) {
            return resposeBodyUtil.success(serviceInfo);
        }else {
            return resposeBodyUtil.error(1,"获取信息失败");
        }
    }

    @RequestMapping("/getFinish")
    public resposeBody getFinishInfo(){
        List<serviceInfo> serviceInfo = serviceService.getFinish();
        if(serviceInfo != null) {
            return resposeBodyUtil.success(serviceInfo);
        }else {
            return resposeBodyUtil.error(1,"获取信息失败");
        }
    }

    @RequestMapping("/getIgnore")
    public resposeBody getIgnoreInfo(){
        List<serviceInfo> serviceInfo = serviceService.getIgnore();
        if(serviceInfo != null) {
            return resposeBodyUtil.success(serviceInfo);
        }else {
            return resposeBodyUtil.error(1,"获取信息失败");
        }
    }

    @RequestMapping(value = "/recovery")
    public resposeBody solve(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean result = serviceService.recoveryService(id);
        if(result){
            return  resposeBodyUtil.success("操作成功！");
        }else{
            return resposeBodyUtil.error(1,"操作失败！");
        }
    }

    @RequestMapping(value = "/reply")
    public resposeBody reply(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String reply = request.getParameter("reply");
        boolean result = serviceService.reply(id,reply);
        if(result){
            return  resposeBodyUtil.success("处理成功！");
        }else{
            return resposeBodyUtil.error(1,"处理失败！");
        }
    }

    @RequestMapping(value = "/getReply")
    public resposeBody getReply(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String reply = serviceService.getReply(id);
        if(reply != null){
            return resposeBodyUtil.success(reply);
        }else {
            return resposeBodyUtil.error(1,"获取失败");
        }
    }

    @RequestMapping(value = "/delete")
    public resposeBody delete(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean result = serviceService.deleteService(id);
        if(result){
            return  resposeBodyUtil.success("取消成功！");
        }else{
            return resposeBodyUtil.error(1,"取消失败！");
        }
    }




}

package com.sakura.dormitory.controller;

import com.sakura.dormitory.pojo.androidInfo;
import com.sakura.dormitory.pojo.resposeBody;
import com.sakura.dormitory.service.loginService;
import com.sakura.dormitory.service.managementService;
import com.sakura.dormitory.utils.resposeBodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/android")
public class androidController {

    @Autowired
    private managementService managementService;
    @Autowired
    private loginService loginService;

    @RequestMapping("/open")
    public resposeBody openDoor(HttpServletRequest request){
        String room = request.getParameter("room");
//        System.out.println(room);
        boolean result = managementService.openDoor(room);
        if(result){
            return resposeBodyUtil.success("开门成功");
        }else {
            return resposeBodyUtil.error(1,"开门失败");
        }
    }

    @RequestMapping("/control")
    public resposeBody control(HttpServletRequest request){
        String room = request.getParameter("room");
        int light = Integer.parseInt(request.getParameter("light"));
        int window = Integer.parseInt(request.getParameter("window"));

        System.out.println("light:"+light+"window:"+window);

        boolean result = managementService.control(room,light,window);

        if(result){
            return  resposeBodyUtil.success("切换成功");
        }else{
            return resposeBodyUtil.error(1,"切换失败");
        }

    }

    @RequestMapping(value = "/addService")
    public resposeBody add(HttpServletRequest request){
        String room = request.getParameter("room");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String type = request.getParameter("type");

        boolean result = managementService.addService(room,title,content,type);

        if(result){
            return resposeBodyUtil.success("报修成功");
        }else {
            return resposeBodyUtil.error(1,"报修失败");
        }
    }

    @RequestMapping(value = "/login")
    public resposeBody login(HttpServletRequest request){
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int password = Integer.parseInt(request.getParameter("password"));

        androidInfo info = loginService.androidLogin(studentId,password);
        if(info != null){
            return resposeBodyUtil.success(info);
        }else {
            return resposeBodyUtil.error(1,"登录失败");
        }
    }

}

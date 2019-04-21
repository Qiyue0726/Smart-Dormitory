package com.sakura.dormitory.controller;

import com.sakura.dormitory.pojo.manager;
import com.sakura.dormitory.service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class loginController {

    @Autowired
    private loginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(String userName, String password){

        boolean result = loginService.login(userName,password);
        if(result){
            System.out.println("login success");
            ModelAndView modelAndView = new ModelAndView("management");
            modelAndView.addObject(new manager(userName,password));
            return modelAndView;

        }else{
            System.out.println("error");

            return (new ModelAndView("login"));
        }
    }


    @RequestMapping(value = "/loginView")
    public String loginView(){

            return "login";
    }

    @RequestMapping(value = "/managementView",method = RequestMethod.POST)
    public ModelAndView managementView(String userName){

        ModelAndView modelAndView = new ModelAndView("management");
        modelAndView.addObject(new manager(userName));
        return modelAndView;
    }

    @RequestMapping(value = "/serviceView",method = RequestMethod.POST)
    public ModelAndView serviceView(String userName){
        ModelAndView modelAndView = new ModelAndView("service");
        modelAndView.addObject(new manager(userName));
        return modelAndView;
    }

}

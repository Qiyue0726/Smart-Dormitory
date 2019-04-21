package com.sakura.dormitory.service.impl;

import com.sakura.dormitory.mapper.managerMapper;
import com.sakura.dormitory.mapper.userInfoMapper;
import com.sakura.dormitory.pojo.androidInfo;
import com.sakura.dormitory.pojo.manager;
import com.sakura.dormitory.pojo.userInfo;
import com.sakura.dormitory.service.loginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class loginServiceImpl implements loginService {

    @Resource
    private managerMapper managerMapper;

    @Resource
    private userInfoMapper userInfoMapper;


    @Override
    public boolean login(String userName, String password) {

            manager info = managerMapper.selectByPrimaryKey(userName);
            if(info != null){
                if(password.equals(info.getPassword())){
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }

        }

    @Override
    public androidInfo androidLogin(int studentId, int password) {

        userInfo info = userInfoMapper.selectByPrimaryKey(studentId);
        if(info != null){
            if(password == info.getPassword()){
                String room = info.getRoom();
                return new androidInfo(info.getUsername(),room);
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}

package com.sakura.dormitory.service;

import com.sakura.dormitory.pojo.androidInfo;

public interface loginService {

    boolean login(String userName,String password);

    androidInfo androidLogin(int studentId, int password);
}

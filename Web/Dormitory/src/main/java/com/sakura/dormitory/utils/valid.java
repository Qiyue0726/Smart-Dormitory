package com.sakura.dormitory.utils;


import com.alibaba.fastjson.JSONObject;

public class valid {
    //判断字符串是否为json格式
    public boolean isJSONValid(String jsonInString ) {
        if(!jsonInString.equals("")) {
            try {
                JSONObject.parseObject(jsonInString);
                return  true;
            } catch (Exception e) {
                try {
                    JSONObject.parseArray(jsonInString);
                    return true;
                } catch (Exception ex1) {
                    return false;
                }
            }
        }else {
            return false;
        }
    }
}

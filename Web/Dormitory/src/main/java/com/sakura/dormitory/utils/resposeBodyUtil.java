package com.sakura.dormitory.utils;

import com.sakura.dormitory.pojo.resposeBody;

public class resposeBodyUtil {

    public static resposeBody success(Object object){
        resposeBody result = new resposeBody();
        result.setStatusCode(0);
        result.setMessage("success");
        result.setData(object);
        return result;
    }

    public static resposeBody success(String msg){
        resposeBody result = new resposeBody();
        result.setStatusCode(0);
        result.setMessage(msg);
        return result;
    }

    public static resposeBody success(){
        return success(null);
    }

    public static resposeBody error(Integer code,String msg){
        resposeBody result = new resposeBody();
        result.setStatusCode(code);
        result.setMessage(msg);
        return result;
    }

}

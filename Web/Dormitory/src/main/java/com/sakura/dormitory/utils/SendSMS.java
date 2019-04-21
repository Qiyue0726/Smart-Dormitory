package com.sakura.dormitory.utils;


import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.sakura.dormitory.pojo.sms;
import org.json.JSONException;

import java.io.IOException;

import java.util.ArrayList;

//群发短信给管理员和宿舍学生
public class SendSMS {

    // 短信应用SDK AppID
    private int appid = 1400190272; // 1400开头

    // 短信应用SDK AppKey
    private String appkey = "541067eaabfb2ef198c3f3d9651630c6";

    // 需要发送短信的手机号码
    private ArrayList phoneList = new ArrayList<String>();

    private String room;

    // 短信模板ID，需要在短信应用中申请
    private int templateId = 291409; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    //templateId7839对应的内容是"您的验证码是: {1}"
    // 签名
    private String smsSign = "智能宿舍"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台申请。


    public void sendWarn(ArrayList<sms> list) {

        try {

            phoneList.add("13250217501");
            for (sms item : list) {
                room = item.getRoom();
                phoneList.add(item.getPhone());
            }

            //将ArrayList转换为数组
            String[] phoneNumbers = new String[phoneList.size()];

            phoneList.toArray(phoneNumbers);

            phoneList.clear();

            String[] params = {room};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result = msender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);

        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }

}

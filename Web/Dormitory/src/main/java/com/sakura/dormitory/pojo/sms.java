package com.sakura.dormitory.pojo;

/**
 * @Author: yujianhao
 * @ClassName: sms
 * @Description: 描述
 * @CreateDate: 2019/3/11 0011 22:55
 */
public class sms {
    private String room;
    private String phone;

    public sms(String room, String phone) {
        this.room = room;
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

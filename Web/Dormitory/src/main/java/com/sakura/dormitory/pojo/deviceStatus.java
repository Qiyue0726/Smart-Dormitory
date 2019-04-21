package com.sakura.dormitory.pojo;

public class deviceStatus {
    private Integer macid;

    private String room;

    private Integer allStatus;

    private Integer lightStatus;

    private Integer windowStatus;

    public deviceStatus(Integer macid, String room, Integer allStatus, Integer lightStatus, Integer windowStatus) {
        this.macid = macid;
        this.room = room;
        this.allStatus = allStatus;
        this.lightStatus = lightStatus;
        this.windowStatus = windowStatus;
    }

    public Integer getMacid() {
        return macid;
    }

    public void setMacid(Integer macid) {
        this.macid = macid;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room == null ? null : room.trim();
    }

    public Integer getAllStatus() {
        return allStatus;
    }

    public void setAllStatus(Integer allStatus) {
        this.allStatus = allStatus;
    }

    public Integer getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(Integer lightStatus) {
        this.lightStatus = lightStatus;
    }

    public Integer getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(Integer windowStatus) {
        this.windowStatus = windowStatus;
    }
}
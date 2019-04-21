package com.sakura.dormitory.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class serviceInfo {
    private Integer id;

    private String room;

    private Integer servicetype;

    private String servicetitle;

    private String servicedata;

    private Integer servicestatus;

    private Integer systemstatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date chulitime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date systemtime;

    private String reply;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room == null ? null : room.trim();
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public String getServicetitle() {
        return servicetitle;
    }

    public void setServicetitle(String servicetitle) {
        this.servicetitle = servicetitle == null ? null : servicetitle.trim();
    }

    public String getServicedata() {
        return servicedata;
    }

    public void setServicedata(String servicedata) {
        this.servicedata = servicedata == null ? null : servicedata.trim();
    }

    public Integer getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(Integer servicestatus) {
        this.servicestatus = servicestatus;
    }

    public Integer getSystemstatus() {
        return systemstatus;
    }

    public void setSystemstatus(Integer systemstatus) {
        this.systemstatus = systemstatus;
    }

    public Date getChulitime() {
        return chulitime;
    }

    public void setChulitime(Date chulitime) {
        this.chulitime = chulitime;
    }

    public Date getSystemtime() {
        return systemtime;
    }

    public void setSystemtime(Date systemtime) {
        this.systemtime = systemtime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }
}
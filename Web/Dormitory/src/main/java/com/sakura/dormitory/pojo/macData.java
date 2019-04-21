package com.sakura.dormitory.pojo;

import java.util.ArrayList;

public class macData {

    private int all;

    private int light;

    private int window;

    private int serviceId;

    private ArrayList temps;

    private ArrayList hums;

    private ArrayList times;

    private int serviceType;

    private int serviceStatus;

    private String serviceTitle;

    private String serviceData;


    public macData(int all, int light, int window, ArrayList temps, ArrayList hums, ArrayList times, int serviceType, int serviceStatus, String serviceTitle, String serviceData, int serviceId) {
        this.all = all;
        this.light = light;
        this.window = window;
        this.temps = temps;
        this.hums = hums;
        this.times = times;
        this.serviceType = serviceType;
        this.serviceStatus = serviceStatus;
        this.serviceTitle = serviceTitle;
        this.serviceData = serviceData;
        this.serviceId = serviceId;
    }

    public macData(int all, int light, int window, ArrayList temps, ArrayList hums, ArrayList times) {
        this.all = all;
        this.light = light;
        this.window = window;
        this.temps = temps;
        this.hums = hums;
        this.times = times;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getWindow() {
        return window;
    }

    public void setWindow(int window) {
        this.window = window;
    }

    public ArrayList getTemps() {
        return temps;
    }

    public void setTemps(ArrayList temps) {
        this.temps = temps;
    }

    public ArrayList getHums() {
        return hums;
    }

    public void setHums(ArrayList hums) {
        this.hums = hums;
    }

    public ArrayList getTimes() {
        return times;
    }

    public void setTimes(ArrayList times) {
        this.times = times;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceData() {
        return serviceData;
    }

    public void setServiceData(String serviceData) {
        this.serviceData = serviceData;
    }
}

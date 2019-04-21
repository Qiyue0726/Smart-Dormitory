package com.sakura.dormitory.pojo;

public class arduinoStatus {
    private int all;
    private int light;
    private int window;

    public arduinoStatus(int all, int light, int window) {
        this.all = all;
        this.light = light;
        this.window = window;
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
}

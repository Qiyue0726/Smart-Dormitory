package com.sakura.dormitory.utils;


import com.sakura.dormitory.pojo.arduinoStatus;
import com.sakura.dormitory.pojo.deviceStatus;
import com.sakura.dormitory.pojo.sms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connect {

    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    private String driverName = "com.mysql.jdbc.Driver";
    private String dataBaseName = "dormitory";
    private String dataBaseHost = "localhost";
    private String url = "jdbc:mysql://"+dataBaseHost+":3306/"+dataBaseName+"?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private String username = "root";
    private String password = "3333";

    public Connection getConnection(){
        if(conn == null){
            try{
                Class.forName(driverName);
                conn = DriverManager.getConnection(url,username,password);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return conn;
    }

    public ArrayList<Long> getCardId(int macId){
        System.out.println("connect.macId--->"+macId);
        ArrayList<Long> cardList = new ArrayList();
        long cardId = 0;
        try{
            conn = getConnection();
            String sql = "select userInfo.cardId from userInfo join room on userInfo.room = room.room where room.macId = " + macId;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String card = rs.getString("cardId");
                cardId = Long.parseLong(card);
                cardList.add(cardId);
            }
            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cardList;
    }

    public void setSensorData(int macId,int temp,int hum){
        try{
            conn = getConnection();
            String sql = "insert into sensorData(macId,temp,hum) values ("+macId+","+temp+","+hum+")";
            ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public arduinoStatus getStatus(int macId){

        int all = 0;
        int light = 0;
        int window = 0;

        try{
            conn = getConnection();
            String sql = "select * from devicestatus where macId = " + macId;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                all = rs.getInt("all_status");
                light = rs.getInt("light_status");
                window = rs.getInt("window_status");
            }
            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new arduinoStatus(all,light,window);
    }


    public void update(int macId, int light, int window) {
        try{
            conn = getConnection();
            String sql = "update devicestatus set light_status = "+ light +",window_status = "+ window +" where macId = "+macId;
            ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<sms> getSms(int macId) {
        ArrayList<sms> list = new ArrayList();
        try {
            conn = getConnection();
            String sql = "select userinfo.room,userinfo.phone from room join userinfo on room.room = userinfo.room where room.macId = " + macId;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String room = rs.getString("room");
                String phone = rs.getString("phone");
                list.add(new sms(room, phone));
            }
            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}


package com.sakura.dormitory.utils;

import com.sakura.dormitory.socket.tcpServer;
import redis.clients.jedis.Jedis;

public class sendToDevice extends Thread{

    private int macId;
    private int code;
    private boolean result = false;         //控制底层返回结果
    private String res = "";              //是否有人结果
    Jedis jedis = new Jedis("localhost");

    public boolean isResult() {
        return result;
    }

    public String have(){
        return res;
    }

    public sendToDevice(int macId, int code){
        this.macId = macId;
        this.code = code;
    }

    @Override
    public void run() {
        // 这里的macId是MAC_A2，通过这个索引取出相对应的socket对象，然后将APP发送过来的数据，再发送到8266
        String id = Integer.toHexString(macId).toUpperCase();
//        System.out.println(id);
        tcpServer.ProcessSocketData psd = tcpServer.getSocketMap().get(new String("MAC_")+id);
        if (psd != null) {

            try {
                // TODO 8266在线状态
                psd.sendCode(code);
                System.out.println("数据已发送到MAC_"+id);
                Thread.sleep(3000);

//                result = (boolean) tcpServer.resultMap.get(macId);
////                tcpServer.resultMap.put(macId,false);

                result = Boolean.parseBoolean(jedis.get(String.valueOf(macId)));
                res = jedis.get("Person"+macId);

                jedis.set(String.valueOf(macId),"false");
                jedis.set("Person"+macId,"");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } else {
            // TODO 继电器离线状态
            System.out.println("socket连接为空，MAC_"+id+"未连接服务器");

            result = false;
            res = "";
        }
    }

//    public boolean controlDevice(int macId, int code){
//        // 这里的macId是MAC_A2，通过这个索引取出相对应的socket对象，然后将APP发送过来的数据，再发送到8266
//        String id = Integer.toHexString(macId).toUpperCase();
//        System.out.println(id);
//        tcpServer.ProcessSocketData psd = tcpServer.getSocketMap().get(new String("MAC_")+id);
//        if (psd != null) {
//            // TODO 8266在线状态
//            psd.sendCode(code);
//            System.out.println("数据已发送到8266");
//            boolean result = false;
//
//            try {
//                Thread.currentThread().sleep(3000);
//                result = (boolean) tcpServer.resultMap.get(macId);
//                tcpServer.resultMap.put(macId,false);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return result;
//
//        } else {
//            // TODO 继电器离线状态
//            System.out.println("socket连接为空，8266未连接服务器");
//
//                return false;
//        }
//
//    }
}

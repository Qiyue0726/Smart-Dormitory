package com.sakura.dormitory.socket;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import com.sakura.dormitory.pojo.arduinoStatus;
import com.sakura.dormitory.pojo.sms;
import com.sakura.dormitory.utils.Connect;
import com.sakura.dormitory.utils.SendSMS;
import com.sakura.dormitory.utils.valid;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 硬件端与服务器端的长连接
 *
 * @author Administrator
 *
 */

public class tcpServer extends Thread {
    private ServletContext servletContext;
    private ServerSocket serverSocket;


    private static Map<String, ProcessSocketData> socketMap = new HashMap<>();


//    public final static Map resultMap = new HashMap();


    public tcpServer(ServletContext servletContext) {
        this.servletContext = servletContext;

//         从web.xml中context-param节点获取socket端口
//        String port = this.servletContext.getInitParameter("socketPort");
        if (serverSocket == null) {
            try {

//                this.serverSocket = new ServerSocket(Integer.parseInt(port));
                this.serverSocket = new ServerSocket(33333);
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }

    public void run() {

        while (!this.isInterrupted()) { // 线程未中断执行循环

            try {
                // 开启服务器，线程阻塞，等待8266的连接
                Socket socket = serverSocket.accept();
                ProcessSocketData psd = new ProcessSocketData(socket);
                new Thread(psd).start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void closeServerSocket() {

        try {

            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //将socket连接以静态集合变量的形式暴露出去
    public static Map<String, ProcessSocketData> getSocketMap() {
        return socketMap;
    }

    public class ProcessSocketData extends Thread {
        private Socket socket;
        private DataInputStream in = null;
        private DataOutputStream out = null;

        //连接redis
        private Jedis jedis = new Jedis("localhost");

        private SendSMS sms = new SendSMS();

        private String mStrName = null;
        private boolean play = false;

        // 构造方法，传入连接进来的socket
        public ProcessSocketData(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e) {

                e.printStackTrace();
            }
            play = true;
        }

        public void run() {
            try {
                // 死循环，无限读取8266发送过来的数据
                while (play) {
                    byte[] msg = new byte[50];
                    in.read(msg);//读取流数据
                    send(msg);

                    String str = new String(msg).trim();
                    System.out.println(str);

                    if (str.contains("MAC")) {
                        mStrName = str.trim();
                        System.out.println(mStrName);
                        System.out.println("已识别");
                        /*
                         * 判断发过的是MAC_A2,那么就将此socket对象添加到这个类的静态集合里面，以MAC_A2为索引。
                         * APP与服务端的通信在AppControlServlet类中触发，想要实现APP与8266通信，只能将这个socket对象通过类的静态变量暴露出去。
                         * 等收到APP的信息，就立马通过MAC_A2作为索引取出socket，和8266进行通讯
                         */
                        tcpServer.socketMap.put(mStrName, this);


                        //设置设备上次的状态
                        Connect connect = new Connect();
//                        System.out.println(Integer.parseInt(mStrName.substring(4),16));
                        arduinoStatus arduinoStatus = connect.getStatus(Integer.parseInt(mStrName.substring(4),16));
                        int all = arduinoStatus.getAll();
                        int light = arduinoStatus.getLight();
                        int window = arduinoStatus.getWindow();
                        String string = "{\"Type\":\"Status\",\"all\":"+all+",\"light\":"+light+",\"window\":"+window+"}";
                        //延时3秒，防止设备还处于初始化状态中
                        Thread.sleep(3000);
                        send(string.getBytes());


                    }else if(new valid().isJSONValid(str)){
//                        System.out.println(str);
                        handle(str);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        public void sendCode(int code){
            byte[] bytes = null;
            String str = null;
            switch (code){
                case 11:
//                    bytes = new String("11").getBytes();
                    str = new String("11");
                    break;
                case 22:
//                    bytes = new String("22").getBytes();
                    str = new String("22");
                    break;
                case 33:
//                    bytes = new String("33").getBytes();
                    str = new String("33");
                    break;
                case 44:
//                    bytes = new String("44").getBytes();
                    str = new String("44");
                    break;
                case 55:
//                    bytes = new String("55").getBytes();
                    str = new String("55");
                    break;
                case 66:
//                    bytes = new String("66").getBytes();
                    str = new String("66");
                    break;
                case 77:
//                    bytes = new String("77").getBytes();
                    str = new String("77");
                    break;
                case 88:
//                    bytes = new String("88").getBytes();
                    str = new String("88");
                    break;
            }
            String command = "{\"Type\":\"Command\",\"Code\":"+str+"}";
            bytes = command.getBytes();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            send(bytes);
        }

        //这是服务器发送数据到8266的函数
        public void send(byte[] bytes) {
            try {
                out.write(bytes);
            } catch (IOException e) {
                try {
                    tcpServer.socketMap.remove(mStrName);
                    out.close();
                    play = false;
                    in.close();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("该客户端已退出！");
            }
        }



        //处理开门及添加温湿度到数据库
        public void handle(String string) throws JSONException {
            Connect connect = new Connect();
            JSONObject jsonObject = new JSONObject(string);
            int macId = jsonObject.getInt("ID");
            String type = jsonObject.getString("Type");
//            System.out.println(macId);
            if(new String("cardId").equals(type)){
                long cardId = jsonObject.getLong("Card");
//                System.out.println(cardId);
                ArrayList<Long> cardList = connect.getCardId(macId);
                for(Long item : cardList) {
                    if(cardId == item){
                        System.out.println("开门");
                        //在当前socket连接中直接发送数据，无需通过socketMap取出连接信息
                        sendCode(33);
                    }
                }

            }else if(new String("sensorData").equals(type)){
                int temp = jsonObject.getInt("Temp");
                int hum = jsonObject.getInt("Hum");
                connect.setSensorData(macId,temp,hum);

            }else if(new String("result").equals(type)){

//                    boolean result = Boolean.parseBoolean(jsonObject.getString("Result"));
//                    resultMap.put(macId,result);

                jedis.set(String.valueOf(macId),jsonObject.getString("Result"));

            }else if(new String("status").equals(type)){

                int light = jsonObject.getInt("light");
                int window = jsonObject.getInt("window");

                connect.update(macId,light,window);
            }else if(new String("warning").equals(type)){
                System.out.println("火灾");
                ArrayList<sms> list;
                list = connect.getSms(macId);
                sms.sendWarn(list);

            }
            else if(new String("person").equals(type)){
                if(jsonObject.getInt("Result") == 1){
                    jedis.set("Person"+macId,"true");
                }else{
                    jedis.set("Person"+macId,"false");
                }
            }
        }




    }

}
package com.example.smartdormitory.request;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpAsyncTask extends AsyncTask<String,Void,String> {

    private Context context;

    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        void setActivity(int code,String message,String data);
    }

    private PriorityListener listener;

    public HttpAsyncTask(Context context, PriorityListener listener) {
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected String doInBackground(String... strings) {

        String path = "http://47.107.107.213:8080" + strings[0];
//        String path = "http://10.0.2.2:8080" + strings[0];
        String data = strings[1];
        String content = null;

        try {
            // 创建一个URL对象，参数就是网址
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            //设置允许输入输出
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            byte[] datas = data.getBytes();

            //设置请求报文头，设定请求数据类型
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求数据长度
            urlConnection.setRequestProperty("Content-Length", String.valueOf(datas.length));
            //设置POST方式请求数据
            urlConnection.setRequestMethod("POST");
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(datas);
            // 获取服务器返回的状态码,200代表获取服务器资源全部成功，206请求部分资源
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                InputStream in = urlConnection.getInputStream();
                content = changeInputStream(in);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                System.out.println(result);
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("statusCode");
                String message = jsonObject.getString("message");
                String data = jsonObject.getString("data");

                listener.setActivity(code,message,data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "未连接到服务器或发送信息有误", Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(result);
    }


        public static String mapToString(Map<String,String> params){
        StringBuffer buffer = new StringBuffer();
        if(params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buffer.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();

    }


    /**
     * 把服务端返回的输入流转换成字符串格式
     * @param inputStream 服务器返回的输入流
     * @return 解析后的字符串
     */
    private static String changeInputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result="";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data,0,len);
                }
                result=new String(outputStream.toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

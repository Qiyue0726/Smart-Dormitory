package com.example.smartdormitory;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdormitory.request.HttpAsyncTask;

import java.util.HashMap;
import java.util.Map;

import static com.example.smartdormitory.request.HttpAsyncTask.mapToString;

public class InfoActivity extends AppCompatActivity {

    private SharedPreferences service;
    private TextView reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        reply = (TextView) findViewById(R.id.tv_reply) ;
        service = getSharedPreferences("serviceId", Context.MODE_PRIVATE);
        String id = service.getString("id",null);

        Map<String,String> params = new HashMap<String, String>();
        params.put("id",id);
        String data = mapToString(params);
        String url = "/service/getReply";
        String[] str = new String[]{url,data};
        //发出一个请求
        new HttpAsyncTask(getApplicationContext(), new HttpAsyncTask.PriorityListener() {

            @Override
            public void setActivity(int code,String message,String data) {
                if(code == 0) {

                    reply.setText(message);
                }
                else if(code == 1)
                    reply.setText(message);
                else
                    Toast.makeText(getApplicationContext(), "网络异常，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        }).execute(str);

    }
}

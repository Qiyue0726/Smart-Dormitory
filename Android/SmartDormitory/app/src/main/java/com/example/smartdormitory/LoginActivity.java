package com.example.smartdormitory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartdormitory.request.HttpAsyncTask;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.smartdormitory.request.HttpAsyncTask.mapToString;

public class LoginActivity extends AppCompatActivity {

    private EditText id;
    private EditText password;


    private String studentId;
    private String paw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        id = (EditText)findViewById(R.id.student_id);
        password = (EditText) findViewById(R.id.password);
        SparkButton login = (SparkButton) findViewById(R.id.loginButton);

        //获取其他页面控件
//        LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
//        View layout = factory.inflate(R.layout.fragment_me, null);
//        tv_name = (TextView)layout.findViewById(R.id.userName);

        login.setEventListener(new SparkEventListener(){

            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                studentId = id.getText().toString();
                paw = password.getText().toString();

                Map<String,String> params = new HashMap<String, String>();
                params.put("studentId",studentId);
                params.put("password",paw);
                String data = mapToString(params);
                String url = "/android/login";
                String[] str = new String[]{url,data};
                //发出一个请求
                new HttpAsyncTask(getApplicationContext(), new HttpAsyncTask.PriorityListener() {

                    @Override
                    public void setActivity(int code,String message,String data) {
                        if(code == 0) {

                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                String name = jsonObject.getString("name");
                                String room = jsonObject.getString("room");

                                //保存登录信息
                                SharedPreferences loginStatus = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor loginEditor = loginStatus.edit();
                                loginEditor.putString("status","login");
                                loginEditor.putString("name",name);
                                loginEditor.putString("room",room);
                                loginEditor.apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            LoginActivity.this.finish();


                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        }
                        else if(code == 1) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            //保存登录信息
                            SharedPreferences loginStatus = getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor loginEditor = loginStatus.edit();
                            loginEditor.putString("status","logout");
                            loginEditor.putString("name","请先进行登录");
                            loginEditor.putString("room",null);
                            loginEditor.apply();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "网络异常，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                }).execute(str);

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });
    }
}

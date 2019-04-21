package com.example.smartdormitory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartdormitory.request.HttpAsyncTask;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.smartdormitory.request.HttpAsyncTask.mapToString;

public class AddActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private SparkButton add;

    private String titleInfo;
    private String contentInfo;

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> type_adapter;

    private String room;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        SharedPreferences info = getSharedPreferences("login", Context.MODE_PRIVATE);
        room = info.getString("room",null);

        title = (EditText)findViewById(R.id.add_title_edit);
        content = (EditText) findViewById(R.id.add_content_edit);
        add = (SparkButton) findViewById(R.id.addButton);
        add.setEventListener(new SparkEventListener(){

            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                titleInfo = title.getText().toString();
                contentInfo = content.getText().toString();

                Map<String,String> params = new HashMap<String, String>();
                params.put("room",room);
                params.put("title",titleInfo);
                params.put("content",contentInfo);
                params.put("type", String.valueOf(type));
                String data = mapToString(params);
                String url = "/android/addService";
                String[] str = new String[]{url,data};
                //发出一个请求
                new HttpAsyncTask(getApplicationContext(), new HttpAsyncTask.PriorityListener() {

                    @Override
                    public void setActivity(int code,String message,String data) {
                        if(code == 0) {
                            AddActivity.this.finish();

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                        else if(code == 1)
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "网络异常，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                }).execute(str);

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        spinner = (Spinner) findViewById(R.id.type_spinner);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("请选择");
        data_list.add("网络");
        data_list.add("阳台");
        data_list.add("空调");
        data_list.add("其他");

        //适配器
        type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,data_list);
        //设置样式
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(type_adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = 0;

            }
        });

    }
}

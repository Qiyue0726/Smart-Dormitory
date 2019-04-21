package com.example.smartdormitory.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdormitory.AddActivity;
import com.example.smartdormitory.InfoActivity;
import com.example.smartdormitory.R;
import com.example.smartdormitory.adapter.RecyclerViewAdapter;
import com.example.smartdormitory.bean.ServiceEntity;
import com.example.smartdormitory.request.HttpAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.smartdormitory.request.HttpAsyncTask.mapToString;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences info;

    private String room;


    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    boolean isLoading;
    private ArrayList<ServiceEntity> data = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Handler handler = new Handler();

    private int lastVisibleItemPosition;

    private FloatingActionButton btnAdd;



    public ServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        adapter = new RecyclerViewAdapter(data,view.getContext());

        btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        info = context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    @Override
    public void onResume() {

        super.onResume();

        if(info.getString("status",null).equals("login")){
            room = info.getString("room",null);
        }else {
            room = null;
        }
        initView();
        initData();
    }
    public void initView() {


        swipeRefreshLayout.setColorSchemeResources(R.color.redStatus);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getData();
                    }
                }, 100);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);       //绑上列表管理器

        data.add(new ServiceEntity(1,"textTitle","textContent"));

        recyclerView.setAdapter(adapter);                   //绑定数据集

        //添加点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView id = (TextView) view.findViewById(R.id.tv_id);
                final String serviceId = id.getText().toString();

                SharedPreferences service = getContext().getSharedPreferences("serviceId",Context.MODE_PRIVATE);
                SharedPreferences.Editor serviceEditor = service.edit();
                serviceEditor.putString("id",serviceId);
                serviceEditor.apply();

                startActivity(new Intent(getActivity(), InfoActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.d("test", "item position = " + position);
                TextView id = (TextView) view.findViewById(R.id.tv_id);
                final String serviceId = id.getText().toString();
//                Log.d("id",serviceId);
                new AlertDialog.Builder(getContext())
                        .setTitle("是否取消报修？")//这里是表头的内容
//                        .setMessage(getString(R.string.media_record_stop) + path)//这里是中间显示的具体信息
                        .setPositiveButton("确定",//这个string是设置右边按钮的文字
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("result","确定");


                                        Map<String,String> params = new HashMap<String, String>();
                                        params.put("id",serviceId);
                                        String data = mapToString(params);
                                        String url = "/service/delete";
                                        String[] str = new String[]{url,data};
                                        //发出一个请求
                                        new HttpAsyncTask(getContext(), new HttpAsyncTask.PriorityListener() {

                                            @Override
                                            public void setActivity(int code,String message,String data) {
                                                if(code == 0){
                                                    initView();
                                                    initData();
                                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                }
                                                else if(code == 1)
                                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(getContext(), "网络传输异常，请检查网络设置", Toast.LENGTH_SHORT).show();
                                            }
                                        }).execute(str);


                                    }
                                })//setPositiveButton里面的onClick执行的是左边按钮
                        .setNegativeButton("取消",//这个string是设置左边按钮的文字
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })//setNegativeButton里面的onClick执行的是右边的按钮的操作
                        .show();
            }
        });
    }


    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 100);

    }

    /**
     * 获取测试数据
     */
    private void getData() {
        data.clear();

        Map<String,String> params = new HashMap<String, String>();
        params.put("room",room);
        String requestData = mapToString(params);
        String url = "/management/ajax/serviceInfo";
        String[] str = new String[]{url,requestData};
        //发出一个请求
        new HttpAsyncTask(getView().getContext(), new HttpAsyncTask.PriorityListener() {

            @Override
            public void setActivity(int code,String message,String reponseData) {
                if(code == 0) {
//                    Log.d("info",reponseData);
                    try {
                        JSONArray serviceArray = new JSONArray(reponseData);
                        for(int i = 0;i<serviceArray.length();i++){
//                            Log.d("t", String.valueOf(serviceArray.get(i)));
                            JSONObject info = new JSONObject(String.valueOf(serviceArray.get(i)));
//                            Log.d("title", String.valueOf(info.getInt("id")));
                            data.add(new ServiceEntity(info.getInt("id"),info.getString("servicetitle"),info.getString("servicedata")));
                        }
//                        Toast.makeText(getView().getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getView().getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                    }

                }
                else if(code == 1)
                    Toast.makeText(getView().getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getView().getContext(), "网络异常，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        }).execute(str);

        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyItemRemoved(adapter.getItemCount());
    }


}


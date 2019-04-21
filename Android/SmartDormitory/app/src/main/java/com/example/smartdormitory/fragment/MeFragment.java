package com.example.smartdormitory.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdormitory.LoginActivity;
import com.example.smartdormitory.MainActivity;
import com.example.smartdormitory.R;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String name;

    private SharedPreferences info;

    private TextView tv_name;

    private SparkButton login;
    private SparkButton logout;


    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        View view = inflater.inflate(R.layout.fragment_me, container, false);


        tv_name = (TextView) view.findViewById(R.id.userName);
        login = (SparkButton) view.findViewById(R.id.btn_login);
        logout = (SparkButton) view.findViewById(R.id.btn_logout);

        login.setEventListener(new SparkEventListener(){


            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        logout.setEventListener(new SparkEventListener(){


            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                new AlertDialog.Builder(getContext())
                        .setTitle("登出系统？")//这里是表头的内容
//                        .setMessage(getString(R.string.media_record_stop) + path)//这里是中间显示的具体信息
                        .setPositiveButton("确定",//这个string是设置右边按钮的文字
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        SharedPreferences.Editor loginEditor = info.edit();
                                        loginEditor.putString("status","logout");
                                        loginEditor.putString("name","请先进行登录");
                                        loginEditor.putString("room",null);
                                        loginEditor.apply();
                                        Toast.makeText(getContext(), "登出成功", Toast.LENGTH_SHORT).show();

//                                        onResume();
                                        MainActivity mainActivity = (MainActivity) getActivity();
                                        mainActivity.reflush();


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

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

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
            name = info.getString("name",null);

            login.setVisibility(View.GONE);

            logout.setVisibility(View.VISIBLE);
        }else {
            login.setVisibility(View.VISIBLE);

            logout.setVisibility(View.GONE);
            name = "请先进行登录";
        }

        tv_name.setText(name);

    }

}

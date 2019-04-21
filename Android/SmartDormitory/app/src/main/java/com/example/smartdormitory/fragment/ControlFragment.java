package com.example.smartdormitory.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartdormitory.R;
import com.example.smartdormitory.request.HttpAsyncTask;
import com.github.zagum.switchicon.SwitchIconView;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.smartdormitory.request.HttpAsyncTask.mapToString;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences info;

    private SwitchIconView lightView;
    private SwitchIconView windowView;
    private View lightButton;
    private View windowButton;

    private SparkButton openButton;

    public int lightStatus = 0;
    public int windowStatus = 0;

    private String room;


    public ControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFragment newInstance(String param1, String param2) {
        ControlFragment fragment = new ControlFragment();
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
//        MyApplication application = (MyApplication) getActivity().getApplication();
//        room = application.getRoom();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_control, container, false);
        lightView = (SwitchIconView) view.findViewById(R.id.lightView);
        windowView = (SwitchIconView) view.findViewById(R.id.windowView);
        lightButton = view.findViewById(R.id.lightButton);
        windowButton = view.findViewById(R.id.windowButton);

        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightView.switchState();
                if(lightView.isIconEnabled()){
                    lightStatus = 1;
                }else {
                    lightStatus = 0;
                }
//                System.out.println(lightStatus);
                controlDevice(view,lightStatus,windowStatus);

            }

        });
        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowView.switchState();
                if(windowView.isIconEnabled()){
                    windowStatus = 1;
                }else {
                    windowStatus = 0;
                }
//                System.out.println(windowStatus);
                controlDevice(view,lightStatus,windowStatus);
            }
        });

        openButton = (SparkButton) view.findViewById(R.id.doorButton);

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
        init();
        openButton.setEventListener(new SparkEventListener(){


            @Override
            public void onEvent(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                Map<String,String> params = new HashMap<String, String>();
                params.put("room",room);
                String data = mapToString(params);
                String url = "/android/open";
                String[] str = new String[]{url,data};
                //发出一个请求
                new HttpAsyncTask(getView().getContext(), new HttpAsyncTask.PriorityListener() {

                    @Override
                    public void setActivity(int code,String message,String data) {
                        if(code == 0)
                            Toast.makeText(getView().getContext(), message, Toast.LENGTH_SHORT).show();
                        else if(code == 1)
                            Toast.makeText(getView().getContext(), message, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getView().getContext(), "网络异常，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                }).execute(str);

//                Toast ts = Toast.makeText(view.getContext(),result, Toast.LENGTH_SHORT);
//                ts.show() ;
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }

    public void init(){
        lightView = (SwitchIconView) getView().findViewById(R.id.lightView);
        windowView = (SwitchIconView) getView().findViewById(R.id.windowView);

        Map<String,String> params = new HashMap<String, String>();
        params.put("room",room);
        String data = mapToString(params);
        String url = "/management/ajax/data";
        String[] str = new String[]{url,data};
        //发出一个请求
        new HttpAsyncTask(getView().getContext(), new HttpAsyncTask.PriorityListener() {

            @Override
            public void setActivity(int code,String message,String data) {
                if(code == 0){
//                    Toast.makeText(view.getContext(), data, Toast.LENGTH_SHORT).show();
                    if(data != null){
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            lightStatus = jsonObject.getInt("light");
                            windowStatus = jsonObject.getInt("window");
//                            System.out.println("L:"+lightStatus+"W:"+windowStatus);
                            //设置开关初始状态
                            if(lightStatus == 1){
                                lightView.setIconEnabled(true,true);
                            }else {
                                lightView.setIconEnabled(false,true);
                            }
                            if(windowStatus == 1){
                                windowView.setIconEnabled(true,true);
                            }else {
                                windowView.setIconEnabled(false,true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if(code == 1)
                    Toast.makeText(getView().getContext(), message, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getView().getContext(), "网络传输异常，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        }).execute(str);
    }

    public void controlDevice(final View view, int light, int window){
        Map<String,String> params = new HashMap<String, String>();
        params.put("room",room);
        params.put("light",String.valueOf(lightStatus));
        params.put("window",String.valueOf(windowStatus));
        String data = mapToString(params);
        String url = "/android/control";
        String[] str = new String[]{url,data};
        //发出一个请求
        new HttpAsyncTask(view.getContext(), new HttpAsyncTask.PriorityListener() {

            @Override
            public void setActivity(int code,String message,String data) {
                if(code == 0)
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                else if(code == 1) {
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                    init();
                }
                else
                    Toast.makeText(view.getContext(), "网络异常,请检查网络设置", Toast.LENGTH_SHORT).show();

            }
        }).execute(str);
    }

}

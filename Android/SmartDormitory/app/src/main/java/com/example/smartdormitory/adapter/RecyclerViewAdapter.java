package com.example.smartdormitory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartdormitory.R;
import com.example.smartdormitory.bean.ServiceEntity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ServiceEntity> serviceList;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewAdapter(ArrayList<ServiceEntity> serviceList, Context context) {

        this.serviceList = serviceList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.RecyclerViewHolder recyclerViewHolder, int position) {


        if (recyclerViewHolder instanceof RecyclerViewHolder) {
            ServiceEntity serviceEntity = serviceList.get(position);

            recyclerViewHolder.title.setText(serviceEntity.getTitle());
            recyclerViewHolder.content.setText(serviceEntity.getContent());
            recyclerViewHolder.serviceId.setText(String.valueOf(serviceEntity.getId()));

            if (onItemClickListener != null) {
                recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = recyclerViewHolder.getLayoutPosition();
                        onItemClickListener.onItemClick(recyclerViewHolder.itemView, position);
                    }
                });

                recyclerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = recyclerViewHolder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(recyclerViewHolder.itemView, position);
                        return false;
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
//        return serviceList.size() == 0 ? 0 : serviceList.size() + 1;
    }


    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_base, parent,
                false);
        return new RecyclerViewHolder(
                view,
                (TextView) view.findViewById(R.id.tv_title),
                (TextView) view.findViewById(R.id.tv_content),
                (TextView) view.findViewById(R.id.tv_id));


    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView serviceId;

        public RecyclerViewHolder(View itemView, TextView title,TextView content,TextView id) {
            super(itemView);
            this.title = title;
            this.content = content;
            this.serviceId = id;
        }
    }


}

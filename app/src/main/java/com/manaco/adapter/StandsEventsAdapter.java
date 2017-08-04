package com.manaco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.model.StandsEventsModel;
import com.manaco.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by vinove on 4/7/16.
 */

public class StandsEventsAdapter extends RecyclerView.Adapter<StandsEventsAdapter.MyViewHolder> {
    List<StandsEventsModel> modelArrayList;
    Context mContext;
    private String startDateTime;


    public StandsEventsAdapter(Context context) {
        this.mContext = context;
        modelArrayList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.standevents_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public void addtoArray(StandsEventsModel standsEventsModel) {
        modelArrayList.add(standsEventsModel);
    }

    public void clear() {
        modelArrayList.clear();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StandsEventsModel model = modelArrayList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvDate.setText(model.getStart_dateTime());

       // String startTime ="<b>" + CommonUtils.timeFormate(model.getStart_dateTime()) + "</b> ";
      //  String endTime="<b>" + CommonUtils.timeFormate(model.getEnd_dateTime()) + "</b> ";
        String string=CommonUtils.dateFormate(model.getStart_dateTime()) +" "+"\nFrom"+" "+CommonUtils.timeFormate(model.getStart_dateTime())+" "+"to"+" "+CommonUtils.timeFormate(model.getEnd_dateTime());
        holder.tvDate.setText(string);
      ;

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDate;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvTestDrive);
            tvDate = (TextView) itemView.findViewById(R.id.tvDateDescription);
        }
    }
}

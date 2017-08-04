package com.manaco.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.model.ListModel;

import java.util.ArrayList;

public class ListPopupWindowAdapter extends BaseAdapter {

    Context mContext;
    ListModel model;
    LayoutInflater inflater;
    ArrayList<ListModel> modelArrayList;

    public ListPopupWindowAdapter(Context mContext, ArrayList<ListModel> modelArrayList){
        this.mContext = mContext;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ListModel getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.listitem, parent, false);
        holder.textView = (TextView) convertView.findViewById(R.id.tvDefault);
        if(modelArrayList.size()>0){
            model = modelArrayList.get(position);
            holder.textView.setText(model.getName());
        }
        return convertView;
    }

    class ViewHolder {
       public TextView textView;
    }
}

package com.manaco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.model.FollowModel;
import com.manaco.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinove on 4/7/16.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    List<NewsModel> modelArrayList;
    Context mContext;


    public NewsAdapter(Context context) {
        this.mContext = context;
        modelArrayList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public void addtoArray(NewsModel newsModel) {
        modelArrayList.add(newsModel);
    }


    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       // FollowModel model = modelArrayList.get(position);


    }

    @Override
    public int getItemCount() {
       // return modelArrayList.size();
        return 10;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {




        public MyViewHolder(View itemView) {
            super(itemView);


        }
    }

}

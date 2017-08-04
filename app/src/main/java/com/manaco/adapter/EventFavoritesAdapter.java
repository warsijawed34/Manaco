package com.manaco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.model.ExhibithorFavorites;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinove on 4/7/16.
 */

public class EventFavoritesAdapter extends RecyclerView.Adapter<EventFavoritesAdapter.MyViewHolder> {
    List<ExhibithorFavorites> modelArrayList;
    Context mContext;


    public EventFavoritesAdapter(Context context) {
        this.mContext = context;
        modelArrayList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.favorites_item_events, parent, false);
        return new MyViewHolder(itemView);
    }

    public void addtoArray(ExhibithorFavorites exhibithorFavoritesModel) {
        modelArrayList.add(exhibithorFavoritesModel);
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
        public TextView tvDate;
        public TextView tvName;
        public ImageView ivPic;




        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate= (TextView) itemView.findViewById(R.id.tvDate);
            tvName= (TextView) itemView.findViewById(R.id.tvName);
            ivPic= (ImageView) itemView.findViewById(R.id.ivLogo);


        }
    }

}

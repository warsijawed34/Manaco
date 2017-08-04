package com.manaco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.model.StandsModel;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinove on 4/7/16.
 */

public class ExhibithorsFavoritesAdapter extends RecyclerView.Adapter<ExhibithorsFavoritesAdapter.MyViewHolder> {
    private List<StandsModel> modelArrayList;
    private Context mContext;
    private String langId;
    public static FavClickListner listClickListener;


    public ExhibithorsFavoritesAdapter(Context context) {
        this.mContext = context;
        modelArrayList = new ArrayList<>();
        langId = SharedPreferencesManger.getPrefValue(mContext, Constants.LANGID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.favorites_item_exibithors, parent, false);
        return new MyViewHolder(itemView);
    }

    public void addtoArray(StandsModel model) {
        modelArrayList.add(model);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StandsModel model = modelArrayList.get(position);
        holder.tvName.setText(model.getName());

        if (langId.equals("1")) {
            holder.tvDescription.setText(model.getDescription());

        } else if (langId.equals("2")) {
            holder.tvDescription.setText(model.getDescription_fr());

        } else if (langId.equals("3")) {
            holder.tvDescription.setText(model.getDescription_it());
        }


        com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.not_image)
                .showImageOnFail(R.drawable.not_image).build();
        imageLoader.displayImage(model.getLogo_path(), holder.ivPic, options);


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();

    }

    public StandsModel getItem(int position) {
        return modelArrayList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvDescription;
        public TextView tvName;
        public ImageView ivPic;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            ivPic = (ImageView) itemView.findViewById(R.id.ivLogo);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listClickListener.onItemClick(getAdapterPosition(), view);

        }
    }

    public void setOnItemClickListener(FavClickListner myClickListener) {
        this.listClickListener = myClickListener;
    }

    public interface FavClickListner {
        public void onItemClick(int position, View v);
    }

}

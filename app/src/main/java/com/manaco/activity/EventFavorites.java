package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.adapter.EventFavoritesAdapter;
import com.manaco.adapter.ExhibithorsFavoritesAdapter;

/**
 * Created by vinove on 25/11/16.
 */

public class EventFavorites extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventFavoritesAdapter exFavAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibithor_favorites);
        mContext = EventFavorites.this;
        initView();
        addListener();
        myToolbar();

    }

    private void initView() {

        ivLeft = (ImageView) findViewById(R.id.ivIconLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvExFavorites);


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        exFavAdapter = new EventFavoritesAdapter(EventFavorites.this);
        mRecyclerView.setAdapter(exFavAdapter);

    }

    private void addListener() {
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
    }

    private void myToolbar() {
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivIconLeft:
                onBackPressed();
                break;
            case R.id.ivRight:
                intent = new Intent(mContext, SearchActvity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            default:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.exit_slide_left);
    }

}

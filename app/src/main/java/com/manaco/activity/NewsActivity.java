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
import com.manaco.adapter.NewsAdapter;

/**
 * Created by vinove on 17/11/16.
 */

public class NewsActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_actitity);
        mContext = NewsActivity.this;
        initView();
        addListener();
    }

    private void initView() {
        ivLeft= (ImageView) findViewById(R.id.ivIconLeft);
        ivRight= (ImageView) findViewById(R.id.ivRight);
        tvRight= (TextView) findViewById(R.id.tvRight);
        tvCenter= (TextView) findViewById(R.id.tvCenter);
        mRecyclerView= (RecyclerView) findViewById(R.id.rvNews);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        newsAdapter = new NewsAdapter(NewsActivity.this);
        mRecyclerView.setAdapter(newsAdapter);
    }

    private void addListener() {
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.ivIconLeft:
                onBackPressed();
                break;
            case R.id.ivRight:
                intent=new Intent(mContext,SearchActvity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
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
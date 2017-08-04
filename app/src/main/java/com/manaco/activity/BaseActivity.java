package com.manaco.activity;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manaco.R;

/**
 * Created by vinove on 2/11/16.
 */

public class BaseActivity extends AppCompatActivity {
    Context mContext;
    public static  ImageView ivLeft,ivRight;
    public static TextView tvRight, tvCenter;
    public static Toolbar toolbar;

    public BaseActivity(){
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=BaseActivity.this;
        ivLeft= (ImageView) findViewById(R.id.ivIconLeft);
        ivRight= (ImageView) findViewById(R.id.ivRight);
        tvCenter= (TextView) findViewById(R.id.tvCenter);
        tvRight= (TextView) findViewById(R.id.tvRight);
        toolbar = (Toolbar) findViewById(R.id.llToolBar);
        setSupportActionBar(toolbar);

    }

}

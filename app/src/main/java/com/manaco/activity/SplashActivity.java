package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.manaco.R;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.Constants;

/**
 * Created by vinove on 2/11/16.
 */

public class SplashActivity extends BaseActivity {

    private Context mContext;
    private static int SPLASH_TIME_OUT = 2000;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mContext = SplashActivity.this;
        userId = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(userId.isEmpty()) {
                    Intent intent = new Intent(mContext, LanguageActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }

}

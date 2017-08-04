package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.utils.CommonUtils;

/**
 * Created by vinove on 1/12/16.
 */

public class FavoritesViewMap extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout llExhibitors, llEvent, llService, llShuttles, llFoodDrinks, llActualities, llAccount, llSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_view_map);
        mContext = FavoritesViewMap.this;
        initView();
        addListener();

    }

    private void initView() {
        ivLeft = (ImageView) findViewById(R.id.ivIconLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        llExhibitors = (LinearLayout) findViewById(R.id.llExhibitors);
        llEvent = (LinearLayout) findViewById(R.id.llEvent);
        llService = (LinearLayout) findViewById(R.id.llService);
        llShuttles = (LinearLayout) findViewById(R.id.llShuttles);
        llFoodDrinks = (LinearLayout) findViewById(R.id.llFoodDrinks);
        llActualities = (LinearLayout) findViewById(R.id.llActualities);
        llAccount = (LinearLayout) findViewById(R.id.llAccount);
        llSetting = (LinearLayout) findViewById(R.id.llSetting);

    }

    private void addListener() {
        llExhibitors.setOnClickListener(this);
        llEvent.setOnClickListener(this);
        llService.setOnClickListener(this);
        llShuttles.setOnClickListener(this);
        llFoodDrinks.setOnClickListener(this);
        llActualities.setOnClickListener(this);
        llAccount.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.llExhibitors:
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                intent = new Intent(mContext, HomeActivity.class);
                intent.putExtra("fragment", "exhibitorsFragment");
                startActivity(intent);
                break;
            case R.id.llEvent:
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                intent = new Intent(mContext, HomeActivity.class);
                intent.putExtra("fragment", "eventFragment");
                startActivity(intent);

                break;
            case R.id.llService:
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                intent = new Intent(mContext, HomeActivity.class);
                intent.putExtra("fragment", "serviceFragment");
                startActivity(intent);

                break;
            case R.id.llShuttles:
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                intent = new Intent(mContext, HomeActivity.class);
                intent.putExtra("fragment", "shuttlesFragment");
                startActivity(intent);

                break;
            case R.id.llFoodDrinks:
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                CommonUtils.showToast(mContext, "llFoodDrinks");
                break;
            case R.id.llActualities:
                CommonUtils.showToast(mContext, "llActualities");
                break;
            case R.id.llAccount:
                intent = new Intent(mContext, MenuActvity.class);
                intent.putExtra("menu", "accountMenu");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.llSetting:
                intent = new Intent(mContext, MenuActvity.class);
                intent.putExtra("menu", "settingMenu");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.ivIconLeft:
                intent = new Intent(mContext, MenuActvity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
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
}

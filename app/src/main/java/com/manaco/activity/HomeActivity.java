package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.manaco.R;
import com.manaco.fragments.BaseFragment;
import com.manaco.fragments.EventFragment;
import com.manaco.fragments.ExhibitorsFragment;
import com.manaco.fragments.FoodDrinksFragment;
import com.manaco.fragments.ServicesFragment;
import com.manaco.fragments.ShuttlesFragment;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;

import static com.manaco.activity.BaseActivity.toolbar;


/**
 * Created by vinove on 3/11/16.
 */

public class HomeActivity extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout llExhibitors, llEvent,llService,llShuttles,llFoodDrinks,llActualities,llAccount,llSetting;
    private String fragment, user_id;
    public Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mContext = HomeActivity.this;

        user_id = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

        initView();
        addListener();
        Intent intent=getIntent();
        fragment=intent.getStringExtra("fragment");

        if(fragment==null){
            llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
            llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            ExhibitorsFragment exhibitorsFragment=new ExhibitorsFragment();
            onFragmentReplace(exhibitorsFragment, new Bundle());
        }else {
            fragmentCallFromMenu(fragment);
        }
    }

    private void initView() {
        llExhibitors= (LinearLayout) findViewById(R.id.llExhibitors);
        llEvent= (LinearLayout) findViewById(R.id.llEvent);
        llService= (LinearLayout) findViewById(R.id.llService);
        llShuttles= (LinearLayout) findViewById(R.id.llShuttles);
        llFoodDrinks= (LinearLayout) findViewById(R.id.llFoodDrinks);
        llActualities= (LinearLayout) findViewById(R.id.llActualities);
        llAccount= (LinearLayout) findViewById(R.id.llAccount);
        llSetting= (LinearLayout) findViewById(R.id.llSetting);
        ivLeft= (ImageView) findViewById(R.id.ivIconLeft);
        ivRight= (ImageView) findViewById(R.id.ivRight);
        tvRight= (TextView) findViewById(R.id.tvRight);
        tvCenter= (TextView) findViewById(R.id.tvCenter);

        toolbar = (Toolbar) findViewById(R.id.llToolBar);

        Window window = getWindow();
        //api level is changed to 21 here just to set the StatusBarColor accordingly
        if (user_id == null || user_id.equalsIgnoreCase("")){
            window.setStatusBarColor(getResources().getColor(R.color.colorRed));
            toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorRed));
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
        }
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
    private void fragmentCallFromMenu(String fragment){
        if(fragment!=null) {
            if(fragment.equalsIgnoreCase("exhibitorsFragment")){
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                ExhibitorsFragment exhibitorsFragment=new ExhibitorsFragment();
                onFragmentReplace(exhibitorsFragment, new Bundle());

            } else if (fragment.equalsIgnoreCase("eventFragment")) {
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                EventFragment eventFragment = new EventFragment();
                onFragmentReplace(eventFragment, new Bundle());
            } else if (fragment.equalsIgnoreCase("serviceFragment")) {
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                ServicesFragment servicesFragment = new ServicesFragment();
                onFragmentReplace(servicesFragment, new Bundle());

            } else if (fragment.equalsIgnoreCase("shuttlesFragment")) {
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                ShuttlesFragment shuttlesFragment = new ShuttlesFragment();
                onFragmentReplace(shuttlesFragment, new Bundle());
            }
        }

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
                ExhibitorsFragment exhibitorsFragment=new ExhibitorsFragment();
                onFragmentReplace(exhibitorsFragment, new Bundle());
                break;
            case R.id.llEvent:
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                EventFragment eventFragment=new EventFragment();
                onFragmentReplace(eventFragment, new Bundle());
                break;
            case R.id.llService:
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                ServicesFragment servicesFragment=new ServicesFragment();
                onFragmentReplace(servicesFragment, new Bundle());

                break;
            case R.id.llShuttles:
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                ShuttlesFragment shuttlesFragment=new ShuttlesFragment();
                onFragmentReplace(shuttlesFragment, new Bundle());


                break;
            case R.id.llFoodDrinks:
                llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
                FoodDrinksFragment foodDrinksFragment=new FoodDrinksFragment();
                onFragmentReplace(foodDrinksFragment, new Bundle());

                break;
            case R.id.llActualities:
                CommonUtils.showToast(mContext,"llActualities");
                break;
            case R.id.llAccount:
                intent=new Intent(mContext,MenuActvity.class);
                intent.putExtra("menu","accountMenu");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.llSetting:
                intent=new Intent(mContext,MenuActvity.class);
                intent.putExtra("menu","settingMenu");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.ivRight:
                intent=new Intent(mContext,SearchActvity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.ivIconLeft:
                intent=new Intent(mContext,MenuActvity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            default:
                break;
        }
    }

}

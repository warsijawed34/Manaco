package com.manaco.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.manaco.R;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;

/**
 * Created by vinove on 4/11/16.
 */

public class SearchActvity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private EditText etSearch;
    private TextView tvCancel;
    private ListPopupWindow lpwSearch;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mContext = SearchActvity.this;

        user_id = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

        initView();
        addListener();
        myToolbar();

    }

    private void initView() {
        etSearch= (EditText) findViewById(R.id.etSearch);
        tvCancel= (TextView) findViewById(R.id.tvCancel);
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
        tvCancel.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivLeft.setImageResource(R.drawable.arrowleft);
    }

    private void myToolbar() {
        ivRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvCancel:
                CommonUtils.showToast(mContext,"Cancel");
                break;
            case R.id.ivIconLeft:
                onBackPressed();
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

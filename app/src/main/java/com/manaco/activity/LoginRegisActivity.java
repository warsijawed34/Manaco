package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.utils.CommonUtils;

/**
 * Created by vinove on 2/11/16.
 */

public class LoginRegisActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private Button btnLogin,btnRegister;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reg_activity);
        mContext = LoginRegisActivity.this;
        initView();
        addListener();
    }

    private void initView() {
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        tvSkip= (TextView) findViewById(R.id.tvSkip);

    }
    private void addListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){

            case R.id.btnLogin:
                intent=new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.btnRegister:
                intent=new Intent(mContext,RegistrationActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.tvSkip:
                intent=new Intent(mContext,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                finish();
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

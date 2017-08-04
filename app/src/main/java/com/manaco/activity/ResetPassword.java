package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.network.ConnectionDetector;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.WebServiceApis;

import org.json.JSONObject;

/**
 * Created by vinove on 7/11/16.
 */

public class ResetPassword extends BaseActivity implements View.OnClickListener, OnWebServiceResult {
    Context mContext;
    EditText etEmaiAdd;
    Button btnResetPsd;
    TextView tvResetPsdLink, tvGoToLogin,tvEmailId;
    TextInputLayout tiEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpsd_activity);
        mContext = ResetPassword.this;
        initView();
        addListener();
        myToolbar();
    }

    private void initView() {
        etEmaiAdd = (EditText) findViewById(R.id.etEmailAdd);
        tiEmailId=(TextInputLayout)findViewById(R.id.tiEmailId);
        tvEmailId=(TextView)findViewById(R.id.tvEmailId);
        btnResetPsd = (Button) findViewById(R.id.btnResetPsd);
        tvGoToLogin = (TextView) findViewById(R.id.tvGoToLogin);
        tvResetPsdLink = (TextView) findViewById(R.id.tvResePsdLink);
        ivLeft = (ImageView) findViewById(R.id.ivIconLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
    }

    private void addListener() {
        btnResetPsd.setOnClickListener(this);
        tvGoToLogin.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
    }

    private void myToolbar() {
        tvCenter.setVisibility(View.INVISIBLE);
        ivRight.setVisibility(View.INVISIBLE);
        ivLeft.setImageResource(R.drawable.arrowleft);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnResetPsd:
                if (validate())
                    resetPasswordApi();
                break;
            case R.id.tvGoToLogin:
                intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
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

    private void resetPasswordApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                JSONObject jObject = new JSONObject();
                jObject.put("client_id", "f47lSVZ0FriC6L2I6FEmrvHXavNWs0NE");
                jObject.put("username", "admin");
                jObject.put("email", etEmaiAdd.getText().toString());
                jObject.put("connection", "monacoapp-users");
                System.out.println("Request: " + jObject.toString());
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).resetPasswordApi(), jObject.toString(), "POST", Constants.SERVICE_TYPE.RESETPASSWORD, this,"");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //validation method
    public boolean validate() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmaiAdd.getText().toString()).matches()) {
            tvEmailId.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            if (etEmaiAdd.getText().toString().isEmpty()) {
                tiEmailId.setError(getResources().getString(R.string.enterEmailAddress));
                return false;
            }
            tiEmailId.setError(getResources().getString(R.string.validateEmailAdd));
            return false;
        }
        else {
            tvEmailId.setTextColor(Color.WHITE);
            tiEmailId.setError("");
        }

        return true;
    }

    @Override
    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type,int responseCode) {
        try {
            etEmaiAdd.setText("");
            tvResetPsdLink.setVisibility(View.VISIBLE);
//            tvResetPsdLink.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
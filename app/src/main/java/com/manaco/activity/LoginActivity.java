package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.network.ConnectionDetector;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constant;
import com.manaco.utils.Constants;
import com.manaco.utils.GPSTracker;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vinove on 2/11/16.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    private EditText etEmail, etPassword;
    private TextView tvForgotPsd, tvEmailId, tvPassword;
    private Button btnLogin;
    private LinearLayout llErrorMsg;
    private RelativeLayout rlLogin;
    private TextInputLayout tiEmailId, tiPassword;
    private ImageView ivBack;


    private String id_token = "";
    private String access_token = "";
    private String token_type = "";
    private String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = LoginActivity.this;
        initView();
        addListener();
    }

    private void initView() {
        etEmail = (EditText) findViewById(R.id.etEmailAdd);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tiEmailId = (TextInputLayout) findViewById(R.id.tiEmailId);
        tiPassword = (TextInputLayout) findViewById(R.id.tiPassword);
        tvForgotPsd = (TextView) findViewById(R.id.tvForgotPsd);
        tvEmailId = (TextView) findViewById(R.id.tvEmailId);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        llErrorMsg = (LinearLayout) findViewById(R.id.llErrorMsg);
        rlLogin = (RelativeLayout) findViewById(R.id.rl_login_id);
        ivBack = (ImageView) findViewById(R.id.ivBack);


    }

    private void addListener() {
        ivBack.setOnClickListener(this);
        tvForgotPsd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        etEmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:
               onBackPressed();
            /*    GPSTracker gps = new GPSTracker(mContext);
                gps.showSettingsAlert();*/
                break;
            case R.id.tvForgotPsd:
                intent = new Intent(mContext, ResetPassword.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.btnLogin:
                if (validate()) {
                    LoginApi();
                } else {
                    llErrorMsg.setVisibility(View.INVISIBLE);
                    rlLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.langBG));
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                }
                break;
            case R.id.etEmailAdd:
                llErrorMsg.setVisibility(View.INVISIBLE);
                rlLogin.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
                btnLogin.setBackground(getResources().getDrawable(R.drawable.rounded_button));
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

    private void LoginApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                JSONObject jObject = new JSONObject();
                jObject.put("client_id", "f47lSVZ0FriC6L2I6FEmrvHXavNWs0NE");
                jObject.put("username", etEmail.getText().toString());
                jObject.put("password", etPassword.getText().toString());
                jObject.put("id_token", "");
                jObject.put("connection", "monacoapp-users");
                jObject.put("grant_type", "password");
                jObject.put("scope", "openid");
                jObject.put("device", "");
                System.out.println("Request: " + jObject.toString());
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).loginApi(), jObject.toString(), "POST", Constants.SERVICE_TYPE.SIGNIN, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void userDetailApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).getUserProfileDetailApi(user_id), "", "GET", Constants.SERVICE_TYPE.USER_DETAIL, this, id_token);
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://monacoapp.eu.auth0.com/tokeninfo
    private void tokenInfoApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                JSONObject jObject = new JSONObject();
                jObject.put("id_token", id_token);
                jObject.put("access_token", access_token);
                jObject.put("token_type", token_type);

                System.out.println("Request: " + jObject.toString());
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).tokenInfoApi(), jObject.toString(), "POST", Constants.SERVICE_TYPE.TOKEN_INFO, this, "");
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
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            tvEmailId.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            if (etEmail.getText().toString().isEmpty()) {
                tiEmailId.setError(getResources().getString(R.string.enterEmailAddress));
                return false;
            }
            tiEmailId.setError(getResources().getString(R.string.validateEmailAdd));
            return false;
        } else {
            tvEmailId.setTextColor(Color.WHITE);
            tiEmailId.setError("");
        }
        if (etPassword.getText().toString().trim().isEmpty()) {
            tvPassword.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            tiPassword.setError(getResources().getString(R.string.enterPassword));
            return false;
        } else {
            tvPassword.setTextColor(Color.WHITE);
            tiPassword.setError("");
        }
        return true;
    }

    @Override
    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type, int responseCode) {

        switch (type) {
            case SIGNIN:
                try {
                    if (responseCode == 200) {
                        JSONObject jsonObj = new JSONObject(result);
                        id_token = JSONUtils.getStringFromJSON(jsonObj, "id_token");
                        access_token = JSONUtils.getStringFromJSON(jsonObj, "access_token");
                        token_type = JSONUtils.getStringFromJSON(jsonObj, "token_type");
                        SharedPreferencesManger.setPrefValue(mContext, Constants.TOKEN_ID, id_token, SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                        SharedPreferencesManger.setPrefValue(mContext, Constants.ACCESS_TOKEN, access_token, SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                        SharedPreferencesManger.setPrefValue(mContext, Constants.TOKEN_TYPE, token_type, SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                        tokenInfoApi();
                    } else {
                        JSONObject jsonObj = new JSONObject(result);
                        llErrorMsg.setVisibility(View.VISIBLE);
                        rlLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
                        btnLogin.setBackground(getResources().getDrawable(R.drawable.rounderror_button));
                        CommonUtils.showToast(mContext, JSONUtils.getStringFromJSON(jsonObj, "error_description"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TOKEN_INFO:
                try {
                    if (responseCode == 200) {
                        JSONObject jsonObj = new JSONObject(result);
                        user_id = JSONUtils.getStringFromJSON(jsonObj, "user_id");
                        userDetailApi();
                    } else {
                        llErrorMsg.setVisibility(View.VISIBLE);
                        rlLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
                        btnLogin.setBackground(getResources().getDrawable(R.drawable.rounderror_button));
                        CommonUtils.showToast(mContext, getResources().getString(R.string.login_error));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case USER_DETAIL:
                try {
                    if (responseCode == 200) {
                        CommonUtils.showToast(mContext, getResources().getString(R.string.login_successfully));
                        JSONObject jsonObject = new JSONObject(result);

                        JSONArray jsonArray = jsonObject.getJSONArray("users");
                        JSONObject userJsonArray = jsonArray.getJSONObject(0);
                        SharedPreferencesManger.setPrefValue(mContext, Constants.USER_ID, JSONUtils.getStringFromJSON(userJsonArray, "id"), SharedPreferencesManger.PREF_DATA_TYPE.STRING);

                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, R.anim.exit_slide_right);
                        finish();
                    } else {
                        llErrorMsg.setVisibility(View.VISIBLE);
                        rlLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
                        btnLogin.setBackground(getResources().getDrawable(R.drawable.rounderror_button));
                        CommonUtils.showToast(mContext, getResources().getString(R.string.login_error));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
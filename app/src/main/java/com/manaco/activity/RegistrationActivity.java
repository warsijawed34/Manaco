package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.adapter.ListPopupWindowAdapter;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.ListModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vinove on 2/11/16.
 */

public class RegistrationActivity extends BaseActivity implements View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    private EditText etUserName, etEmailAdd, etPassword, etConfirmPass, etAge, etGender;
    private Button btnCreateAcc;
    private ListPopupWindow lpwGender, lpwAge;
    TextInputLayout tiUserName, tiEmailAdd, tiPassword, tiConfirmPass, tiAge, tiGender;
    TextView tvUserName, tvEmailAdd, tvPassword, tvConfirmPass, tvAge, tvGender;

    String auth0Id = "";
    private ArrayList<ListModel> genderArrayList = new ArrayList<>();
    private ArrayList<ListModel> ageArrayList = new ArrayList<>();
    private ListPopupWindowAdapter listPopupWindowAdapter;
    private String genderId, ageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        mContext = RegistrationActivity.this;
        initView();
        addListener();
        myToolbar();

        genderApi();
        ageApi();

    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmailAdd = (EditText) findViewById(R.id.etEmailAdd);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPassword);
        etAge = (EditText) findViewById(R.id.etAge);
        etGender = (EditText) findViewById(R.id.etGender);

        tiUserName = (TextInputLayout) findViewById(R.id.tiEtUserName);
        tiEmailAdd = (TextInputLayout) findViewById(R.id.tiEtEmailAdd);
        tiPassword = (TextInputLayout) findViewById(R.id.tiEtPassword);
        tiConfirmPass = (TextInputLayout) findViewById(R.id.tiEtConfirmPassword);
        tiAge = (TextInputLayout) findViewById(R.id.tiEtAge);
        tiGender = (TextInputLayout) findViewById(R.id.tiEtGender);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvEmailAdd = (TextView) findViewById(R.id.tvEmailAdd);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvConfirmPass = (TextView) findViewById(R.id.tvConfirmPass);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvGender = (TextView) findViewById(R.id.tvGender);

        btnCreateAcc = (Button) findViewById(R.id.btnCreatAcc);

        ivLeft = (ImageView) findViewById(R.id.ivIconLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvCenter = (TextView) findViewById(R.id.tvCenter);

    }

    private void addListener() {
        ivLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        btnCreateAcc.setOnClickListener(this);
        etGender.setOnClickListener(this);
        etAge.setOnClickListener(this);
    }


    private void myToolbar() {
        tvCenter.setVisibility(View.INVISIBLE);
        ivRight.setVisibility(View.INVISIBLE);
        ivLeft.setImageResource(R.drawable.arrowleft);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivIconLeft:
                onBackPressed();
                break;
            case R.id.btnCreatAcc:
                if (validate()) {
                    createAuth0Api();
                }
                break;
            case R.id.etGender:
                lpwGender.setAnchorView(etGender);
                lpwGender.setModal(true);
                lpwGender.show();
                break;
            case R.id.etAge:
                lpwAge.setAnchorView(etAge);
                lpwAge.setModal(true);
                lpwAge.show();
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

    private void createAuth0Api() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                JSONObject jObject = new JSONObject();
                jObject.put("client_id", "f47lSVZ0FriC6L2I6FEmrvHXavNWs0NE");
                jObject.put("email", etEmailAdd.getText().toString());
                jObject.put("username", etUserName.getText().toString());
                jObject.put("password", etPassword.getText().toString());
                jObject.put("connection", "monacoapp-users");
                JSONObject userMetadata = new JSONObject();
                userMetadata.put("gender_id", genderId);
                userMetadata.put("agegroup_id", ageId);
                userMetadata.put("language_id", SharedPreferencesManger.getPrefValue(mContext, Constants.LANGID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString());
                jObject.put("user_metadata", userMetadata);
                System.out.println("Request: " + jObject.toString());
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).callApi(1), jObject.toString(), "POST", Constants.SERVICE_TYPE.AUTH_SIGNUP, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signUpApi(String auth0Id) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                JSONObject jObject = new JSONObject();
                jObject.put("email", etEmailAdd.getText().toString());
                jObject.put("username", etUserName.getText().toString());
                jObject.put("password", etPassword.getText().toString());
                jObject.put("auth0_id", auth0Id);
                jObject.put("gender_id", genderId);
                jObject.put("agegroup_id", ageId);
                jObject.put("language_id", SharedPreferencesManger.getPrefValue(mContext, Constants.LANGID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString());
                System.out.println("Request: " + jObject.toString());
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).callApi(0), jObject.toString(), "POST", Constants.SERVICE_TYPE.SIGNUP, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genderApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).gendersApi(), "", "GET", Constants.SERVICE_TYPE.GENDERS_LIST, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ageApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).ageApi(), "", "GET", Constants.SERVICE_TYPE.AGE_LIST, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type, int responseCode) {
        System.out.print("ResultType:" + result);
        switch (type) {
            case AUTH_SIGNUP:
                try {
                    if (responseCode == 200) {
                        JSONObject jsonObject = new JSONObject(result);
                        auth0Id = jsonObject.getString("_id");
                        signUpApi(auth0Id);
                    } else {
                        JSONObject jsonObject = new JSONObject(result);
                        tvUserName.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
                        tiUserName.setError(JSONUtils.getStringFromJSON(jsonObject, "description"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SIGNUP:
                try {

                    if (responseCode == 200) {

                        CommonUtils.showToast(mContext, getResources().getString(R.string.registered_successfully));
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, R.anim.exit_slide_right);
                        finish();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case GENDERS_LIST:
                try {
                    if (responseCode == 200) {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray gendersAndAgeJsonArray = null;
                        gendersAndAgeJsonArray = jsonObject.getJSONArray("genders");
                        for (int i = 0; i < gendersAndAgeJsonArray.length(); i++) {
                            JSONObject jObject = gendersAndAgeJsonArray.getJSONObject(i);
                            ListModel listModel = new ListModel();
                            listModel.setId(JSONUtils.getStringFromJSON(jObject, "id"));
                            listModel.setName(JSONUtils.getStringFromJSON(jObject, "name"));
                            listModel.setCreated_at(JSONUtils.getStringFromJSON(jObject, "created_at"));
                            listModel.setUpdated_at(JSONUtils.getStringFromJSON(jObject, "updated_at"));
                            listModel.setName_fr(JSONUtils.getStringFromJSON(jObject, "name_fr"));
                            listModel.setName_it(JSONUtils.getStringFromJSON(jObject, "name_it"));
                            genderArrayList.add(listModel);

                        }
                        listPopupWindowAdapter = new ListPopupWindowAdapter(mContext, genderArrayList);
                        lpwGender = new ListPopupWindow(mContext);
                        lpwGender.setAdapter(listPopupWindowAdapter);
                        itemClickListner();
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            case AGE_LIST:
                try {
                    if (responseCode == 200) {

                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray gendersAndAgeJsonArray = null;
                        gendersAndAgeJsonArray = jsonObject.getJSONArray("agegroups");
                        for (int i = 0; i < gendersAndAgeJsonArray.length(); i++) {
                            JSONObject jObject = gendersAndAgeJsonArray.getJSONObject(i);
                            ListModel listModel = new ListModel();
                            listModel.setId(JSONUtils.getStringFromJSON(jObject, "id"));
                            listModel.setName(JSONUtils.getStringFromJSON(jObject, "name"));
                            listModel.setCreated_at(JSONUtils.getStringFromJSON(jObject, "created_at"));
                            listModel.setUpdated_at(JSONUtils.getStringFromJSON(jObject, "updated_at"));
                            listModel.setName_fr(JSONUtils.getStringFromJSON(jObject, "name_fr"));
                            listModel.setName_it(JSONUtils.getStringFromJSON(jObject, "name_it"));
                            ageArrayList.add(listModel);

                        }
                        listPopupWindowAdapter = new ListPopupWindowAdapter(mContext, ageArrayList);
                        lpwAge = new ListPopupWindow(mContext);
                        lpwAge.setAdapter(listPopupWindowAdapter);
                        itemClickListner();
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public void itemClickListner() {
        lpwGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                genderId = genderArrayList.get(position).getId();
                etGender.setText(genderArrayList.get(position).getName());
                lpwGender.dismiss();
            }
        });
        lpwAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageId = ageArrayList.get(position).getId();
                etAge.setText(ageArrayList.get(position).getName());
                lpwAge.dismiss();
            }
        });


    }


    //validation method
    public boolean validate() {

        if (etUserName.getText().toString().trim().isEmpty() || etUserName.length() < 3) {
            tvUserName.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            if (etUserName.getText().toString().trim().isEmpty()) {
                tiUserName.setError(getResources().getString(R.string.enterUserName));
                return false;
            }
            tiUserName.setError(getResources().getString(R.string.validateUserName));
            return false;
        } else {
            tvUserName.setTextColor(Color.WHITE);
            tiUserName.setError("");
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmailAdd.getText().toString()).matches()) {
            tvEmailAdd.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            if (etEmailAdd.getText().toString().isEmpty()) {
                tiEmailAdd.setError(getResources().getString(R.string.enterEmailAddress));
                return false;
            }
            tiEmailAdd.setError(getResources().getString(R.string.validateEmailAdd));
            return false;
        } else {
            tvEmailAdd.setTextColor(Color.WHITE);
            tiEmailAdd.setError("");
        }
        if (etPassword.getText().toString().trim().isEmpty() || etPassword.length() < 8 || etPassword.length() > 25) {
            tvPassword.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            if (etPassword.getText().toString().trim().isEmpty()) {
                tiPassword.setError(getResources().getString(R.string.enterPassword));
                return false;
            }
            tiPassword.setError(getResources().getString(R.string.validatePassword));
            return false;
        } else {
            tvPassword.setTextColor(Color.WHITE);
            tiPassword.setError("");
        }
        if (etConfirmPass.getText().toString().trim().isEmpty() || etConfirmPass.length() < 8 || etConfirmPass.length() > 25) {
            tvConfirmPass.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            if (etConfirmPass.getText().toString().trim().isEmpty()) {
                tiConfirmPass.setError(getResources().getString(R.string.enterConfirmPass));
                return false;
            }
            tiConfirmPass.setError(getResources().getString(R.string.validateConfirmPassword));
            return false;
        } else {
            tvConfirmPass.setTextColor(Color.WHITE);
            tiConfirmPass.setError("");
        }

        if (!etPassword.getText().toString().trim().equalsIgnoreCase(etConfirmPass.getText().toString().trim())) {
            tvConfirmPass.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            tiConfirmPass.setError(getResources().getString(R.string.matchConfirmPassword));
            return false;
        } else {
            tvConfirmPass.setTextColor(Color.WHITE);
            tiConfirmPass.setError("");
        }

        if (etAge.getText().toString().trim().isEmpty()) {
            tvAge.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            tiAge.setError(getResources().getString(R.string.enterAge));
            return false;
        } else {
            tvAge.setTextColor(Color.WHITE);
            tiAge.setError("");
        }
        if (etGender.getText().toString().trim().isEmpty()) {
            tvGender.setTextColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
            tiGender.setError(getResources().getString(R.string.enterGender));
            return false;
        } else {
            tvGender.setTextColor(Color.WHITE);
            tiGender.setError("");
        }
        return true;
    }

}
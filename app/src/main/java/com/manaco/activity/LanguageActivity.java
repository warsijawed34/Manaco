package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.manaco.R;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.ListModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CallWebService;
import com.manaco.utils.ChangeLanguage;
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

public class LanguageActivity extends BaseActivity implements View.OnClickListener , OnWebServiceResult {
    private Context mContext;
    private Button btnEnlish, btnFrench, btnItaliano;
    private ArrayList<ListModel> languageArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_activity);
        mContext = LanguageActivity.this;
        initView();
        addListener();
        languageApi();

    }

    private void initView() {
        btnEnlish = (Button) findViewById(R.id.btnEnglish);
        btnFrench = (Button) findViewById(R.id.btnFrench);
        btnItaliano = (Button) findViewById(R.id.btnItaliano);
    }

    private void addListener() {
        btnEnlish.setOnClickListener(this);
        btnFrench.setOnClickListener(this);
        btnItaliano.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnEnglish:
                SharedPreferencesManger.setPrefValue(mContext, Constants.LANGID, languageArrayList.get(0).getId(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                SharedPreferencesManger.setPrefValue(mContext, Constants.LANGNAME, languageArrayList.get(0).getName(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                ChangeLanguage.setLanguage(mContext, "en");
                intent=new Intent(mContext,LoginRegisActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.btnFrench:
                SharedPreferencesManger.setPrefValue(mContext, Constants.LANGID, languageArrayList.get(1).getId(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                SharedPreferencesManger.setPrefValue(mContext, Constants.LANGNAME, languageArrayList.get(1).getName(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                ChangeLanguage.setLanguage(mContext, "fr");
                intent=new Intent(mContext,LoginRegisActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.btnItaliano:
                SharedPreferencesManger.setPrefValue(mContext, Constants.LANGID, languageArrayList.get(2).getId(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                SharedPreferencesManger.setPrefValue(mContext, Constants.LANGNAME, languageArrayList.get(2).getName(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                ChangeLanguage.setLanguage(mContext, "it");
                intent=new Intent(mContext,LoginRegisActivity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            default:
                break;
        }
    }

    private void languageApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).languageApi(), "", "GET", Constants.SERVICE_TYPE.LANGUAGE_LIST, this,"");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type,int responseCode) {
        System.out.print("ResultType:" + result);
        switch (type) {
            case LANGUAGE_LIST:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray languagesJsonArray = jsonObject.getJSONArray("languages");
                    for (int i = 0; i < languagesJsonArray.length(); i++) {

                        JSONObject jObject = languagesJsonArray.getJSONObject(i);

                        ListModel listModel = new ListModel();
                        listModel.setId(JSONUtils.getStringFromJSON(jObject, "id"));
                        listModel.setName(JSONUtils.getStringFromJSON(jObject, "name"));
                        listModel.setCreated_at(JSONUtils.getStringFromJSON(jObject, "created_at"));
                        listModel.setUpdated_at(JSONUtils.getStringFromJSON(jObject, "updated_at"));
                        languageArrayList.add(listModel);
                        setView(languageArrayList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    public void setView(ArrayList<ListModel> languageArrayList)
    {
        int id[]={R.id.btnEnglish, R.id.btnFrench, R.id.btnItaliano};

        for (int i=0; i<languageArrayList.size(); i++)
        {
            ((Button) findViewById(id[i])).setText(languageArrayList.get(i).getName());
        }

    }
}

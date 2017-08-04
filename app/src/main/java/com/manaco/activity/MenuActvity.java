package com.manaco.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.manaco.R;
import com.manaco.adapter.ExhibithorsMenuAdater;
import com.manaco.adapter.ListPopupWindowAdapter;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.ExpandableListDataPump;
import com.manaco.model.ListModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CallWebService;
import com.manaco.utils.ChangeLanguage;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.ExpandableHeight;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vinove on 16/11/16.
 */

public class MenuActvity extends BaseActivity implements View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    private RelativeLayout rlExhibithors,rlEVent,rlServices,rlShuttles,rlFoodnDrinks,rlActualites,rlAccount,rlSetting;
    private RelativeLayout rlAccInclude,rlSettingInclude;
    private TextView tvChangeUser, tvChangePass, tvChangeLanguage;
    private ToggleButton tbNotis,tbPedoMeter;
    private String yesNoNotis,yesNoPedoMeter,langName;
    private ScrollView iScroll;
    private ArrayList<ListModel> languageArrayList=new ArrayList<>();
    private Intent intent;
    private ExpandableHeight expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private String user_id;
    private Toolbar toolbarRedBlue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        mContext = MenuActvity.this;

        user_id = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

        initView();
        addListener();
        myToolbar();
        utilitiesMethod();
        languageApi();
        
    }

    private void initView() {
        ivLeft= (ImageView) findViewById(R.id.ivIconLeft);
        ivRight= (ImageView) findViewById(R.id.ivRight);
        tvRight= (TextView) findViewById(R.id.tvRight);
        tvCenter= (TextView) findViewById(R.id.tvCenter);
        rlExhibithors= (RelativeLayout) findViewById(R.id.rlExhibithors);
        rlEVent= (RelativeLayout) findViewById(R.id.rlEVent);
        rlServices= (RelativeLayout) findViewById(R.id.rlServices);
        rlShuttles= (RelativeLayout) findViewById(R.id.rlShuttles);
        rlFoodnDrinks= (RelativeLayout) findViewById(R.id.rlFoodnDrinks);
        rlActualites= (RelativeLayout) findViewById(R.id.rlActualites);
        rlAccount= (RelativeLayout) findViewById(R.id.rlAccount);
        rlSetting= (RelativeLayout) findViewById(R.id.rlSetting);
        rlAccInclude= (RelativeLayout) findViewById(R.id.rlAccInclude);
        rlSettingInclude= (RelativeLayout) findViewById(R.id.rlSettingInclude);
        tvChangeUser= (TextView) findViewById(R.id.tvChangeUser);
        tvChangePass= (TextView) findViewById(R.id.tvChangePass);
        tvChangeLanguage= (TextView) findViewById(R.id.tvChangeLanguage);
        tbNotis= (ToggleButton) findViewById(R.id.tbNotis);
        tbPedoMeter= (ToggleButton) findViewById(R.id.tbPedoMeter);
        iScroll = (ScrollView) findViewById(R.id.scrollDown);
        expandableListView = (ExpandableHeight) findViewById(R.id.expandableListView);

        toolbarRedBlue = (Toolbar) findViewById(R.id.llToolBar);

        Window window = getWindow();
        //api level is changed to 21 here just to set the StatusBarColor accordingly
        if (user_id == null || user_id.equalsIgnoreCase("")){
            window.setStatusBarColor(getResources().getColor(R.color.colorRed));
            toolbarRedBlue.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorRed));
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            toolbarRedBlue.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
        }

    }

    private void addListener() {
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        rlExhibithors.setOnClickListener(this);
        rlEVent.setOnClickListener(this);
        rlServices.setOnClickListener(this);
        rlShuttles.setOnClickListener(this);
        rlFoodnDrinks.setOnClickListener(this);
        rlActualites.setOnClickListener(this);
        rlAccount.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        tvChangeUser.setOnClickListener(this);
        tvChangePass.setOnClickListener(this);
        tvChangeLanguage.setOnClickListener(this);
        tbNotis.setOnClickListener(this);
        tbPedoMeter.setOnClickListener(this);

    }

    private void myToolbar() {

    }
    private void utilitiesMethod(){
        Intent intent=getIntent();
        String menu=intent.getStringExtra("menu");
        if(menu!=null){
            if(menu.equalsIgnoreCase("accountMenu")){
                rlAccInclude.setVisibility(View.VISIBLE);
                rlSettingInclude.setVisibility(View.GONE);
                iScroll.post(new Runnable()
                {
                    public void run()
                    {
                        iScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }else if (menu.equalsIgnoreCase("settingMenu")){
                rlSettingInclude.setVisibility(View.VISIBLE);
                rlAccInclude.setVisibility(View.GONE);
                iScroll.post(new Runnable()
                {
                    public void run()
                    {
                        iScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }
        langName= SharedPreferencesManger.getPrefValue(mContext, Constants.LANGNAME, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        tvChangeLanguage.setText(langName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivIconLeft:
                onBackPressed();
                break;
            case R.id.ivRight:
                intent=new Intent(mContext,SearchActvity.class);
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.rlExhibithors:
                expandableListView.setVisibility(View.VISIBLE);
                rlAccInclude.setVisibility(View.GONE);
                rlSettingInclude.setVisibility(View.GONE);
                expandableListView.setExpanded(true);
                expandableListDetail = ExpandableListDataPump.getData();
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new ExhibithorsMenuAdater(this, expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
                break;
            case R.id.rlEVent:
                intent=new Intent(mContext,HomeActivity.class);
                intent.putExtra("fragment","eventFragment");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.rlServices:
                intent=new Intent(mContext,HomeActivity.class);
                intent.putExtra("fragment","serviceFragment");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.rlShuttles:
                intent=new Intent(mContext,HomeActivity.class);
                intent.putExtra("fragment","shuttlesFragment");
                startActivity(intent);
                overridePendingTransition(0, R.anim.exit_slide_right);
                break;
            case R.id.rlFoodnDrinks:
                CommonUtils.showToast(mContext,"rlFoodnDrinks");
                break;
            case R.id.rlActualites:
                CommonUtils.showToast(mContext,"rlActualites");
                break;
            case R.id.rlAccount:
                rlAccInclude.setVisibility(View.VISIBLE);
                rlSettingInclude.setVisibility(View.GONE);
                expandableListView.setVisibility(View.GONE);
                iScroll.post(new Runnable()
                {
                    public void run()
                    {
                        iScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
                break;
            case R.id.rlSetting:
                rlSettingInclude.setVisibility(View.VISIBLE);
                rlAccInclude.setVisibility(View.GONE);
                expandableListView.setVisibility(View.GONE);
                iScroll.post(new Runnable()
                {
                    public void run()
                    {
                        iScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
                break;
            case R.id.tvChangeUser:
                intent=new Intent(mContext,LoginRegisActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.tvChangePass:
                intent=new Intent(mContext,LoginRegisActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.tvChangeLanguage:
                showDialog(languageArrayList);
                break;
            case R.id.tbNotis:
                if(tbNotis.isChecked()){
                    tbNotis.setBackgroundResource(R.drawable.on_button);
                    yesNoNotis="yes";
                    CommonUtils.showToast(mContext,yesNoNotis);

                }else {
                    tbNotis.setBackgroundResource(R.drawable.on_button);
                    yesNoNotis="no";
                    CommonUtils.showToast(mContext,yesNoNotis);
                }
                break;
            case R.id.tbPedoMeter:
                if(tbPedoMeter.isChecked()){
                    tbPedoMeter.setBackgroundResource(R.drawable.on_button);
                    yesNoPedoMeter="yes";
                    CommonUtils.showToast(mContext,yesNoPedoMeter);

                }else {
                    tbPedoMeter.setBackgroundResource(R.drawable.on_button);
                    yesNoPedoMeter="no";
                    CommonUtils.showToast(mContext,yesNoPedoMeter);
                }
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.exit_slide_left);
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void showDialog(final ArrayList<ListModel> languageArrayList) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.list_dialog, null);
        ListView lvLanguages = (ListView) view.findViewById(R.id.list_item);
        ListPopupWindowAdapter adapter = new ListPopupWindowAdapter(mContext, languageArrayList);
        lvLanguages.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.show();
        lvLanguages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvChangeLanguage.setText(languageArrayList.get(i).getName());
                if(languageArrayList.get(i).getId().equalsIgnoreCase("1")){
                    SharedPreferencesManger.setPrefValue(mContext, Constants.LANGNAME, languageArrayList.get(i).getName(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                    ChangeLanguage.setLanguage(mContext,"en");
                    intent = getIntent();
                    finish();
                    startActivity(intent);
                } else if(languageArrayList.get(i).getId().equalsIgnoreCase("2")){
                    SharedPreferencesManger.setPrefValue(mContext, Constants.LANGNAME, languageArrayList.get(i).getName(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                    ChangeLanguage.setLanguage(mContext,"fr");
                    intent = getIntent();
                    finish();
                    startActivity(intent);

                } else if(languageArrayList.get(i).getId().equalsIgnoreCase("3")){
                    SharedPreferencesManger.setPrefValue(mContext, Constants.LANGNAME, languageArrayList.get(i).getName(), SharedPreferencesManger.PREF_DATA_TYPE.STRING);
                    ChangeLanguage.setLanguage(mContext,"it");
                    intent = getIntent();
                    finish();
                    startActivity(intent);
                }

                dialog.dismiss();
            }
        });
    }
}

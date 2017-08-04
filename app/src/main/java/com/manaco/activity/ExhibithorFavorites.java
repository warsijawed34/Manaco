package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;
import com.manaco.adapter.ExhibithorsFavoritesAdapter;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.StandsModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by vinove on 18/11/16.
 */

public class ExhibithorFavorites extends BaseActivity implements View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExhibithorsFavoritesAdapter exFavAdapter;
    private String tokenId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibithor_favorites);
        mContext = ExhibithorFavorites.this;
        initView();
        addListener();
        myToolbar();
        tokenId = SharedPreferencesManger.getPrefValue(mContext, Constants.TOKEN_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        userId = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
       standFavStandInterestedApi(tokenId, userId);
      //  standFavEventsInterestedApi(tokenId, userId);

    }

    private void initView() {

        ivLeft = (ImageView) findViewById(R.id.ivIconLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvExFavorites);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        exFavAdapter = new ExhibithorsFavoritesAdapter(ExhibithorFavorites.this);

    }

    private void addListener() {
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);

        ((ExhibithorsFavoritesAdapter) exFavAdapter).setOnItemClickListener(new ExhibithorsFavoritesAdapter.FavClickListner() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent=new Intent(mContext,ExhibitorMapDetails.class);
                intent.putExtra("exhibitorId", exFavAdapter.getItem(position).getId());
                startActivity(intent);

            }
        });
    }

    private void myToolbar() {
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivIconLeft:
                onBackPressed();
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

    private void standFavStandInterestedApi(String tokenId, String userId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).standFavInterested(userId), "", "GET", Constants.SERVICE_TYPE.STANDS_FAV_INTERESTED, this, tokenId);
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void standFavEventsInterestedApi(String tokenId, String userId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).eventsFavInterested(userId), "", "GET", Constants.SERVICE_TYPE.EVENTS_FAV_INTERESTED, this, tokenId);
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
        switch (type) {
            case STANDS_FAV_INTERESTED:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray standJsonArray = jsonObject.getJSONArray("stands");
                    for (int i = 0; i < standJsonArray.length(); i++) {
                        JSONObject standJsonObject = standJsonArray.getJSONObject(i);
                        StandsModel standsModel = new StandsModel();
                        standsModel.setId(JSONUtils.getStringFromJSON(standJsonObject, "id"));
                        standsModel.setName(JSONUtils.getStringFromJSON(standJsonObject, "name"));
                        standsModel.setDescription(JSONUtils.getStringFromJSON(standJsonObject, "description"));
                        standsModel.setDescription_fr(JSONUtils.getStringFromJSON(standJsonObject, "description_fr"));
                        standsModel.setDescription_it(JSONUtils.getStringFromJSON(standJsonObject, "description_it"));
                        standsModel.setCreated_at(JSONUtils.getStringFromJSON(standJsonObject, "created_at"));
                        standsModel.setUpdated_at(JSONUtils.getStringFromJSON(standJsonObject, "updated_at"));
                        standsModel.setCategory_id(JSONUtils.getStringFromJSON(standJsonObject, "category_id"));
                        standsModel.setLogo_path(JSONUtils.getStringFromJSON(standJsonObject, "logo_path"));
                        standsModel.setStand_ref(JSONUtils.getStringFromJSON(standJsonObject, "stand_ref"));
                        standsModel.setWebsite_url(JSONUtils.getStringFromJSON(standJsonObject, "website_url"));
                        standsModel.setAddress_id(JSONUtils.getStringFromJSON(standJsonObject, "address_id"));
                        standsModel.setStatus_id(JSONUtils.getStringFromJSON(standJsonObject, "status_id"));
                        standsModel.setDeleted_at(JSONUtils.getStringFromJSON(standJsonObject, "deleted_at"));

                        JSONObject jsonObject1 = standJsonObject.getJSONObject("pivot");
                        standsModel.setUser_id(JSONUtils.getStringFromJSON(jsonObject1, "user_id"));
                        standsModel.setStand_id(JSONUtils.getStringFromJSON(jsonObject1, "stand_id"));
                        exFavAdapter.addtoArray(standsModel);
                    }

                    mRecyclerView.setAdapter(exFavAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case EVENTS_FAV_INTERESTED:
                try{
                    JSONObject jsonObject = new JSONObject(result);

                }catch (Exception e){
                    e.printStackTrace();
                }
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

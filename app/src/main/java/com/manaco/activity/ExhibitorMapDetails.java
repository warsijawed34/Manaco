package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manaco.R;
import com.manaco.adapter.StandsEventsAdapter;
import com.manaco.fragments.BaseFragment;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.AddressModel;
import com.manaco.model.StandsEventsModel;
import com.manaco.model.StandsInfoModel;
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

import java.util.ArrayList;

/**
 * Created by vinove on 7/11/16.
 */

public class ExhibitorMapDetails extends BaseFragment implements OnMapReadyCallback, View.OnClickListener, OnWebServiceResult {
    private GoogleMap googleMapView;
    private Context mContext;
    private LinearLayout llExhibitors, llEvent, llService, llShuttles, llFoodDrinks, llActualities, llAccount, llSetting;
    private Button btnGo;
    private TextView tvInformations, tvEvent, tvFollow, tvDescription, tvNameExb, tvNameDetailsExb;
    private ImageView ivLogoExbMapDetails, ivInfoLogo, ivOnMarker;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StandsEventsAdapter adapter;
    private ArrayList<StandsModel> standsModelArrayList = new ArrayList<>();
    private String exhibitorId;
    private LinearLayout llEventInfo, llInformations;
    private ScrollView iScroll;
    private TextView tvSituation, tvWebsiteUrl, tvTittle, tvTittleDesc, tvNumber, tvEmailAdd;
    private CameraPosition cameraPosition;
    private String userId, tokenId, langId, IsInterested="";
    private Bitmap bitmap = null;
    private View markerView;
    private String standId;
    public Toolbar toolbar;
    private String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibitors_map_details);
        mContext = ExhibitorMapDetails.this;

        user_id = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        initView();
        addListener();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewDetails);
        mapFragment.getMapAsync(this);


        llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
        llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        exhibitorId = getIntent().getStringExtra("exhibitorId");

        userId = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        tokenId = SharedPreferencesManger.getPrefValue(mContext, Constants.TOKEN_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        langId = SharedPreferencesManger.getPrefValue(mContext, Constants.LANGID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        checkUserFollowUnFollowApi(userId, exhibitorId);


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
        btnGo = (Button) findViewById(R.id.btnGo);
        ivLogoExbMapDetails = (ImageView) findViewById(R.id.iv_LogoExbMapDetails);
        tvInformations = (TextView) findViewById(R.id.tv_informations);
        tvEvent = (TextView) findViewById(R.id.tv_event);
        tvFollow = (TextView) findViewById(R.id.tv_follow);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvNameExb = (TextView) findViewById(R.id.tv_NameExb);
        tvNameDetailsExb = (TextView) findViewById(R.id.tv_NameDetailsExb);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvEvent);
        llEventInfo = (LinearLayout) findViewById(R.id.llEVentInfo);
        llInformations = (LinearLayout) findViewById(R.id.llInformations);
        iScroll = (ScrollView) findViewById(R.id.scrollDown);
        tvWebsiteUrl = (TextView) findViewById(R.id.tvWebsite);
        tvSituation = (TextView) findViewById(R.id.tvSituation);
        tvWebsiteUrl = (TextView) findViewById(R.id.tvWebsite);
        tvTittle = (TextView) findViewById(R.id.tvTittle);
        tvTittleDesc = (TextView) findViewById(R.id.tvTittleDesc);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvEmailAdd = (TextView) findViewById(R.id.tvEmailAdd);
        ivInfoLogo = (ImageView) findViewById(R.id.ivInfoLogo);

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


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new StandsEventsAdapter(ExhibitorMapDetails.this);


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
        btnGo.setOnClickListener(this);
        tvInformations.setOnClickListener(this);
        tvEvent.setOnClickListener(this);
        tvFollow.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapView = googleMap;
        homeStandDetailsApi();

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
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
            case R.id.btnGo:
                String lat = "";
                String lon = "";
                String imageUrl = "";
                for (int i = 0; i < standsModelArrayList.size(); i++) {
                    lat = standsModelArrayList.get(i).getAddressModelArrayList().get(i).getLat();
                    lon = standsModelArrayList.get(i).getAddressModelArrayList().get(i).getLng();
                    imageUrl = standsModelArrayList.get(i).getLogo_path();
                }
                intent = new Intent(mContext, NavigationExhibitorsActivity.class);
                intent.putExtra("Navigation", "ExhibitorsView");
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("exhibitorId", exhibitorId);
                intent.putExtra("markerImage", imageUrl);
                startActivity(intent);
                break;
            case R.id.tv_informations:
                llEventInfo.setVisibility(View.GONE);
                llInformations.setVisibility(View.VISIBLE);
                iScroll.post(new Runnable() {
                    public void run() {
                        iScroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
                standInformationApi(standId);
                break;
            case R.id.tv_event:
                llInformations.setVisibility(View.GONE);
                llEventInfo.setVisibility(View.VISIBLE);
                standEventsApi(standId);
                break;
            case R.id.tv_follow:
                llInformations.setVisibility(View.GONE);
                llEventInfo.setVisibility(View.GONE);
               // IsInterested = SharedPreferencesManger.getPrefValue(mContext, Constants.INTERESTED, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
                if (IsInterested.equals("1")) {
                    exhibithorsDetailsUnFollowApi();
                } else {
                    exhibithorsDetailsFollowApi();
                }

                break;
            default:
                break;
        }
    }

    private void homeStandDetailsApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).exhibitorDetailsApi(exhibitorId), "", "GET", Constants.SERVICE_TYPE.HOME_DETAILS_STANDS, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void standInformationApi(String standId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).standInformationApi(standId), "", "GET", Constants.SERVICE_TYPE.STANDS_INFO, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void standEventsApi(String standId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).standEventsApi(standId), "", "GET", Constants.SERVICE_TYPE.STANDS_EVENTS, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exhibithorsDetailsUnFollowApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis
                        (mContext).exhibithorsDetailsUnFollowApi(userId, exhibitorId), "", "POST", Constants
                        .SERVICE_TYPE.EXHIBITORS_DETAILS_UNFOLLOW, this, tokenId);
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exhibithorsDetailsFollowApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).exhibithorsDetailsFollowApi(userId, exhibitorId), "", "POST", Constants.SERVICE_TYPE.EXHIBITORS_DETAILS_FOLLOW, this, tokenId);
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkUserFollowUnFollowApi(String userId, String exhibitorId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).checkFollowUnfollow(userId, exhibitorId), "", "GET", Constants.SERVICE_TYPE.CHECKFOLLOW, this, "");
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
            case HOME_DETAILS_STANDS:
                try {
                    standsModelArrayList.clear();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject standJsonObject = jsonObject.getJSONObject("stand");

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
                    JSONArray addressJsonArray = standJsonObject.getJSONArray("address");

                    tvNameExb.setText(standsModel.getName());
                    if (langId.equals("1")) {
                        tvDescription.setText(standsModel.getDescription());
                    } else if (langId.equals("2")) {
                        tvDescription.setText(standsModel.getDescription_fr());
                    } else if (langId.equals("3")) {
                        tvDescription.setText(standsModel.getDescription_it());
                    }

                    tvWebsiteUrl.setText(standsModel.getWebsite_url());
                    tvNameDetailsExb.setText(standsModel.getName());
                    standId=standsModel.getId();


                    for (int j = 0; j < addressJsonArray.length(); j++) {
                        AddressModel addressModel = new AddressModel();
                        JSONObject addressJsonObject = addressJsonArray.getJSONObject(j);

                        addressModel.setId(JSONUtils.getStringFromJSON(addressJsonObject, "id"));
                        addressModel.setStreet(JSONUtils.getStringFromJSON(addressJsonObject, "street"));
                        addressModel.setCity(JSONUtils.getStringFromJSON(addressJsonObject, "city"));
                        addressModel.setState(JSONUtils.getStringFromJSON(addressJsonObject, "state"));
                        addressModel.setPostal_code(JSONUtils.getStringFromJSON(addressJsonObject, "postal_code"));
                        addressModel.setLat(JSONUtils.getStringFromJSON(addressJsonObject, "lat"));
                        addressModel.setLng(JSONUtils.getStringFromJSON(addressJsonObject, "lng"));
                        addressModel.setCountry_id(JSONUtils.getStringFromJSON(addressJsonObject, "country_id"));
                        addressModel.setIs_primary(JSONUtils.getStringFromJSON(addressJsonObject, "is_primary"));
                        addressModel.setIs_billing(JSONUtils.getStringFromJSON(addressJsonObject, "is_billing"));
                        addressModel.setIs_shipping(JSONUtils.getStringFromJSON(addressJsonObject, "is_shipping"));
                        addressModel.setCreated_at(JSONUtils.getStringFromJSON(addressJsonObject, "created_at"));
                        addressModel.setUpdated_at(JSONUtils.getStringFromJSON(addressJsonObject, "updated_at"));

                        standsModel.setAddressModelArrayList(addressModel);
                        tvSituation.setText(addressModel.getStreet() + " " + addressModel.getCity() + " " + addressModel.getState());

                    }

                    standsModelArrayList.add(standsModel);


                    addMarkerOnGoogleMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case STANDS_INFO:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray InfoJsonArray = jsonObject.getJSONArray("informations");
                    JSONObject object;
                    for (int i = 0; i < InfoJsonArray.length(); i++) {
                        object = InfoJsonArray.getJSONObject(i);
                        StandsInfoModel model = new StandsInfoModel();
                        model.setId(JSONUtils.getStringFromJSON(object, "id"));
                        model.setTitle(JSONUtils.getStringFromJSON(object, "title"));
                        model.setTitle_fr(JSONUtils.getStringFromJSON(object, "title_fr"));
                        model.setTitle_it(JSONUtils.getStringFromJSON(object, "title_it"));
                        model.setDescription(JSONUtils.getStringFromJSON(object, "description"));
                        model.setDescription_fr(JSONUtils.getStringFromJSON(object, "description_fr"));
                        model.setDescription_it(JSONUtils.getStringFromJSON(object, "description_it"));
                        model.setCreated_at(JSONUtils.getStringFromJSON(object, "created_at"));
                        model.setUpdated_at(JSONUtils.getStringFromJSON(object, "updated_at"));

                        if (langId.equals("1")) {
                            tvTittle.setText(model.getTitle());
                        } else if (langId.equals("2")) {
                            tvTittle.setText(model.getTitle_fr());
                        } else if (langId.equals("3")) {
                            tvTittle.setText(model.getTitle_it());
                        }
                        if (langId.equals("1")) {
                            tvTittleDesc.setText(model.getDescription());
                        } else if (langId.equals("2")) {
                            tvTittleDesc.setText(model.getDescription_fr());
                        } else if (langId.equals("3")) {
                            tvTittleDesc.setText(model.getDescription_it());
                        }


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case STANDS_EVENTS:
                try {
                    adapter.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray eventJsonArray = jsonObject.getJSONArray("events");
                    for (int i = 0; i < eventJsonArray.length(); i++) {
                        JSONObject object = eventJsonArray.getJSONObject(i);
                        StandsEventsModel model = new StandsEventsModel();
                        model.setId(JSONUtils.getStringFromJSON(object, "id"));
                        model.setName(JSONUtils.getStringFromJSON(object, "name"));
                        model.setDescription(JSONUtils.getStringFromJSON(object, "description"));
                        model.setDescription_fr(JSONUtils.getStringFromJSON(object, "description_fr"));
                        model.setDescription_it(JSONUtils.getStringFromJSON(object, "description_it"));
                        model.setStart_dateTime(JSONUtils.getStringFromJSON(object, "start_dateTime"));
                        model.setEnd_dateTime(JSONUtils.getStringFromJSON(object, "end_dateTime"));
                        model.setStand_id(JSONUtils.getStringFromJSON(object, "stand_id"));
                        model.setCreated_at(JSONUtils.getStringFromJSON(object, "created_at"));
                        model.setUpdated_at(JSONUtils.getStringFromJSON(object, "updated_at"));
                        model.setAddress_id(JSONUtils.getStringFromJSON(object, "address_id"));
                        model.setStatus_id(JSONUtils.getStringFromJSON(object, "status_id"));
                        model.setDeleted_at(JSONUtils.getStringFromJSON(object, "deleted_at"));
                        model.setNotes(JSONUtils.getStringFromJSON(object, "notes"));
                        model.setNotes_fr(JSONUtils.getStringFromJSON(object, "notes_fr"));
                        model.setNotes_it(JSONUtils.getStringFromJSON(object, "notes_it"));
                        adapter.addtoArray(model);

                    }
                    mRecyclerView.setAdapter(adapter);
                    iScroll.post(new Runnable() {
                        public void run() {
                            iScroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case EXHIBITORS_DETAILS_FOLLOW:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    tvFollow.setText(getString(R.string.unfollow));
                    tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                    tvFollow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.redheart50px, 0, 0);
                    IsInterested="1";


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case EXHIBITORS_DETAILS_UNFOLLOW:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    tvFollow.setText(getString(R.string.follow));
                    tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.langBG));
                    tvFollow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.greyheart50px, 0, 0);
                    //  CommonUtils.showToast(mContext, JSONUtils.getStringFromJSON(jsonObject, "seccess_message"));
                    IsInterested="0";

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CHECKFOLLOW:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject object = jsonObject.getJSONObject("stand_user");
                    //  String stand_id=JSONUtils.getStringFromJSON(object,"stand_id");
                    //  String user_id=JSONUtils.getStringFromJSON(object,"user_id");
                    IsInterested = JSONUtils.getStringFromJSON(object, "IsInterested");
                    if (IsInterested.equals("1")) {
                        tvFollow.setText(getString(R.string.unfollow));
                        tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                        tvFollow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.redheart50px, 0, 0);

                    } else {
                        tvFollow.setText(getString(R.string.follow));
                        tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.langBG));
                        tvFollow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.greyheart50px, 0, 0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    public void addMarkerOnGoogleMap() {
        new AsyncTaskRunner().execute();
    }


    private class AsyncTaskRunner extends AsyncTask<String, Bitmap, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            bitmap = CommonUtils.getBitmapFromURL(standsModelArrayList.get(0).getLogo_path());
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //set bitmap in details page
            ivLogoExbMapDetails.setImageBitmap(bitmap);

            markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
            ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);
            bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
            ivOnMarker.setImageBitmap(CommonUtils.getRoundedCornerBitmap(bitmap, 40));


            for (int k = 0; k < standsModelArrayList.get(0).getAddressModelArrayList().size(); k++) {
                ArrayList<AddressModel> addressModel = standsModelArrayList.get(0).getAddressModelArrayList();
                LatLng latLng = new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()));

                googleMapView.addMarker(new MarkerOptions().position(latLng).title("").icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));
                googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMapView.getUiSettings().setMyLocationButtonEnabled(true);
                cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()))).zoom(15).build();
                googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        }
    }
}
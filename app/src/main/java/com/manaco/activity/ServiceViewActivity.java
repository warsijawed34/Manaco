package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manaco.R;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.AddressModel;
import com.manaco.model.CategoryModel;
import com.manaco.model.ServiceModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vinove on 14/11/16.
 */

public class ServiceViewActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    private GoogleMap googleMapView;
    private LinearLayout llExhibitors, llEvent, llService, llShuttles, llFoodDrinks, llActualities, llAccount, llSetting;
    private Button btnGo;
    private TextView tvName;
    private TextView tvDescription;
    private String serviceId, addressStreet, serviceName;
    private CameraPosition cameraPosition;
    private String addressLatitude, addressLongitude;
    int markerposition;
    private Bitmap bitmap;
    private ArrayList<AddressModel> addressList;
    private ArrayList<CategoryModel> serviceTypeList;
    private LatLng position;
    private String serviceTypeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serviceview_activity);
        mContext = ServiceViewActivity.this;

        initView();
        addListener();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewServicesView);
        mapFragment.getMapAsync(this);

        llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
        llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));


        serviceId = getIntent().getExtras().getString("serviceId");
        serviceName = getIntent().getExtras().getString("serviceName");

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
        tvName = (TextView) findViewById(R.id.tvName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);


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

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapView = googleMap;

        serviceViewApi(serviceId);
        serviceTypeViewApi();

    }

    private void serviceViewApi(String serviceId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis
                        (mContext).serviceViewApi(serviceId), "", "GET", Constants.SERVICE_TYPE.SERVICEVIEW,
                        this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void serviceTypeViewApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis
                        (mContext).serviceTypeViewApi(), "", "GET", Constants.SERVICE_TYPE
                        .SERVICES_TYPE,
                        this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                for (int i = 0; i < addressList.size(); i++) {
                    lat = addressList.get(i).getLat();
                    lon = addressList.get(i).getLng();
                }

                intent = new Intent(mContext, NavigationExhibitorsActivity.class);
                intent.putExtra("Navigation", "serviceView");
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("serviceID", serviceId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type, int responseCode) {
        switch (type) {
            case SERVICEVIEW:
                try {
                    addressList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);

                    ServiceModel serviceModel = new ServiceModel();
                    JSONObject serviceJsonObject = jsonObject.getJSONObject("service");


                    serviceTypeId = JSONUtils.getStringFromJSON(serviceJsonObject,
                            "service_type_id");

                    JSONArray addressJsonArray = serviceJsonObject.getJSONArray("address");

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
                        addressList.add(addressModel);
                    }


                    tvName.setText(JSONUtils.getStringFromJSON(serviceJsonObject, "name"));


                    for (int i = 0; i < addressList.size(); i++) {
                        if (JSONUtils.getStringFromJSON(serviceJsonObject, "address_id").contains(addressList.get(i).getId())) {
                            addressStreet = addressList.get(i).getStreet();
                            addressLatitude = addressList.get(i).getLat();
                            addressLongitude = addressList.get(i).getLng();
                        }
                    }

                    if (serviceId.equals("1"))
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_wifi);
                    else if (serviceId.equals("2"))
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_wc);
                    else if (serviceId.equals("3"))
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_parking);


                    Bitmap scalebitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

                    addMarkerOnGoogleMap(addressLatitude, addressLongitude, scalebitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SERVICES_TYPE:
                try {
                    serviceTypeList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);


                    JSONArray serviceTypeJsonArray = jsonObject.getJSONArray("service_types");

                    for (int j = 0; j < serviceTypeJsonArray.length(); j++) {
                        CategoryModel serviceTypeModel = new CategoryModel();

                        JSONObject serviceTypeJsonObject = serviceTypeJsonArray.getJSONObject(j);

                        serviceTypeModel.setId(JSONUtils.getStringFromJSON(serviceTypeJsonObject, "id"));
                        serviceTypeModel.setName(JSONUtils.getStringFromJSON(serviceTypeJsonObject, "name"));
                        serviceTypeModel.setName_fr(JSONUtils.getStringFromJSON(serviceTypeJsonObject, "name_fr"));
                        serviceTypeModel.setName_it(JSONUtils.getStringFromJSON(serviceTypeJsonObject, "name_it"));
                        serviceTypeModel.setCreated_at(JSONUtils.getStringFromJSON(serviceTypeJsonObject, "created_at"));
                        serviceTypeModel.setUpdated_at(JSONUtils.getStringFromJSON(serviceTypeJsonObject, "updated_at"));
                        serviceTypeList.add(serviceTypeModel);
                    }

                    for (int i = 0; i < serviceTypeList.size(); i++) {
                        if (serviceTypeId.contains(serviceTypeList.get(i).getId())) {
                            serviceName = serviceTypeList.get(i).getName();

                        }
                    }

                    tvDescription.setText(serviceName + "\n" + addressStreet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }


    public void addMarkerOnGoogleMap(String lat, String longitude, Bitmap scalebitmap) {

        LatLng latlong = new LatLng(Double.parseDouble(lat), Double.parseDouble(longitude));
        googleMapView.addMarker(new MarkerOptions().position(latlong).icon(BitmapDescriptorFactory.fromBitmap(scalebitmap)));
        googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
        cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(longitude))).zoom(15).build();
        googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}

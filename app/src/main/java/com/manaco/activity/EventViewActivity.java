package com.manaco.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
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
import com.manaco.model.StandsEventsModel;
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
 * Created by vinove on 14/11/16.
 */

public class EventViewActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, OnWebServiceResult {
    private Context mContext;
    private GoogleMap googleMapView;
    private LinearLayout llExhibitors, llEvent, llService, llShuttles, llFoodDrinks, llActualities, llAccount, llSetting;
    private Button btnGo;
    private TextView tvTime, tvTestDrive, tvDescription;
    private String eventId;
    private ArrayList<StandsEventsModel> eventViewlArrayList = new ArrayList<>();
    private Bitmap bitmap = null;
    private CameraPosition cameraPosition;
    private String imageUrl, langId;
    private View markerView;
    private ImageView ivOnMarker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventview_activity);
        mContext = EventViewActivity.this;

        initView();
        addListener();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewEventView);
        mapFragment.getMapAsync(this);

        llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
        llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));

        eventId = getIntent().getStringExtra("eventId");
        imageUrl = getIntent().getStringExtra("imageUrl");
        langId = SharedPreferencesManger.getPrefValue(mContext, Constants.LANGID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

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
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTestDrive = (TextView) findViewById(R.id.tvTestDrive);
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
        eventViewApi(eventId);

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
                for (int i = 0; i < eventViewlArrayList.size(); i++) {
                    lat = eventViewlArrayList.get(i).getAddressModelArrayList().get(i).getLat();
                    lon = eventViewlArrayList.get(i).getAddressModelArrayList().get(i).getLng();
                }
                intent = new Intent(mContext, NavigationExhibitorsActivity.class);
                intent.putExtra("Navigation", "EventView");
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("markerImage", imageUrl);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    private void eventViewApi(String eventId) {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).eventViewApi(eventId), "", "GET", Constants.SERVICE_TYPE.EVENTVIEW, this, "");
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
            case EVENTVIEW:
                try {

                    eventViewlArrayList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject object = jsonObject.getJSONObject("event");
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
                    JSONArray addressJsonArray = object.getJSONArray("address");

                    String dateTimeTittle = CommonUtils.dateFormate(model.getStart_dateTime()) + " " + "From" + " " + CommonUtils.timeFormate(model.getStart_dateTime()) + " " + "to" + " " + CommonUtils.timeFormate(model.getEnd_dateTime());
                    tvTime.setText(dateTimeTittle);

                    tvTestDrive.setText(model.getName());
                    if (langId.equals("1")) {
                        tvDescription.setText(model.getDescription());
                    } else if (langId.equals("2")) {
                        tvDescription.setText(model.getDescription_fr());
                    } else if (langId.equals("3")) {
                        tvDescription.setText(model.getDescription_it());
                    }


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

                        model.setAddressModelArrayList(addressModel);

                    }
                    eventViewlArrayList.add(model);
                    addMarkerOnGoogleMap();


                } catch (Exception e) {

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
            bitmap = CommonUtils.getBitmapFromURL(imageUrl);
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
            ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);

            bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
            ivOnMarker.setImageBitmap(CommonUtils.getRoundedCornerBitmap(bitmap, 40));

            for (int k = 0; k < eventViewlArrayList.get(0).getAddressModelArrayList().size(); k++) {
                ArrayList<AddressModel> addressModel = eventViewlArrayList.get(0).getAddressModelArrayList();
                LatLng latLng = new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()));
                googleMapView.addMarker(new MarkerOptions().position(latLng).title("").icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));

                googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()))).zoom(15).build();
                googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }
}

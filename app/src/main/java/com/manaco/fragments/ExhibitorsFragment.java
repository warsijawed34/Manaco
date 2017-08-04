package com.manaco.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manaco.R;
import com.manaco.activity.ExhibithorFavorites;
import com.manaco.activity.ExhibitorMapDetails;
import com.manaco.activity.FavoritesViewMap;
import com.manaco.activity.HomeActivity;
import com.manaco.activity.LoginActivity;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.AddressModel;
import com.manaco.model.CategoryModel;
import com.manaco.model.StandsModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.GPSTracker;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * Created by vinove on 8/11/16.
 */

public class ExhibitorsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    public GoogleMap googleMapView;
    private MapView mMapView;
    private ImageButton ibNavigation;
    private LinearLayout llFavoritesOne, llFavoritesTwo, llStandView, llList;
    private ArrayList<StandsModel> standsModelArrayList = new ArrayList<>();
    private ArrayList<StandsModel> favLogoArrayList = new ArrayList<>();
    private ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    private ArrayList<Bitmap> bitmapFavArrayList = new ArrayList<>();
    private CameraPosition cameraPosition;
    private SpotsDialog prog;
    private Bitmap bitmap, bitmapLogo;
    private int standModelArrayListCount, favLogoCount;
    private int position = 0,LogoPosition=0;
    private String standId, userId,tokenId, favLogo;
    private GPSTracker gps;
    private double currentLatitude;
    private double currentLongitude;
    private ImageView ivOnMarker, ivMarkerBg;
    private View markerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exhibitors_fragment, container, false);
        mContext = getActivity();
        mMapView = (MapView) view.findViewById(R.id.mapViewExhibitors);
        ibNavigation = (ImageButton) view.findViewById(R.id.ibNavigation);
        llFavoritesOne = (LinearLayout) view.findViewById(R.id.llFavoritesOneClick);
        llFavoritesTwo = (LinearLayout) view.findViewById(R.id.llFavoritesTwoClick);
        llStandView = (LinearLayout) view.findViewById(R.id.llStandView);
        llList = (LinearLayout) view.findViewById(R.id.llList);
        ibNavigation.setOnClickListener(this);
        llFavoritesOne.setOnClickListener(this);
        llStandView.setOnClickListener(this);
        llList.setOnClickListener(this);

        prog = new SpotsDialog(mContext, R.style.Custom);
        prog.setCancelable(false);
        tokenId = SharedPreferencesManger.getPrefValue(mContext, Constants.TOKEN_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        userId = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();

        standFavStandInterestedApi(tokenId, userId);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(mContext.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapView = googleMap;

        /*CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(currentLatitude, currentLongitude));
        circleOptions.radius(1000); // radius of circle in meters
        circleOptions.strokeColor(Color.RED);//apply stroke with blue
        circleOptions.fillColor(Color.RED);// fill circle with red
        Circle circle = googleMapView.addCircle(circleOptions);*/

        homeStandApi();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llFavoritesOneClick:
                llFavoritesOne.setVisibility(View.INVISIBLE);
                llFavoritesTwo.setVisibility(View.VISIBLE);

                //fade out color of icons
                googleMapView.clear();
                for (int i = 0; i < standsModelArrayList.size(); i++) {
                    //Images that are loaded first on map
                    bitmap = bitmapArrayList.get(i);
                    ivOnMarker.setImageBitmap(bitmap);
                    ivOnMarker.setAlpha(0.2f);
                    ivMarkerBg.setAlpha(0.2f);
                    standId = standsModelArrayList.get(i).getId();

                    for (int j = 0; j < favLogoArrayList.size(); j++) {
                        if(standsModelArrayList.get(i).getId().equalsIgnoreCase(favLogoArrayList.get(j).getId())) {
                            ivOnMarker.setAlpha(1f);
                            ivMarkerBg.setAlpha(1f);
                        }
                    }

                    for (int k = 0; k < standsModelArrayList.get(i).getAddressModelArrayList().size(); k++) {
                        ArrayList<AddressModel> addressModel = standsModelArrayList.get(i).getAddressModelArrayList();
                        LatLng latlong = new LatLng(Double.parseDouble(addressModel.get(0).getLat()), Double.parseDouble(addressModel.get(0).getLng()));
                        googleMapView.addMarker(new MarkerOptions().position(latlong).snippet(standId).icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));
                        googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                        googleMapView.getUiSettings().setMyLocationButtonEnabled(true);
                        cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(0).getLat()), Double.parseDouble(addressModel.get(0).getLng()))).zoom(16).build();
                        googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }


                break;
            case R.id.llStandView:
                intent=new Intent(mContext, HomeActivity.class);
                intent.putExtra("fragment", "exhibitorsFragment");
                startActivity(intent);
                getActivity().finish();

                break;
            case R.id.llList:
                if (userId.isEmpty()) {
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, ExhibithorFavorites.class);
                    startActivity(intent);
                }
                break;
            case R.id.ibNavigation:
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LatLng latlong = new LatLng(currentLatitude, currentLongitude);
                    googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                    googleMapView.getUiSettings().setMyLocationButtonEnabled(false);
                    googleMapView.setMyLocationEnabled(true);

                    cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLatitude, currentLongitude)).zoom(15).build();
                    googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                } else {

                }
                break;
            default:
                break;
        }
    }

    private void homeStandApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).homeStandsApi(), "", "GET", Constants.SERVICE_TYPE.HOME_STANDS, this, "");
                webService.execute();
            } else {
                CommonUtils.showToast(mContext, getString(R.string.internetConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type, int responseCode) {
        switch (type) {
            case HOME_STANDS:
                try {
                    standsModelArrayList.clear();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray standsJsonArray = jsonObject.getJSONArray("stands");

                    for (int i = 0; i < standsJsonArray.length(); i++) {
                        StandsModel standsModel = new StandsModel();
                        JSONObject standJsonObject = standsJsonArray.getJSONObject(i);

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
                        }

                        JSONArray categoryJsonArray = standJsonObject.getJSONArray("category");

                        for (int k = 0; k < categoryJsonArray.length(); k++) {

                            CategoryModel categoryModel = new CategoryModel();
                            JSONObject categoryJsonObject = addressJsonArray.getJSONObject(k);

                            categoryModel.setId(JSONUtils.getStringFromJSON(categoryJsonObject, "id"));
                            categoryModel.setName(JSONUtils.getStringFromJSON(categoryJsonObject, "name"));
                            categoryModel.setName_fr(JSONUtils.getStringFromJSON(categoryJsonObject, "name_fr"));
                            categoryModel.setName_it(JSONUtils.getStringFromJSON(categoryJsonObject, "name_it"));
                            categoryModel.setCreated_at(JSONUtils.getStringFromJSON(categoryJsonObject, "created_at"));
                            categoryModel.setUpdated_at(JSONUtils.getStringFromJSON(categoryJsonObject, "updated_at"));
                            standsModel.setCategoryModelArrayList(categoryModel);
                        }
                        standsModelArrayList.add(standsModel);
                    }

                    googleMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker arg0) {

                            Intent intent = new Intent(getActivity(), ExhibitorMapDetails.class);
                            intent.putExtra("exhibitorId", arg0.getSnippet());
                            startActivity(intent);
                            return true;
                        }
                    });

                    addMarkerOnGoogleMap();
                   // standFavStandInterestedApi(tokenId, userId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case STANDS_FAV_INTERESTED:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray standJsonArray = jsonObject.getJSONArray("stands");
                    for (int i = 0; i < standJsonArray.length(); i++) {
                        JSONObject standJsonObject = standJsonArray.getJSONObject(i);
                        StandsModel favModel = new StandsModel();
                        favModel.setId(JSONUtils.getStringFromJSON(standJsonObject, "id"));
                        favModel.setName(JSONUtils.getStringFromJSON(standJsonObject, "name"));
                        favModel.setLogo_path(JSONUtils.getStringFromJSON(standJsonObject, "logo_path"));
                        favLogoArrayList.add(favModel);
                    }
                    getFavoritesLogoPath (favLogoArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void addMarkerOnGoogleMap() {
        standModelArrayListCount = standsModelArrayList.size();
        new AsyncTaskRunner().execute();
    }


    private class AsyncTaskRunner extends AsyncTask<String, Bitmap, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!prog.isShowing())
                prog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = CommonUtils.getBitmapFromURL(standsModelArrayList.get(position).getLogo_path());
            return Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            googleMapView.clear();
            markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
            ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);
            ivMarkerBg = (ImageView) markerView.findViewById(R.id.ivMarkerBg);



            if (standModelArrayListCount >= 1) {
                bitmapArrayList.add(CommonUtils.getRoundedCornerBitmap(bit, 40));
                standModelArrayListCount--;
                position++;

                if (standModelArrayListCount != 0) {
                    new AsyncTaskRunner().execute();
                } else {
                    prog.dismiss();

                    for (int i = 0; i < standsModelArrayList.size(); i++) {
                        //Images that are loaded first on map
                        bitmap = bitmapArrayList.get(i);
                        ivOnMarker.setImageBitmap(bitmap);
                        standId = standsModelArrayList.get(i).getId();


                        for (int k = 0; k < standsModelArrayList.get(i).getAddressModelArrayList().size(); k++) {
                            ArrayList<AddressModel> addressModel = standsModelArrayList.get(i).getAddressModelArrayList();
                            LatLng latlong = new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()));
                            googleMapView.addMarker(new MarkerOptions().position(latlong).snippet(standId).icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));
                            googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                            googleMapView.getUiSettings().setMyLocationButtonEnabled(true);
                            cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()))).zoom(16).build();
                            googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }

                    }
                }

            }
            gpsTracker();
        }
    }

    private void getFavoritesLogoPath (ArrayList<StandsModel> favLogoArrayList) {
        favLogoCount = favLogoArrayList.size();
        new AsyncTaskRunner1FavLogo().execute();

    }

    private class AsyncTaskRunner1FavLogo extends AsyncTask<String, Bitmap, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = CommonUtils.getBitmapFromURL(favLogoArrayList.get(LogoPosition).getLogo_path());
            return Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
            ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);


            if(favLogoCount>=1){
                bitmapFavArrayList.add(CommonUtils.getRoundedCornerBitmap(bit, 40));
                favLogoCount--;
                LogoPosition++;
                if(favLogoCount!=0){
                    new AsyncTaskRunner1FavLogo().execute();
                }else {
                    for (int i = 0; i < favLogoArrayList.size(); i++) {
                        //favourite image, others are to be fade out
                        bitmapLogo=bitmapFavArrayList.get(i);

                    }
                }
            }
        }
    }


    public void gpsTracker() {
        gps = new GPSTracker(mContext);

        if (gps.canGetLocation()) {

            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();

        } else {
            gps.showSettingsAlert();

        }


    }

}
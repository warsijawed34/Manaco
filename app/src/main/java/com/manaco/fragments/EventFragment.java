package com.manaco.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manaco.R;
import com.manaco.activity.EventFavorites;
import com.manaco.activity.EventViewActivity;
import com.manaco.activity.ExhibitorMapDetails;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.AddressModel;
import com.manaco.model.StandsEventsModel;
import com.manaco.model.StandsModel;
import com.manaco.network.ConnectionDetector;
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

public class EventFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    public GoogleMap googleMapView;
    private MapView mMapView;
    ;
    private TextView tvTab1, tvTab2, tvTab3;
    private ImageButton ibNavigation;
//    private LinearLayout llfavorites;
    private ArrayList<StandsEventsModel> eventsModelArrayList = new ArrayList<>();
    private int eventModelArrayListCount;
    private int position = 0, imageLogoPos = 0, selectedPosition = 0;
    private Bitmap bitmap;
    private ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    private String eventId, logoId;
    private SpotsDialog prog;
    private CameraPosition cameraPosition;
    private String[] numberDate;
    private GPSTracker gps;
    private double latitudeCurrent;
    private double longitudeCurrent;
    private ImageView ivOnMarker;
    private View markerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_fragment, container, false);
        mContext = getActivity();
        tvTab1 = (TextView) view.findViewById(R.id.tvTab1);
        tvTab2 = (TextView) view.findViewById(R.id.tvTab2);
        tvTab3 = (TextView) view.findViewById(R.id.tvTab3);
        ibNavigation = (ImageButton) view.findViewById(R.id.ibNavigation);
//        llfavorites = (LinearLayout) view.findViewById(R.id.llFavorites);

        prog = new SpotsDialog(mContext, R.style.Custom);
        prog.setCancelable(false);

        tvTab1.setOnClickListener(this);
        tvTab2.setOnClickListener(this);
        tvTab3.setOnClickListener(this);
        ibNavigation.setOnClickListener(this);
//        llfavorites.setOnClickListener(this);

        mMapView = (MapView) view.findViewById(R.id.mapViewEvent);
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
        eventApi();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvTab1:
                tvTab1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selectedTab));
                tvTab2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                tvTab3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                selectedPosition = 0;
                addMarkerOnGoogleMap();
                break;
            case R.id.tvTab2:
                tvTab2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selectedTab));
                tvTab1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                tvTab3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                selectedPosition = 1;
                addMarkerOnGoogleMap();
                break;
            case R.id.tvTab3:
                tvTab3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.selectedTab));
                tvTab2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                tvTab1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                selectedPosition = 2;
                addMarkerOnGoogleMap();
                break;
           /* case R.id.llFavorites:
                intent = new Intent(mContext, EventFavorites.class);
                startActivity(intent);
                break;*/
            case R.id.ibNavigation:
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LatLng latlong = new LatLng(latitudeCurrent, longitudeCurrent);
                    googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                    googleMapView.getUiSettings().setMyLocationButtonEnabled(false);
                    googleMapView.setMyLocationEnabled(true);
                    cameraPosition = new CameraPosition.Builder().target(new LatLng(latitudeCurrent, longitudeCurrent)).zoom(15).build();
                    googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {

                }
                break;
            default:
                break;
        }

    }

    private void eventApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).eventUrlApi(), "", "GET", Constants.SERVICE_TYPE.EVENTS, this, "");
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
            case EVENTS:
                try {
                    eventsModelArrayList.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray eventsJsonArray = jsonObject.getJSONArray("events");

                    for (int i = 0; i < eventsJsonArray.length(); i++) {
                        StandsEventsModel model = new StandsEventsModel();
                        JSONObject object = eventsJsonArray.getJSONObject(i);
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
                        JSONArray standsJsonArray = object.getJSONArray("stand");
                        for (int k = 0; k < standsJsonArray.length(); k++) {
                            StandsModel standsModel = new StandsModel();
                            JSONObject standJsonObject = standsJsonArray.getJSONObject(k);
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
                            model.setStandsModelArrayList(standsModel);
                        }
                        eventsModelArrayList.add(model);
                        String dateTime;
                        String suffixDate;
                        for (int k = 0; k < eventsModelArrayList.size(); k++) {
                            dateTime = eventsModelArrayList.get(i).getStart_dateTime();
                            if (eventsModelArrayList.size() == 1) {
                                numberDate = CommonUtils.dateFormateMMM(dateTime).split(" ");
                                suffixDate = CommonUtils.getOrdinalSuffix(Integer.parseInt(numberDate[1]));
                                tvTab1.setText(CommonUtils.dateFormateMMM(dateTime) + "" + suffixDate);
                            } else if (eventsModelArrayList.size() == 2) {
                                numberDate = CommonUtils.dateFormateMMM(dateTime).split(" ");
                                suffixDate = CommonUtils.getOrdinalSuffix(Integer.parseInt(numberDate[1]));
                                tvTab2.setText(CommonUtils.dateFormateMMM(dateTime) + "" + suffixDate);
                            } else if (eventsModelArrayList.size() == 3) {
                                numberDate = CommonUtils.dateFormateMMM(dateTime).split(" ");
                                suffixDate = CommonUtils.getOrdinalSuffix(Integer.parseInt(numberDate[1]));
                                tvTab3.setText(CommonUtils.dateFormateMMM(dateTime) + "" + suffixDate);
                            }
                        }
                    }

                    googleMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker arg0) {
                            String imageUrl = "";
                            for (int i = 0; i < eventsModelArrayList.size(); i++) {
                                if (eventsModelArrayList.get(i).getId().equals(arg0.getSnippet())) {
                                    imageUrl = eventsModelArrayList.get(i).getStandsModelArrayList().get(0).getLogo_path();
                                }
                            }
                            Intent intent = new Intent(mContext, EventViewActivity.class);
                            intent.putExtra("eventId", arg0.getSnippet());
                            intent.putExtra("imageUrl", imageUrl);
                            startActivity(intent);
                            return true;
                        }
                    });
                    addMarkerOnGoogleMap();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public void addMarkerOnGoogleMap() {
//        eventModelArrayListCount = eventsModelArrayList.size();
        bitmapArrayList.clear();
        ArrayList<StandsModel> standsModel = eventsModelArrayList.get(selectedPosition).getStandsModelArrayList();
        for (int i = 0; i < standsModel.size(); i++) {
            String path = standsModel.get(i).getLogo_path();
            new AsyncTaskRunner1().execute(path);
        }
    }

    private class AsyncTaskRunner1 extends AsyncTask<String, Bitmap, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!prog.isShowing())
                prog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String path = params[0];
            Bitmap bitmap = CommonUtils.getBitmapFromURL(path);
//            ArrayList<StandsModel> standsModel = eventsModelArrayList.get(selectedPosition).getStandsModelArrayList();
//            Bitmap bitmap = CommonUtils.getBitmapFromURL(standsModel.get(imageLogoPos).getLogo_path());
            return Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        }


        @Override
        protected void onPostExecute(Bitmap bit) {
            markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
            ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);

            bitmapArrayList.add(CommonUtils.getRoundedCornerBitmap(bit, 40));
            prog.dismiss();
            googleMapView.clear();
            eventId = eventsModelArrayList.get(selectedPosition).getId();
            for (int k = 0; k < eventsModelArrayList.get(selectedPosition).getAddressModelArrayList().size(); k++) {
                bitmap = bitmapArrayList.get(k);
                ivOnMarker.setImageBitmap(bitmap);
                ArrayList<AddressModel> addressModel = eventsModelArrayList.get(selectedPosition).getAddressModelArrayList();
                LatLng latlong = new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()));
                googleMapView.addMarker(new MarkerOptions().position(latlong).snippet(eventId).icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));
                googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()))).zoom(16).build();
                googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            gpsTracker();


        }
    }

    public void gpsTracker() {
        gps = new GPSTracker(mContext);
        if (gps.canGetLocation()) {
            latitudeCurrent = gps.getLatitude();
            longitudeCurrent = gps.getLongitude();
        } else {
            gps.showSettingsAlert();

        }
    }

   /* private class AsyncTaskRunner extends AsyncTask<String, Bitmap, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!prog.isShowing())
                prog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            ArrayList<StandsModel> standsModel = eventsModelArrayList.get(position).getStandsModelArrayList();
            Bitmap bitmap = CommonUtils.getBitmapFromURL(standsModel.get(imageLogoPos).getLogo_path());
            return Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        }


        @Override
        protected void onPostExecute(Bitmap bit) {
            markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
            ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);

            if (eventModelArrayListCount >= 1) {
                bitmapArrayList.add(CommonUtils.getRoundedCornerBitmap(bit, 40));
                eventModelArrayListCount--;
                position++;

                if (eventModelArrayListCount != 0) {
                    new AsyncTaskRunner().execute();
                } else {
                    prog.dismiss();

                    for (int i = 0; i < eventsModelArrayList.size(); i++) {
                        bitmap = bitmapArrayList.get(i);
                        ivOnMarker.setImageBitmap(bitmap);
                        eventId = eventsModelArrayList.get(i).getId();
                        for (int k = 0; k < eventsModelArrayList.get(i).getAddressModelArrayList().size(); k++) {
                            ArrayList<AddressModel> addressModel = eventsModelArrayList.get(i).getAddressModelArrayList();
                            LatLng latlong = new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()));
                            googleMapView.addMarker(new MarkerOptions().position(latlong).snippet(eventId).icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));
                          //  googleMapView.addMarker(new MarkerOptions().position(latlong).snippet(id).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                            googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                            cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()))).zoom(16).build();
                            googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    }
                }

            }
            gpsTracker();


        }
    }*/
}

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
import com.manaco.activity.ServiceViewActivity;
import com.manaco.interfaces.OnWebServiceResult;
import com.manaco.model.AddressModel;
import com.manaco.model.CategoryModel;
import com.manaco.model.ServiceModel;
import com.manaco.network.ConnectionDetector;
import com.manaco.utils.CallWebService;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.GPSTracker;
import com.manaco.utils.JSONUtils;
import com.manaco.utils.WebServiceApis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * Created by vinove on 8/11/16.
 */

public class ServicesFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, OnWebServiceResult {
    private Context mContext;
    public GoogleMap googleMapView;
    private MapView mMapView;
    private ImageButton ibNavigation;
    private ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();
    private ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    private CameraPosition cameraPosition;
    private SpotsDialog prog;
    int servicePosition;
    private String serviceName,id;
    private GPSTracker gps;
    private double currentLatitude;
    private double currentLongitude;
    private Bitmap bitmap;
    private int sevicesModelArrayListCount;
    private int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.services_fragment, container, false);
        mContext = getActivity();
        ibNavigation = (ImageButton) view.findViewById(R.id.ibNavigation);
        ibNavigation.setOnClickListener(this);

        mMapView = (MapView) view.findViewById(R.id.mapViewServices);


        prog = new SpotsDialog(mContext, R.style.Custom);
        prog.setCancelable(false);

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
        serviceApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void serviceApi() {
        try {
            if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                CallWebService webService = new CallWebService(mContext, new WebServiceApis(mContext).servicesApi(), "", "GET", Constants.SERVICE_TYPE.SERVICES, this, "");
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
            case SERVICES:
                try {
                    serviceModelArrayList.clear();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray serviceJsonArray = jsonObject.getJSONArray("services");

                    for (int i = 0; i < serviceJsonArray.length(); i++) {
                        ServiceModel serviceModel = new ServiceModel();
                        JSONObject serviceJsonObject = serviceJsonArray.getJSONObject(i);

                        serviceModel.setId(JSONUtils.getStringFromJSON(serviceJsonObject, "id"));
                        serviceModel.setName(JSONUtils.getStringFromJSON(serviceJsonObject, "name"));
                        serviceModel.setName_it(JSONUtils.getStringFromJSON(serviceJsonObject, "name_it"));
                        serviceModel.setName_fr(JSONUtils.getStringFromJSON(serviceJsonObject, "name_fr"));
                        serviceModel.setServiceType_id(JSONUtils.getStringFromJSON(serviceJsonObject, "service_type_id"));
                        serviceModel.setCreated_at(JSONUtils.getStringFromJSON(serviceJsonObject, "created_at"));
                        serviceModel.setUpdated_at(JSONUtils.getStringFromJSON(serviceJsonObject, "updated_at"));
                        serviceModel.setStatus_id(JSONUtils.getStringFromJSON(serviceJsonObject, "status_id"));
                        serviceModel.setDeleted_at(JSONUtils.getStringFromJSON(serviceJsonObject, "deleted_at"));
                        serviceModel.setAddress_id(JSONUtils.getStringFromJSON(serviceJsonObject, "address_id"));

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

                            serviceModel.setAddressModelArrayList(addressModel);
                        }


                        CategoryModel categoryModel = new CategoryModel();
                        JSONObject categoryJsonObject = serviceJsonObject.getJSONObject("service_type");


                        categoryModel.setId(JSONUtils.getStringFromJSON(categoryJsonObject, "id"));
                        categoryModel.setName(JSONUtils.getStringFromJSON(categoryJsonObject, "name"));
                        categoryModel.setName_fr(JSONUtils.getStringFromJSON(categoryJsonObject, "name_fr"));
                        categoryModel.setName_it(JSONUtils.getStringFromJSON(categoryJsonObject, "name_it"));
                        categoryModel.setCreated_at(JSONUtils.getStringFromJSON(categoryJsonObject, "created_at"));
                        categoryModel.setUpdated_at(JSONUtils.getStringFromJSON(categoryJsonObject, "updated_at"));
                        serviceModel.setServiceTypeModelArrayList(categoryModel);

                        serviceModelArrayList.add(serviceModel);
                    }

                    googleMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker arg0) {

                            Intent intent = new Intent(getActivity(), ServiceViewActivity.class);
                            intent.putExtra("serviceId", arg0.getSnippet());
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
        sevicesModelArrayListCount = serviceModelArrayList.size();
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

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_parking);
            if (position == 0) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_wifi);
            } else if (position == 1) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_wc);
            } else if (position == 2) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_parking);
            }


            return Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            if (sevicesModelArrayListCount >= 1) {
                bitmapArrayList.add(bit);
                sevicesModelArrayListCount--;
                position++;

                if (sevicesModelArrayListCount != 0) {
                    new AsyncTaskRunner().execute();
                } else {
                    prog.dismiss();

                    for (int i = 0; i < serviceModelArrayList.size(); i++) {
                        bitmap = bitmapArrayList.get(i);
                        id = serviceModelArrayList.get(i).getId();
                        for (int k = 0; k < serviceModelArrayList.get(i).getAddressModelArrayList().size(); k++) {
                            ArrayList<AddressModel> addressModel = serviceModelArrayList.get(i).getAddressModelArrayList();
                            LatLng latlong = new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()));
                            googleMapView.addMarker(new MarkerOptions().position(latlong).snippet(id).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                            googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                            cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(addressModel.get(k).getLat()), Double.parseDouble(addressModel.get(k).getLng()))).zoom(14).build();
                            googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }

                    }
                }

            }
            gpsTracker();
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

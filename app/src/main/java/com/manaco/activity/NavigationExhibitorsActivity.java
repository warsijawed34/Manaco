package com.manaco.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manaco.R;
import com.manaco.pereferences.SharedPreferencesManger;
import com.manaco.utils.CommonUtils;
import com.manaco.utils.Constants;
import com.manaco.utils.DistanceUtil;
import com.manaco.utils.GMapV2GetRouteDirection;
import com.manaco.utils.GPSTracker;

import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * Created by vinove on 14/11/16.
 */

public class NavigationExhibitorsActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {
    private Context mContext;
    private GoogleMap googleMapView;
    private LinearLayout llExhibitors, llEvent, llService, llShuttles, llFoodDrinks, llActualities, llAccount, llSetting, llDetails;
    private ImageView ivNavigationDown, ivNavigationUp, ivOnMarker;
    private TextView tvTimeMeter, tvAddress;
    private ImageButton btnClose;
    private String navigationClik, time, distance, endAddress;
    private String latitude, longitude, markerImage, exhibitorId, serviceId ;
    private CameraPosition cameraPosition;
    private Bitmap bitmap;
    private GPSTracker gps;
    private String user_id;
    // private double latitudeCurrent;
    // private double longitudeCurrent;

    ImageButton buttonClose;
    TextView tvTimerMeter, tvAddressText;
    LinearLayout llLayout;
    BottomSheetDialog dialog;


    private GMapV2GetRouteDirection v2GetRouteDirection;
    private Document document;
    private LatLng provider_position, user_position;
    private SupportMapFragment mapFragment;
    double lat = 43.7260023;
    double lon = 7.3040478;
    private SpotsDialog prog;
    private View markerView;
    View bottomSheetView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        mContext = NavigationExhibitorsActivity.this;

        user_id = SharedPreferencesManger.getPrefValue(mContext, Constants.USER_ID, SharedPreferencesManger.PREF_DATA_TYPE.STRING).toString();
        initView();
        addListener();

/*
        dialog = new BottomSheetDialog(NavigationExhibitorsActivity.this);
        dialog.setContentView(R.layout.navigation_dialog);
       // bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetView = getLayoutInflater().inflate(R.layout.navigation_dialog, null);
        tvTimeMeter= (TextView) bottomSheetView.findViewById(R.id.tvTimeMeter);
        tvAddress= (TextView) bottomSheetView.findViewById(R.id.tvAddress);*/


        buttonClose = (ImageButton) findViewById(R.id.btnClose);
        tvTimerMeter = (TextView) findViewById(R.id.tvTimeMeter);
        tvAddressText = (TextView) findViewById(R.id.tvAddress);
        llLayout = (LinearLayout) findViewById(R.id.llLayout);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewNavigation);
        mapFragment.getMapAsync(this);

        prog = new SpotsDialog(mContext, R.style.Custom);
        prog.setCancelable(false);

        navigationClik = getIntent().getStringExtra("Navigation");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        markerImage = getIntent().getStringExtra("markerImage");
        exhibitorId = getIntent().getStringExtra("exhibitorId");
        serviceId = getIntent().getStringExtra("serviceID");


        if (navigationClik.equalsIgnoreCase("EventView")) {
            llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
            llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));

        } else if (navigationClik.equalsIgnoreCase("ExhibitorsView")) {
            llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
            llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));

        } else if (navigationClik.equalsIgnoreCase("serviceView")) {
            llExhibitors.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llEvent.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llService.setBackgroundColor(ContextCompat.getColor(mContext, R.color.langBG));
            llShuttles.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
            llFoodDrinks.setBackgroundColor(ContextCompat.getColor(mContext, R.color.loginRegBG));
        }


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
        ivNavigationDown = (ImageView) findViewById(R.id.ivNavigationDown);
        ivNavigationUp = (ImageView) findViewById(R.id.ivNavigationUp);
        btnClose = (ImageButton) findViewById(R.id.btnClose);
        // tvTimeMeter = (TextView) findViewById(R.id.tvTimeMeter);
        // tvAddress = (TextView) findViewById(R.id.tvAddress);
        llDetails = (LinearLayout) findViewById(R.id.llDetails);

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
        ivNavigationDown.setOnClickListener(this);
        ivNavigationUp.setOnClickListener(this);
        btnClose.setOnClickListener(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapView = googleMap;
        addMarkerOnGoogleMap();
        googleMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (exhibitorId != null) {
                    Intent intent = new Intent(mContext, ExhibitorMapDetails.class);
                    intent.putExtra("exhibitorId", exhibitorId);
                    startActivity(intent);
                }

                return true;
            }
        });

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
            case R.id.ivNavigationDown:
//                googleMap(Double.parseDouble(latitude), Double.parseDouble(longitude));

                break;
            case R.id.ivNavigationUp:

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LatLng latlong = new LatLng(lat, lon);
                    googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latlong));
                    googleMapView.getUiSettings().setMyLocationButtonEnabled(false);
                    googleMapView.setMyLocationEnabled(true);
                    cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(15).build();
                    googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                } else {

                }
                break;
            case R.id.btnClose:
                finish();
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
        protected void onPreExecute() {
            super.onPreExecute();
            if (!prog.isShowing())
                prog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            if(serviceId!=null){
                if (serviceId.equals("1"))
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_wifi);
                else if (serviceId.equals("2"))
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_wc);
                else if (serviceId.equals("3"))
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_parking);
            }else {
                bitmap = CommonUtils.getBitmapFromURL(markerImage);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            prog.dismiss();

            if(serviceId!=null){
                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMapView.addMarker(new MarkerOptions().position(latLng).title("").icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                googleMapView.getUiSettings().setMyLocationButtonEnabled(true);
//                cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10).build();
//                googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap(Double.parseDouble(latitude), Double.parseDouble(longitude));
            }else {
                markerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_map, null);
                ivOnMarker = (ImageView) markerView.findViewById(R.id.ivMarker);
                bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
                ivOnMarker.setImageBitmap(CommonUtils.getRoundedCornerBitmap(bitmap, 40));
                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                googleMapView.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMapView.addMarker(new MarkerOptions().position(latLng).title("").icon(BitmapDescriptorFactory.fromBitmap(CommonUtils.createDrawableFromView(mContext, markerView))));
                googleMapView.getUiSettings().setMyLocationButtonEnabled(true);
//                cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10).build();
//                googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap(Double.parseDouble(latitude), Double.parseDouble(longitude));
            }



            //   gpsTracker();
        }
    }

    public void gpsTracker() {
        gps = new GPSTracker(mContext);

        if (gps.canGetLocation()) {

            //   latitudeCurrent = gps.getLatitude();
            //  longitudeCurrent = gps.getLongitude();

        } else {
            gps.showSettingsAlert();

        }
    }

    public void googleMap(double provider_latitude, double provider_longitude) {
        v2GetRouteDirection = new GMapV2GetRouteDirection();
        provider_position = new LatLng(provider_latitude, provider_longitude);
        user_position = new LatLng(lat, lon);

        googleMapView.addCircle(new CircleOptions()
                .center(new LatLng(lat, lon))
                .radius(150)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

        GetRouteTask getRoute = new GetRouteTask();
        getRoute.execute();
    }


    private class GetRouteTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            if (!prog.isShowing())
                prog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            //Get All Route values
            document = v2GetRouteDirection.getDocument(provider_position, user_position, GMapV2GetRouteDirection.MODE_WALKING);
            response = "Success";
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (response.equalsIgnoreCase("Success")) {

                /*final ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);

                PolylineOptions rectLine = new PolylineOptions()
                    .width(8)
                    .color(ContextCompat.getColor(mContext, R.color.colorRed));
                for (int i = 0; i < directionPoint.size(); i++) {

                    rectLine.add(directionPoint.get(i));
                }*/

                //googleMapView.addPolyline(rectLine);

                /*ArrayList<LatLng> listOfPoints=new ArrayList<>();

                listOfPoints.add(user_position);
                listOfPoints.add(provider_position);
                drawDashedPolyLine(googleMapView,listOfPoints, Color.RED);*/


                final ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
                PolylineOptions rectLine = new PolylineOptions()
                        .width(5f)
                        .color(ContextCompat.getColor(mContext, R.color.colorRed));
                for (int i = 1; i < directionPoint.size(); i = i + 20) {
                    if (directionPoint.size() > i + 10) {
                        createDashedLine(googleMapView, directionPoint.get(i), directionPoint.get(i + 10));
                        //rectLine.add(directionPoint.get(i));
                    }
                }
//                createDashedLine(googleMapView, user_position, provider_position);

                double s1 = lat + Double.parseDouble(latitude);
                double s2 = lon + Double.parseDouble(longitude);
                double s3 = s1 / 2;
                double s4 = s2 / 2;

                CameraPosition cameraPosition = new CameraPosition.Builder().target(
                        new LatLng(s3, s4)).zoom(12).build();
                googleMapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                choosePopup();

                /*ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                params.height = 880;
                mapFragment.getView().setLayoutParams(params);*/


                /*dialog = new BottomSheetDialog(NavigationExhibitorsActivity.this);
                // dialog.setContentView(R.layout.navigation_dialog);
                bottomSheetView = getLayoutInflater().inflate(R.layout.navigation_dialog, null);
                tvTimeMeter = (TextView) bottomSheetView.findViewById(R.id.tvTimeMeter);
                tvAddress = (TextView) bottomSheetView.findViewById(R.id.tvAddress);

                time = v2GetRouteDirection.getDurationText(document);
                distance = v2GetRouteDirection.getDistanceText(document);
                endAddress = v2GetRouteDirection.getEndAddress(document);
                tvTimerMeter.setText(time + " " + "(" + distance + ")");
                tvAddressText.setText(endAddress);
                dialog.show();

                buttonClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });*/


                /*btnClose.setVisibility(View.VISIBLE);
                llDetails.setVisibility(View.VISIBLE);

                time = v2GetRouteDirection.getDurationText(document);
                distance = v2GetRouteDirection.getDistanceText(document);
                endAddress = v2GetRouteDirection.getEndAddress(document);
                tvTimeMeter.setText(time + " " + "(" + distance + ")");
                tvAddress.setText(endAddress);*/

            }
            prog.dismiss();
        }
    }

    //for dotted lines, this is not working currently
    /*private void drawDashedPolyLine(GoogleMap mMap, ArrayList<LatLng> listOfPoints, int color) {
    *//* Boolean to control drawing alternate lines *//*
        boolean added = false;
        for (int i = 0; i < listOfPoints.size() - 1 ; i++) {
        *//* Get distance between current and next point *//*
            double distance = getConvertedDistance(listOfPoints.get(i),listOfPoints.get(i + 1));

        *//* If distance is less than 0.002 kms *//*
            if (distance < 0.002) {
                if (!added) {
                    mMap.addPolyline(new PolylineOptions()
                            .add(listOfPoints.get(i))
                            .add(listOfPoints.get(i + 1))
                            .color(color));
                    added = true;
                } else {*//* Skip this piece *//*
                    added = false;
                }
            } else {
            *//* Get how many divisions to make of this line *//*
                int countOfDivisions = (int) ((distance/0.002));

            *//* Get difference to add per lat/lng *//*
                double latdiff = (listOfPoints.get(i+1).latitude - listOfPoints
                        .get(i).latitude) / countOfDivisions;
                double lngdiff = (listOfPoints.get(i + 1).longitude - listOfPoints
                        .get(i).longitude) / countOfDivisions;

            *//* Last known indicates start point of polyline. Initialized to ith point *//*
                LatLng lastKnowLatLng = new LatLng(listOfPoints.get(i).latitude, listOfPoints.get(i).longitude);
                for (int j = 0; j < countOfDivisions; j++) {

                *//* Next point is point + diff *//*
                    LatLng nextLatLng = new LatLng(lastKnowLatLng.latitude + latdiff, lastKnowLatLng.longitude + lngdiff);
                    if (!added) {
                        mMap.addPolyline(new PolylineOptions()
                                .add(lastKnowLatLng)
                                .add(nextLatLng)
                                .color(color));
                        added = true;
                    } else {
                        added = false;
                    }
                    lastKnowLatLng = nextLatLng;
                }
            }
        }
    }

    private double getConvertedDistance(LatLng latlng1, LatLng latlng2) {
        double distance = DistanceUtil.distance(latlng1.latitude,
                latlng1.longitude,
                latlng2.latitude,
                latlng2.longitude);
        BigDecimal bd = new BigDecimal(distance);
        BigDecimal res = bd.setScale(3, RoundingMode.DOWN);
        return res.doubleValue();
    }*/



    public void createDashedLine(GoogleMap map, LatLng latLngOrig, LatLng latLngDest) {
        double difLat = latLngDest.latitude - latLngOrig.latitude;
        double difLng = latLngDest.longitude - latLngOrig.longitude;

        double zoom = map.getCameraPosition().zoom;

        double divLat = difLat / (zoom * 2);
        double divLng = difLng / (zoom * 2);

        LatLng tmpLatOri = latLngOrig;

        for (int i = 0; i < (zoom * 2); i++) {
            LatLng loopLatLng = tmpLatOri;

            if (i > 0) {
                loopLatLng = new LatLng(tmpLatOri.latitude + (divLat * 0.25f), tmpLatOri.longitude + (divLng * 0.25f));
            }

            Polyline polyline = map.addPolyline(new PolylineOptions()
                    .add(loopLatLng)
                    .add(new LatLng(tmpLatOri.latitude + divLat, tmpLatOri.longitude + divLng))
                    .color(ContextCompat.getColor(mContext, R.color.colorRed))
                    .width(5f));

            tmpLatOri = new LatLng(tmpLatOri.latitude + divLat, tmpLatOri.longitude + divLng);
        }
    }

    public void choosePopup() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.navigation_dialog, null);
        final BottomSheetDialog btDialog = new BottomSheetDialog(NavigationExhibitorsActivity.this);
        ImageButton iBtn= (ImageButton) promptView.findViewById(R.id.btnClose);
        TextView tvTime= (TextView) promptView.findViewById(R.id.tvTimeMeter);
        TextView tvAdress= (TextView) promptView.findViewById(R.id.tvAddress);
        btDialog.setContentView(promptView);
        time = v2GetRouteDirection.getDurationText(document);
        distance = v2GetRouteDirection.getDistanceText(document);
        endAddress = v2GetRouteDirection.getEndAddress(document);
        tvTime.setText(time + " " + "(" + distance + ")");
        tvAdress.setText(endAddress);
        btDialog.show();

        iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btDialog.dismiss();
            }
        });
    }
}
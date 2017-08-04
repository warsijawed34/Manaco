package com.manaco.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manaco.R;

/**
 * Created by vinove on 8/11/16.
 */

public class ShuttlesFragment extends Fragment implements OnMapReadyCallback {
    private Context mContext;
    public GoogleMap googleMapView;
    private MapView mMapView;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shuttles_fragment, container, false);
        mContext = getActivity();

        mMapView = (MapView) view.findViewById(R.id.mapViewShuttles);
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
        LatLng sydney = new LatLng(-34, 151);
        googleMapView.addMarker(new MarkerOptions().position(sydney).title("Marker in Vinove"));
        googleMapView.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}

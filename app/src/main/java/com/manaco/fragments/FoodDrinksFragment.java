package com.manaco.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manaco.R;

/**
 * Created by vinove on 8/11/16.
 */

public class FoodDrinksFragment extends Fragment {

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fooddrinks_fragment, container, false);
        mContext = getActivity();
        return view;
    }
}

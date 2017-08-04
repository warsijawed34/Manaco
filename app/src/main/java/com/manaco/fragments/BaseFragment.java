package com.manaco.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.manaco.R;

/**
 * Created by vinove on 8/11/16.
 * import android.support.v4.app.Fragment;
 */

public class BaseFragment extends FragmentActivity {
    public static ImageView ivLeft,ivRight;
    public static TextView tvRight, tvCenter;
//    public static Toolbar toolbar_inFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivLeft= (ImageView) findViewById(R.id.ivIconLeft);
        ivRight= (ImageView) findViewById(R.id.ivRight);
        tvCenter= (TextView) findViewById(R.id.tvCenter);
        tvRight= (TextView) findViewById(R.id.tvRight);
//        toolbar_inFragment = (Toolbar) findViewById(R.id.llToolBar);

        //((ActionBarActivity)getActivity()).setSupportActionBar(toolbar_inFragment);
    }

    public void onFragmentReplaceWithBackStack(Fragment fragment, String TAG, Bundle args) {
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabContent_fragment, fragment).addToBackStack(TAG).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void onFragmentAddWithBackStack(Fragment fragment, String TAG, Bundle args) {
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.tabContent_fragment, fragment).addToBackStack(TAG).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void onFragmentAddWithoutBackStack(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.tabContent_fragment, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void onFragmentRemove(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void onFragmentReplace(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabContent_fragment, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

}

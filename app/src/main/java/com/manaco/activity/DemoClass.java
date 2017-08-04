package com.manaco.activity;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.manaco.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vinove on 10/11/16.
 */

public class DemoClass extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        mContext = DemoClass.this;


    }
}

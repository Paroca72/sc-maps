package com.sccomponents.maps.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.sccomponents.maps.ScLimitMap;

public class LimitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit);

        // Find the map fragment
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.mapLayout);

        // Cast the fragment and init the map
        ScLimitMap limitMap = (ScLimitMap) fragment;

        // Map constraints (ITALY)
        limitMap.setBounds(
                new LatLng(36.031332, 6.207275),
                new LatLng(47.234490, 19.973145)
        );
    }
}

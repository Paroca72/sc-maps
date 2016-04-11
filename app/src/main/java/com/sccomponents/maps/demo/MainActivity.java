package com.sccomponents.maps.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons events
        Button touchableMap = (Button) this.findViewById(R.id.btnScTouchableMap);
        touchableMap.setOnClickListener(this);

        Button limitMap = (Button) this.findViewById(R.id.btnScLimitMap);
        limitMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Generic intent
        Intent generic = null;

        // Selector
        switch (v.getId()) {
            // ScTouchableMap
            case R.id.btnScTouchableMap:
                generic = new Intent(this, TouchableActivity.class);
                break;

            // ScLimitMap
            case R.id.btnScLimitMap:
                generic = new Intent(this, LimitActivity.class);
                break;
        }

        // Start
        this.startActivity(generic);
    }

}

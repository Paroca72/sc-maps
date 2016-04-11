package com.sccomponents.maps.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.sccomponents.maps.ScTouchableMap;

public class TouchableActivity        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchable);

        // Find the map fragment
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.mapLayout);

        // Cast the fragment and init the map
        ScTouchableMap touchableMap = (ScTouchableMap) fragment;
        touchableMap.setMapListener(new ScTouchableMap.OnMapListener() {
            @Override
            public void onTouched() {
                TouchableActivity.this.showMessage("Map is touched!", true);
            }

            @Override
            public void onReleased() {
                TouchableActivity.this.showMessage("Map is released!", true);
            }

            @Override
            public void onUnsettled() {
                TouchableActivity.this.showMessage("Map is unsettled!", false);
            }

            @Override
            public void onSettled() {
                TouchableActivity.this.showMessage("Map is settled!", false);
            }
        });
    }

    // Show message
    private void showMessage(String message, boolean atTop) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        if (atTop) toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

}

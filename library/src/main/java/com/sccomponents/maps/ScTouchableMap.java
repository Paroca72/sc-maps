package com.sccomponents.maps;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;

/**
 * ScTouchableMap improve the ability to control the user interaction with the map.
 * With this class you will be able to understand when the map is touched, released, unsettled and settled.
 */
public class ScTouchableMap
        extends SupportMapFragment
        implements OnMapReadyCallback, GoogleMap.OnCameraChangeListener {

    /**
     * Private variables
     */

    // Injection
    private View mOriginalView = null;

    // Map and position
    protected GoogleMap mGoogleMap = null;
    private CameraPosition mLastPosition = null;
    private int mMapSettleCheckTime = 500;

    // Status
    private boolean mTouched = false;
    private boolean mSettled = false;

    // Handler
    private Handler mHandler = null;
    private Runnable mRunnableChecker = null;

    // Listener
    private OnMapListener mMapListener = null;
    private GoogleMap.OnCameraChangeListener mCameraChangeListener = null;


    /**
     * Constructor
     */

    public ScTouchableMap() {
        // Create the handler and the checker runnable method
        this.mHandler = new Handler();
        this.mRunnableChecker = new Runnable() {
            @Override
            public void run() {
                // Check if the map is settled
                ScTouchableMap.this.checkMapOnSettled();
            }
        };
    }


    /**
     * Private methods
     */

    // Stop check
    private void stopCheckMoving() {
        // Remove the runnable reference from the handler
        this.mHandler.removeCallbacks(this.mRunnableChecker);
    }

    // Check if the map is settled
    private void startCheckMoving() {
        // Stop the precedence timer
        this.stopCheckMoving();

        // Check if the map is ready
        if (this.mGoogleMap != null) {
            // Save the last camera position and schedule the check
            this.mLastPosition = this.mGoogleMap.getCameraPosition();
            this.mHandler.postDelayed(this.mRunnableChecker, this.mMapSettleCheckTime);
        }
    }

    // Check map on settled
    private void checkMapOnSettled() {
        // Check if the current activity still exists and the map if ready.
        // Can happen when the app closing.
        if (this.getActivity() != null && this.mGoogleMap != null) {
            // Check if the camera position is finish to change
            CameraPosition currentPosition = this.mGoogleMap.getCameraPosition();
            if (currentPosition.equals(this.mLastPosition)) {
                // Call settled
                this.onSettled();
            }
        }
    }


    /**
     * Protected methods
     */

    // On touched
    protected void onTouched() {
        // Check if not already touched
        if (!this.mTouched) {
            // Save the status
            this.mTouched = true;

            // Check the single listener
            if (this.mMapListener != null) {
                this.mMapListener.onTouched();
            }
        }
    }

    // On released
    protected void onReleased() {
        // Check if not already released
        if (this.mTouched) {
            // Save the status
            this.mTouched = false;

            // Call the single listener
            if (this.mMapListener != null) {
                this.mMapListener.onReleased();
            }
        }
    }

    // On unsettled
    protected void onUnsettled() {
        // Check for changed
        if (this.mSettled) {
            // Save the status
            this.mSettled = false;

            // Check the single listener
            if (this.mMapListener != null) {
                this.mMapListener.onUnsettled();
            }
        }
    }

    // On settled
    protected void onSettled() {
        // Check for changed
        if (!this.mSettled) {
            // Save the status
            this.mSettled = true;

            // Check the single listener
            if (this.mMapListener != null) {
                this.mMapListener.onSettled();
            }
        }
    }


    /**
     * Public methods
     */

    // Set the single one listener
    @SuppressWarnings("unused")
    public void setMapListener(OnMapListener listener) {
        this.mMapListener = listener;
    }

    // Set the on camera change listener
    @SuppressWarnings("unused")
    public void setOnCameraChangeListener(GoogleMap.OnCameraChangeListener listener) {
        this.mCameraChangeListener = listener;
    }


    /**
     * Public properties
     */

    // The delay milliseconds before check if the map is settled
    // Default value is 500 milliseconds.
    @SuppressWarnings("unused")
    public int getSettleCheckDelay() {
        return this.mMapSettleCheckTime;
    }

    @SuppressWarnings("unused")
    public void setSettleCheckDelay(int value) {
        this.mMapSettleCheckTime = value;
    }


    /**
     * Methods override
     */

    // Inject the current view inside a FrameLayout.
    // After this the view will be enabled for touch control.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        // Super
        this.mOriginalView = super.onCreateView(inflater, parent, savedInstanceState);

        // Create the wrapper
        Wrapper wrapper = new Wrapper(this.getActivity());
        wrapper.addView(this.mOriginalView);

        // Request map
        this.getMapAsync(this);

        // Return the wrapper
        return wrapper;
    }

    // When the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Save the reference of map
        this.mGoogleMap = googleMap;
        // Set the on camera change listener
        this.mGoogleMap.setOnCameraChangeListener(this);
    }

    // When the camera map change
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        // Unsettled
        this.onUnsettled();
        // If not touched
        if (!mTouched) {
            // Start to check moving
            startCheckMoving();
        }

        // Listener propagation
        if (this.mCameraChangeListener != null) {
            this.mCameraChangeListener.onCameraChange(cameraPosition);
        }
    }

    // Must be overridden
    @Override
    public View getView() {
        return mOriginalView;
    }


    /**
     * Listener
     */

    @SuppressWarnings("unused")
    public interface OnMapListener {

        void onTouched();

        void onReleased();

        void onUnsettled();

        void onSettled();

    }


    /******************************************************************************************
     * Touchable wrapper
     */

    private class Wrapper extends FrameLayout {

        public Wrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    ScTouchableMap.this.onTouched();
                    break;

                case MotionEvent.ACTION_UP:
                    ScTouchableMap.this.onReleased();
                    break;
            }
            return super.dispatchTouchEvent(event);
        }
    }

}

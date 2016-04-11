package com.sccomponents.maps;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegion;

/**
 * ScLimitMap extend the ScTouchableMap.
 * Limit the boundaries of the map to a fixed area by left, top, right, bottom coordinate.
 * If the user move the map over this limits the class will reposition the map within.
 */
public class ScLimitMap
        extends ScTouchableMap
        implements OnMapReadyCallback {

    /**
     * Private variables
     */

    private LatLngBounds mMapBounds = null;
    private int mPadding = 20;


    /**
     * Private methods
     */

    // Check if map boundaries it's inside the visible region
    private boolean exceedsLimitBounds() {
        // Check if the map is ready
        if (this.mGoogleMap == null) return false;

        // Find the map region visible on video
        VisibleRegion region = this.mGoogleMap.getProjection().getVisibleRegion();
        LatLngBounds bounds = region.latLngBounds;

        // First dimensions
        double firstWidth = Math.abs(this.mMapBounds.northeast.longitude - this.mMapBounds.southwest.longitude);
        double firstHeight = Math.abs(this.mMapBounds.northeast.longitude - this.mMapBounds.southwest.longitude);

        // Second dimensions
        double secondWidth = Math.abs(bounds.northeast.longitude - bounds.southwest.longitude);
        double secondHeight = Math.abs(bounds.northeast.longitude - bounds.southwest.longitude);

        // Return
        return (secondWidth > firstWidth) || (secondHeight > firstHeight);
    }

    // Fix the map position
    private LatLng fixPositionByConstraints() {
        // Check if the map is ready
        if (this.mGoogleMap == null) return null;

        // Find the map region visible on video
        VisibleRegion region = this.mGoogleMap.getProjection().getVisibleRegion();
        LatLngBounds bounds = region.latLngBounds;

        // Holder
        double latitude = 0.0;
        double longitude = 0.0;

        // If exists limits
        if (this.mMapBounds != null) {
            // Check latitude
            if (bounds.southwest.latitude < this.mMapBounds.southwest.latitude) {
                latitude = this.mMapBounds.southwest.latitude - bounds.southwest.latitude;
            }
            if (bounds.northeast.latitude > this.mMapBounds.northeast.latitude) {
                latitude = this.mMapBounds.northeast.latitude - bounds.northeast.latitude;
            }

            // Check longitude
            if (bounds.southwest.longitude < this.mMapBounds.southwest.longitude) {
                longitude = this.mMapBounds.southwest.longitude - bounds.southwest.longitude;
            }
            if (bounds.northeast.longitude > this.mMapBounds.northeast.longitude) {
                longitude = this.mMapBounds.northeast.longitude - bounds.northeast.longitude;
            }
        }

        // Check for inside the limit
        if (latitude == 0.0 && longitude == 0.0) {
            return null;

        } else {
            // Find the actual camera position
            LatLng actual = this.mGoogleMap.getCameraPosition().target;
            // Create a new point inside the limits
            return new LatLng(actual.latitude + latitude, actual.longitude + longitude);
        }
    }

    // Check the map scrolling and zooming limits
    private void checkMapLimits() {
        // Only if instantiate
        if (this.mGoogleMap == null) return;

        // Check for zoom
        if (exceedsLimitBounds()) {
            // Fix the camera position
            CameraUpdate update = CameraUpdateFactory.newLatLngBounds(mMapBounds, this.mPadding);
            this.mGoogleMap.moveCamera(update);

        } else {
            // Fix the position
            LatLng position = fixPositionByConstraints();

            // If must be set
            if (position != null) {
                // Find the camera
                CameraPosition camera = this.mGoogleMap.getCameraPosition();

                // Find a new camera position
                CameraPosition newPosition = new CameraPosition(position, camera.zoom, camera.tilt, camera.bearing);
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(newPosition);

                // Apply
                this.mGoogleMap.moveCamera(update);
            }
        }
    }

    // Override the method for check the boundaries when the map is settled
    @Override
    protected void onSettled() {
        // Call super
        super.onSettled();
        // Check the limits
        this.checkMapLimits();
    }


    /**
     * Public methods
     */

    // Set the map bounds
    @SuppressWarnings("unused")
    public void setBounds(LatLng southWest, LatLng northEast) {
        // Check for null values
        if (southWest == null || northEast == null) {
            // Set null
            this.mMapBounds = null;

        } else {
            // Create new bounds
            this.mMapBounds = new LatLngBounds(southWest, northEast);
        }
        // Check the limits
        this.checkMapLimits();
    }


    /**
     * Public properties
     */

    // The map padding when repositioning happen
    // Default value is 20.
    @SuppressWarnings("unused")
    public int getMapPadding() {
        return this.mPadding;
    }

    @SuppressWarnings("unused")
    public void setMapPadding(int value) {
        this.mPadding = value;
    }

}

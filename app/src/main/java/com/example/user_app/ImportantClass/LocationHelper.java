package com.example.user_app.ImportantClass;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

public class LocationHelper {
    private Context context;
    private LocationManager locationManager;

    public LocationHelper(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public LatLng getLatitudeLongitude() {
        // Check if location permissions are granted
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    double latitude = lastKnownLocation.getLatitude();
                    double longitude = lastKnownLocation.getLongitude();
                    return new LatLng(latitude, longitude);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
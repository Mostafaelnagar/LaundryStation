package app.laundrystation.common;

import android.content.Context;
import android.location.LocationManager;

public class Validation {
//    /<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    public static boolean isLocationEnabled(Context context) {
        LocationManager lm = null;
        boolean gps_enabled = false, network_enabled = false;
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return (gps_enabled || network_enabled) ? true : false;
    }
}

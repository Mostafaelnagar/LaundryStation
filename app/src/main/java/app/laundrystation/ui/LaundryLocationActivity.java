package app.laundrystation.ui;


import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import app.laundrystation.R;

public class LaundryLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    String lat, lng;
    String LaundryName;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.iv_select_location_map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (getIntent() != null) {
            lat = getIntent().getStringExtra("LaundryLat");
            lng = getIntent().getStringExtra("LaundryLng");
            Log.i("onMapReady", "onMapReady: " + lat);
            LaundryName = getIntent().getStringExtra("LaundryName");
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 15));
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                    .title(LaundryName)
            );
        }
    }
}

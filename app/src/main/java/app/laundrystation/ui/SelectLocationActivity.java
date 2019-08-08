package app.laundrystation.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import app.laundrystation.R;
import app.laundrystation.common.Validation;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
     Context mContext;
     SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    private LocationManager service;
    private boolean enabled;
    private double lat, lng;
    @BindView(R.id.tv_select_location_save)
    Button saveBtn;

    public SelectLocationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_location);

        ButterKnife.bind(this);

        mContext = this;
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.iv_select_location_map);

        saveBtn = findViewById(R.id.tv_select_location_save);

        client = LocationServices.getFusedLocationProviderClient(this);
        service = (LocationManager) this.getSystemService(LOCATION_SERVICE);
//        //return the GPS Status if Enabled or not
        enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            getLocationRequest();
        } else
            getCurrentLocation();

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void getCurrentLocation() {
        ActivityCompat.requestPermissions(SelectLocationActivity.this, new String[]{ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(SelectLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(SelectLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    //todo add new lat lng to map
                    Log.i("onSuccess", "onSuccess: " + lat + " lng " + lng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(getResources().getString(R.string.app_name))
                    );
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            if (lat == 0.0 && lng == 0.0) {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.emptyLocation), Toast.LENGTH_SHORT).show();
                            } else {
                                try {

                                    intent.putExtra("lat", lat);
                                    intent.putExtra("lang", lng);
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    });
                }


            }
        });
    }

    private void getLocationRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectLocationActivity.this);
        builder.setMessage("Enable Your GPS?!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Enable GPS
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            getCurrentLocation();
        }
    }
}
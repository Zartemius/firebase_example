package com.example.artem.firstappwithnotification.mainscreen;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.artem.firstappwithnotification.R;
import com.example.artem.firstappwithnotification.view.LoggingInActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnSuccessListener;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainScreen extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MainScreenViewModel mMainScreenViewModel;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    double latitude;
    double longitude;
    private final static int REQUEST_LOCATION_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mMainScreenViewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        //Log.i("ORDER_FROM_MAIN_SCREEN", "order id " + listOfOrders.get(0).getUserId());
    }

    @OnClick(R.id.main_activity__button)
    void signOut(){
        mMainScreenViewModel.signOut();
        Intent intent = new Intent(this, LoggingInActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.main_activity__storage)
    void callStorage(){
        //Intent intent = new Intent(this, StorageOfReports.class);
        //startActivity(intent);
    }

    @Override
    public void onBackPressed() {
       this.finishAffinity();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Log.i("LOCATION_IN_FUSE", "latitude " + latitude);
                            Log.i("LOCATION_IN_FUSE", "longitude " + longitude);

                            Log.i("LOCATION", "latitude " + latitude);
                            Log.i("LOCATION", "longitude " + longitude);

                            LatLng current_location = new LatLng(latitude,longitude);

                            mMap.addMarker(new MarkerOptions().position(current_location).title("Saint-Petersburg"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));

                            mMap.setMinZoomPreference(9);
                            mMap.setMaxZoomPreference(30);

                            PolylineOptions plo = new PolylineOptions();
                            plo.add(current_location);
                            plo.color(Color.BLUE);
                            plo.geodesic(true);
                            plo.startCap(new RoundCap());
                            plo.width(20);
                            plo.jointType(JointType.BEVEL);

                            mMap.addPolyline(plo);
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                else{
                    Log.i("GET_LOCATION_PERMISSION", "getLocation: permissions denied");
                }
                break;
        }
    }
}

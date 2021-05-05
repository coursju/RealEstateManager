package com.openclassrooms.realestatemanager.controler;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.EstateWithPhotos;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.EstateListViewModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

public class MapsEstateActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsEstateActivity";
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker actualPosition;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_estate);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            if (Utils.isConnected()) {
                getActualLocation();
                showEstates();
                configureMarkersListeners();
            }else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getActualLocation() {
        Log.i(TAG, "getActualLocation");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Log.i(TAG, "getActualLocation: onSuccess");

                        Location location = task.getResult();
                        if (location != null) {
                            LatLng actualLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            actualPosition = mMap.addMarker(new MarkerOptions().position(actualLocation).title(getResources().getString(R.string.im_here)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocation, 15));
                            updateLocation();
                        }
                    }
                });
    }

    public void updateLocation() {
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationProvider gpsProvider =
                locationManager.getProvider(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng newPostion = new LatLng(location.getLatitude(), location.getLongitude());
                actualPosition.setPosition(newPostion);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    public void showEstates(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getApplicationContext());
        EstateListViewModel estateListViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(EstateListViewModel.class);
                estateListViewModel.getEstateWithPhotos().observe(this, estateWithPhotosList ->{
                            if (estateWithPhotosList != null) {
                        if (mMap != null) {
                            for (EstateWithPhotos estateWithPhotos : estateWithPhotosList){
                                Executors.newCachedThreadPool().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            List<Address> mAddressList = null;
                                            if (estateWithPhotos != null)
                                                mAddressList = new Geocoder(getApplicationContext()).getFromLocationName(estateWithPhotos.getEstate().getAddress() + " " + estateWithPhotos.getEstate().getCity(), 1);
                                            Log.i(TAG, "updateMapAddress");
                                            List<Address> finalMAddressList = mAddressList;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (finalMAddressList != null && !finalMAddressList.isEmpty()) {
                                                        LatLng mLatLng = new LatLng(finalMAddressList.get(0).getLatitude(), finalMAddressList.get(0).getLongitude());
                                                        mMap.addMarker(new MarkerOptions().position(mLatLng)).setTag(i);
                                                        i++;
                                                    }
                                                }
                                            });

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
                );
    }

    public void configureMarkersListeners(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MapsEstateActivity.this, DetailsActivity.class);
                intent.putExtra("position",(int)marker.getTag());
                startActivity(intent);
                return true;
            }
        });
    }
}



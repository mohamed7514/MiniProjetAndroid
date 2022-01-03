package com.example.examentp;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import kotlin.LateinitKt;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
              String location= searchView.getQuery().toString();
                List<Address> adressList = null;
              if (location !=null || !location.equals("")){
                  Geocoder geocoder = new Geocoder(MapActivity.this);
                  try {
                      adressList=geocoder.getFromLocationName(location,1);
                  }catch (IOException e){
                      e.printStackTrace();
                  }
                  Address address= adressList.get(0);
                  LatLng latLng= new LatLng(address.getLatitude(),address.getLongitude());
                  map.addMarker(new MarkerOptions().position(latLng).title(location));
                  map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
              }
              return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
    }
}
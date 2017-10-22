package com.rashid.bharadwaj.party;

import android.content.Context;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class DashboardActivity extends AppCompatActivity {
    private MapView mapView;
    LocationManager lm;
    public int numClicks = 1;
    public View extendedView;
    public View tintedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Mapbox.getInstance(this, "pk.eyJ1IjoicmlmYXRyYXNoaWQiLCJhIjoiOExWXzZpVSJ9.gEuYvTL_aXc7fqZMegK9kw");
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setMyLocationEnabled(true);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(47.670370, -122.120182))
                        .zoom(13)
                        .bearing(180)
                        .tilt(30)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000);

                mapboxMap.getUiSettings().setTiltGesturesEnabled(false);
            }
        });

        /*
         * Later implementation...
         */
        tintedView = (View) findViewById(R.id.tintedBG);
        extendedView = (View) findViewById(R.id.extendedLayout);

        Button b = (Button) findViewById(R.id.hostPartyButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numClicks++;
                // even

                if(numClicks%2 == 0){
                    tintedView.setVisibility(View.VISIBLE);
                    extendedView.setVisibility(View.VISIBLE);
                }else{
                    tintedView.setVisibility(View.INVISIBLE);
                    extendedView.setVisibility(View.INVISIBLE);
                }

            }
        });
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 150, 1, this);
//
        // Mapbox.getInstance(this,)
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}

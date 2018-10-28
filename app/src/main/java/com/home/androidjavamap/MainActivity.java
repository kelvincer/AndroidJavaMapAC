package com.home.androidjavamap;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.home.androidjavamap.entities.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private GoogleMap mMap;
    MainViewModel viewModel;
    private List<Result> results;
    TextView placeName, placeAddress;
    private Marker lastClickedMarker;
    SupportMapFragment mapFragment;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        setUpObservers();
        setUpViews();
    }

    private void setUpObservers() {

        viewModel.getDataResult().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                Log.d(LOG_TAG, results.toString());
                fillMap(results);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
        viewModel.getPlaces();
    }

    private void fillMap(List<Result> results) {

        this.results = results;
       /* for (Result r : results) {
            LatLng latLng = new LatLng(r.getGeometry().getLocation().getLat(), r.getGeometry().getLocation().getLng());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            marker.setTag(r.getPlaceId());
        }*/

        for (int i = 0; i < results.size(); i++) {
            LatLng latLng = new LatLng(results.get(i).getGeometry().getLocation().getLat(), results.get(i).getGeometry().getLocation().getLng());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            ;
            marker.setTag(i);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(results.get(0).getGeometry().getLocation().getLat(),
                results.get(0).getGeometry().getLocation().getLng()), 12.0f));
    }


    private void setUpViews() {

        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        placeName = llBottomSheet.findViewById(R.id.txv_place_name);
        placeAddress = llBottomSheet.findViewById(R.id.txv_place_detail);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        setZoomControlPosition();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        int index = (Integer) marker.getTag();
        if (lastClickedMarker != null)
            lastClickedMarker.setIcon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Result result = results.get(index);
        placeName.setText(result.getName());
        placeAddress.setText(result.getFormattedAddress());
        lastClickedMarker = marker;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        return false;
    }

    private void setZoomControlPosition() {

        View zoomControls = mapFragment.getView().findViewById(0x1);
        if (zoomControls != null && zoomControls.getLayoutParams() instanceof RelativeLayout.LayoutParams) {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) zoomControls.getLayoutParams();

            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
            }

            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics());
            params.setMargins(margin, margin, margin, margin);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.setMarginEnd(margin);
            }
        }
    }
}

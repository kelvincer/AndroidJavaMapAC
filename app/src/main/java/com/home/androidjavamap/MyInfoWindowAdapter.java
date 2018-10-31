package com.home.androidjavamap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.home.androidjavamap.entities.Result;

import java.util.List;

class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private String name, vicinity;
    private List<Result> resultList;

    public MyInfoWindowAdapter(Context context) {
        this.resultList = resultList;

        this.name = name;
        this.vicinity = vicinity;
        myContentsView = LayoutInflater.from(context).inflate(
                R.layout.custom_info_contents, null);
    }

    @Override
    public View getInfoContents(Marker marker) {


        TextView tvTitle =  myContentsView.findViewById(R.id.title);
        tvTitle.setText(marker.getTitle());

        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

}
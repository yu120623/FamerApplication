package com.project.farmer.famerapplication.farm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmModel;

public class FarmMapFragment extends Fragment {
    private MapView mapView;
    private AMap map;
    private View view;
    private FarmModel farmModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.frag_farm_map, container, false);
        findViews();
        mapView.onCreate(savedInstanceState);
        initData();
        return view;
    }

    private void initData() {
        map = mapView.getMap();
        farmModel = (FarmModel) this.getArguments().getSerializable("farmModel");
        LatLng latlng = new LatLng(farmModel.getFarmLatitude(),farmModel.getFarmLongitude());
        map.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
        MarkerOptions mark = new MarkerOptions();
        mark.icon(BitmapDescriptorFactory.defaultMarker());
        mark.position(latlng);
        map.addMarker(mark);
        map.moveCamera(CameraUpdateFactory.zoomTo(20));
    }

    private void findViews() {
        mapView = (MapView) view.findViewById(R.id.map);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}

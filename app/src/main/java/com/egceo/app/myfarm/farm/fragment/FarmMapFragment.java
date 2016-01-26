package com.egceo.app.myfarm.farm.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.home.activity.MapNavActivity;

import de.greenrobot.event.EventBus;

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
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getActivity(), MapNavActivity.class);
                intent.putExtra("latitude", farmModel.getFarmLatitude());
                intent.putExtra("longitude",farmModel.getFarmLongitude());
                startActivity(intent);
                return false;
            }
        });
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void onEvent(Integer index){
        if(index.intValue() == 2){
            EventBus.getDefault().post(false);
        }
    }
}

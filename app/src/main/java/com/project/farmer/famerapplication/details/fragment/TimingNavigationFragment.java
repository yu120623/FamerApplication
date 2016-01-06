package com.project.farmer.famerapplication.details.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.baseandroid.BaseFragment;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmItemsModel;
import com.project.farmer.famerapplication.entity.FarmSetModel;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.List;

public class TimingNavigationFragment extends Fragment {
    private MapView mapView;
    private AMap map;
    private View view;
    private FarmSetModel farmSetModel;
    private LinearLayout navContent;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.frag_timing_navigation, container, false);
        findViews();
        mapView.onCreate(savedInstanceState);
        initData();
        addMark();
        addItem();
        return view;
    }

    private void addItem() {
        List<FarmItemsModel> farmItemsModels = farmSetModel.getFarmItemsModels();
        for (int i = 0; i < farmItemsModels.size(); i++) {
            FarmItemsModel item = farmItemsModels.get(i);
            View view = ((LinearLayout) inflater.inflate(R.layout.item_farm_set_nav, navContent, true)).getChildAt(navContent.getChildCount() - 1);
            TextView itemName = (TextView) view.findViewById(R.id.farmset_item_name);
            TextView itemTag = (TextView) view.findViewById(R.id.farmset_item_tag);
            TextView itemDesc = (TextView) view.findViewById(R.id.farmset_item_desc);
            TextView itemDistance = (TextView) view.findViewById(R.id.farm_set_distance);
            itemName.setText(item.getFarmName());
            itemTag.setText(AppUtil.getFarmSetTag(item.getFarmItemType()));
            itemDesc.setText(item.getFarmItemName());
            itemDistance.setText(item.getDistance()+"");
        }
    }

    private void addMark() {
        List<FarmItemsModel> farmItemsModels = farmSetModel.getFarmItemsModels();
        for (int i = 0; i < farmItemsModels.size(); i++) {
            MarkerOptions mark = new MarkerOptions();
            FarmItemsModel item = farmItemsModels.get(i);
            mark.position(new LatLng(item.getFarmLatitude(), item.getFarmLongitude()));
            mark.icon(BitmapDescriptorFactory.defaultMarker());
            mark.title(item.getFarmItemName());
            map.addMarker(mark);

        }
    }

    private void initData() {
        inflater = LayoutInflater.from(getActivity().getApplicationContext());
        farmSetModel = (FarmSetModel) this.getArguments().getSerializable("farmSet");
        map = mapView.getMap();
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
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

    private void findViews() {
        navContent = (LinearLayout) view.findViewById(R.id.nav_content);
        mapView = (MapView) view.findViewById(R.id.map);
    }

}

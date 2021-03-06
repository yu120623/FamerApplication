package com.egceo.app.myfarm.topic.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.home.activity.MapNavActivity;
import com.egceo.app.myfarm.util.AppUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class TimingNavigationFragment extends Fragment {
    private MapView mapView;
    private AMap map;
    private View view;
    private ScrollView scrollView;
    private FarmSetModel farmSetModel;
    private LinearLayout navContent;
    private LayoutInflater inflater;
    private List<Marker> markers;
    private DecimalFormat decimalFormat;

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
        if (null == farmItemsModels || farmItemsModels.size() <= 0) return;
        for (int i = 0; i < farmItemsModels.size(); i++) {
            FarmItemsModel item = farmItemsModels.get(i);
            View view = ((LinearLayout) inflater.inflate(R.layout.item_farm_set_nav, navContent, true)).getChildAt(navContent.getChildCount() - 1);
            if(i == farmItemsModels.size() -1)
                view.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.button_min_height));
            TextView itemName = (TextView) view.findViewById(R.id.farmset_item_name);
            TextView itemTag = (TextView) view.findViewById(R.id.farmset_item_tag);
            TextView itemDesc = (TextView) view.findViewById(R.id.farmset_item_desc);
            TextView itemDistance = (TextView) view.findViewById(R.id.farm_set_distance);
            Button navBtn = (Button) view.findViewById(R.id.nav_btn_bg);
            itemName.setText(item.getFarmName());
            itemTag.setText(AppUtil.getFarmSetTag(item.getFarmItemType()));
            itemTag.setBackgroundResource(AppUtil.getFarmSetTagBg(item.getFarmItemType()));
            itemDesc.setText(item.getFarmItemName());
            itemDistance.setText(decimalFormat.format(item.getDistance()));
            navBtn.setTag(item);
            navBtn.setOnClickListener(onNavBtnClick);
        }
    }

    private View.OnClickListener onNavBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FarmItemsModel item = (FarmItemsModel) view.getTag();
            Intent intent = new Intent(getActivity(), MapNavActivity.class);
            intent.putExtra("latitude", item.getFarmLatitude());
            intent.putExtra("longitude",item.getFarmLongitude());
            startActivity(intent);
        }
    };

    private void addMark() {
        List<FarmItemsModel> farmItemsModels = farmSetModel.getFarmItemsModels();
        if (null == farmItemsModels || farmItemsModels.size() <= 0) return;
        for (int i = 0; i < farmItemsModels.size(); i++) {
            MarkerOptions mark = new MarkerOptions();
            FarmItemsModel item = farmItemsModels.get(i);
            mark.position(new LatLng(item.getFarmLatitude(), item.getFarmLongitude()));
            mark.icon(AppUtil.getMarkResource(item.getFarmItemType()));
            mark.title(item.getFarmItemName());
            markers.add(map.addMarker(mark));
        }
    }

    private void initData() {
        decimalFormat = new DecimalFormat("#.##");
        markers = new ArrayList<>();
        inflater = LayoutInflater.from(getActivity().getApplicationContext());
        farmSetModel = (FarmSetModel) this.getArguments().getSerializable("farmSet");
        map = mapView.getMap();
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setOnMapLoadedListener(onloadListener);
        map.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }

    private AMap.OnMapLoadedListener onloadListener = new AMap.OnMapLoadedListener() {
        @Override
        public void onMapLoaded() {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i = 0; i < markers.size(); i++) {
                builder.include(markers.get(i).getPosition());
            }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 150));
        }
    };

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

    public void onEvent(Integer index) {
        if (index.intValue() == 2) {
            EventBus.getDefault().post(AttachUtil.isScrollViewAttach(scrollView));
        }
    }


    private void findViews() {
        navContent = (LinearLayout) view.findViewById(R.id.nav_content);
        mapView = (MapView) view.findViewById(R.id.map);
        scrollView = (ScrollView) view.findViewById(R.id.scroll_view);

    }

}

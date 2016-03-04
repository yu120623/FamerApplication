package com.egceo.app.myfarm.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Text;
import com.baseandroid.BaseActivity;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.util.AppUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by FreeMason on 2016/2/18.
 */
public class FarmSetNavListActivity extends Activity {
    private MapView mapView;
    private AMap map;
    private FarmSetModel farmSetModel;
    private LinearLayout navContent;
    private LayoutInflater inflater;
    private List<Marker> markers;
    private DecimalFormat decimalFormat;
    private Context context;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmset_nav_list);
        context = getApplicationContext();
        sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
        findViews();
        mapView.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        TextView actionBarTitle = (TextView) this.findViewById(com.baseandroid.R.id.action_bar_title);
        actionBarTitle.setText("线路");
        ImageView back = (ImageView) this.findViewById(com.baseandroid.R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        markers = new ArrayList<>();
        decimalFormat = new DecimalFormat("#.##");
        inflater = LayoutInflater.from(getApplicationContext());
        map = mapView.getMap();
        farmSetModel = (FarmSetModel) this.getIntent().getSerializableExtra("farmSetModel");
        map.getUiSettings().setCompassEnabled(false);
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
        addItem();
        addMark();
    }

    private void addMark() {
        List<FarmItemsModel> farmItemsModels = farmSetModel.getFarmItemsModels();
        if (null == farmItemsModels || farmItemsModels.size() <= 0) return;
        for (int i = 0; i < farmItemsModels.size(); i++) {
            MarkerOptions mark = new MarkerOptions();
            FarmItemsModel item = farmItemsModels.get(i);
            mark.position(new LatLng(item.getFarmLatitude(), item.getFarmLongitude()));
            mark.icon(AppUtil.getMarkResource(item.getFarmItemType()));
            mark.title(item.getFarmItemName());
            Marker marker = map.addMarker(mark);
            markers.add(marker);
        }
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
            Intent intent = new Intent(FarmSetNavListActivity.this, MapNavActivity.class);
            intent.putExtra("latitude", item.getFarmLatitude());
            intent.putExtra("longitude",item.getFarmLongitude());
            startActivity(intent);
        }
    };

    private void findViews() {
        mapView = (MapView) this.findViewById(R.id.map);
        navContent = (LinearLayout) this.findViewById(R.id.nav_content);
    }

    private AMap.OnMapLoadedListener onloadListener = new AMap.OnMapLoadedListener() {
        @Override
        public void onMapLoaded() {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i = 0; i < markers.size(); i++) {
                builder.include(markers.get(i).getPosition());
            }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 150));
            map.moveCamera(CameraUpdateFactory.zoomOut());
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

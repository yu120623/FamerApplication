package com.egceo.app.myfarm.city.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.Code;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends BaseActivity {
    private View locationBtn;
    private TextView locationName;
    private double lat;
    private double log;
    private boolean isLocation = true;//判断是否正在定位
    private boolean isLocationSuccess = false;//判断是否定位成功
    private List<Code> hotCity = new ArrayList<Code>();
    private List<Code> allCity = new ArrayList<Code>();
    private LinearLayout hotCityLayout;
    private RecyclerView cityList;
    private CityAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private View header;
    private AMapLocation location;
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLocation)
                    return;
                if(isLocationSuccess){
                    Intent intent = new Intent();
                    Code code = new Code();
                    code.setCodeDesc(location.getCity());
                    code.setCodeName(location.getCityCode());
                    intent.putExtra("city",code);
                    setResult(RESULT_OK,intent);
                    finish();
                    return;
                }
                location();
            }
        });
    }

    private void initCityList() {
        cityList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CityAdapter();
        header = inflater.inflate(R.layout.city_header,null);
        header.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT));
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        cityList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(cityList, header);
        hotCityLayout = (LinearLayout) header.findViewById(R.id.hot_city_layout);
    }

    private void showHotCity() {
        int lines = (hotCity.size() / 4)+(hotCity.size() % 4 > 0 ? 1:0);//计算热门城市有几行
        for(int i = 0;i < lines;i++){
            addHotCity(i);
        }
        adapter.notifyDataSetChanged();;
    }

    private void addHotCity(int lineIndex){
        LinearLayout tagsLineLayout = getLineLayout();//生成每行热门城市的layout
        for(int i = 0; i < 4; i++){
            int index = i+(lineIndex*4);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView hotNameTextView = new TextView(context);
            params.weight = 1;
            hotNameTextView.setGravity(Gravity.CENTER);
            hotNameTextView.setLayoutParams(params);
            hotNameTextView.setTextColor(getResources().getColor(R.color.black));
            hotNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            hotNameTextView.setPadding(CommonUtil.Dp2Px(context,5),CommonUtil.Dp2Px(context,5),CommonUtil.Dp2Px(context,5),CommonUtil.Dp2Px(context,5));
            String tagName = "";
            if(index >= hotCity.size()) {
                tagName = "";
                hotNameTextView.setVisibility(View.INVISIBLE);
            }
            else {
                tagName = hotCity.get(i).getCodeDesc();
                hotNameTextView.setTag(hotCity.get(i));
                hotNameTextView.setOnClickListener(onChangeCity);
            }
            hotNameTextView.setText(tagName);
            tagsLineLayout.addView(hotNameTextView);
        }
        hotCityLayout.addView(tagsLineLayout);
    }

    private LinearLayout getLineLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setWeightSum(4);
        return linearLayout;
    }

    //获取城市列表和推荐城市
    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.CITY_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                hotCity = data.getCodeModel().getCodeRecommend();
                allCity = data.getCodeModel().getCodeAll();
                if(hotCity != null && hotCity.size() > 0){
                    showHotCity();
                }
                if(allCity != null && allCity.size() > 0){
                    adapter.notifyDataSetChanged();
                }

            }
        },data);
        request.execute();
    }

    private void location() {
        locationName.setText(R.string.location_now);
        AMapLocationClient mLocationClient = new AMapLocationClient(context);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                location = aMapLocation;
                if(aMapLocation.getErrorCode() == 0){
                    lat = aMapLocation.getLatitude();
                    log = aMapLocation.getLongitude();
                    locationName.setText(aMapLocation.getCity());
                    isLocationSuccess = true;
                }else{
                    locationName.setText(R.string.location_failed);
                }
                isLocation = false;
            }
        });
        mLocationClient.startLocation();
    }


    private void initData() {
        location();
        initCityList();
        loadDataFromServer();
    }


    class CityAdapter extends RecyclerView.Adapter<CityViewHolder>{

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_city,null,false);
            RecyclerView.LayoutParams param = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
            param.bottomMargin = CommonUtil.Dp2Px(context,10);
            view.setLayoutParams(param);
            view.setOnClickListener(onChangeCity);
            return new CityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            holder.cityName.setText(allCity.get(position).getCodeDesc());
            holder.itemView.setTag(allCity.get(position));
        }

        @Override
        public int getItemCount() {
            return allCity.size();
        }
    }

    public View.OnClickListener onChangeCity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            Code city = (Code) view.getTag();
            intent.putExtra("city",city);
            setResult(RESULT_OK,intent);
            finish();
        }
    };

    class CityViewHolder extends RecyclerView.ViewHolder{
        private TextView cityName;
        public CityViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
        }
    }


    private void findViews() {
        locationBtn = this.findViewById(R.id.location_btn);
        locationName = (TextView) this.findViewById(R.id.location_name);
        cityList = (RecyclerView) this.findViewById(R.id.city_list);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_city;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.choose_ctiy);
    }
}

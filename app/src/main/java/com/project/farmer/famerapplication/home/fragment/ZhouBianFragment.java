package com.project.farmer.famerapplication.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.farm.activity.FarmDetailActivity;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import org.apmem.tools.layouts.FlowLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ZhouBianFragment extends BaseFragment {
    private RecyclerView nearList;
    private DisplayImageOptions options;
    private List<FarmModel> farmAroundListModels;
    private NearAdapter adapter;
    private DecimalFormat decimalFormat;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        decimalFormat = new DecimalFormat("#.##");
        nearList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new NearAdapter();
        farmAroundListModels = new ArrayList<FarmModel>();
        nearList.setAdapter(adapter);
        nearList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
            }
        });
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY).build();
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_AROUND_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(0);
        data.setType("around");
        data.setFarmLatitude(Float.valueOf(sp.getFloat(AppUtil.SP_LAT,0)).doubleValue());
        data.setFarmLongitude(Float.valueOf(sp.getFloat(AppUtil.SP_LOG,0)).doubleValue());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmAroundListModels = data.getFarmModels();
                if (farmAroundListModels != null && farmAroundListModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
            }
        }, data);
        request.execute();
    }

    private void findViews() {
        nearList = (RecyclerView) this.findViewById(R.id.near_list);
    }

    class NearAdapter extends RecyclerView.Adapter<RecommendViewHolder> implements View.OnClickListener {
        @Override
        public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_recommend, null);
            RecommendViewHolder holder = new RecommendViewHolder(v);
            v.setOnClickListener(this);
            return holder;
        }
        @Override
        public void onBindViewHolder(RecommendViewHolder holder, int position) {
            FarmModel farmModel = farmAroundListModels.get(position);
            holder.itemView.setTag(farmModel);
            holder.recommendName.setText(farmModel.getFarmName());
            holder.recommendArea.setText(farmModel.getFarmArea());
            holder.recommendReason.setText(farmModel.getFarmDesc());
            holder.recommendTuijian.setVisibility(View.INVISIBLE);
            holder.recommendDistance.setText(decimalFormat.format(farmModel.getFarmDistance()) + "km");
            for (int i = 0; i < farmModel.getFarmTags().size(); i++) {
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(getActivity());
                TextView tv1 = new TextView(getActivity());
                tv1.setText("  ");
                tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                tv.setBackgroundResource(R.drawable.near_label_bg);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                tv.setTextColor(Color.WHITE);
                tv.setPadding(20, 2, 20, 2);
                tv.setText(farmModel.getFarmTags().get(i));
                tv.setLayoutParams(lp);
                tv1.setLayoutParams(lp);
                holder.flowLayout.addView(tv);
                holder.flowLayout.addView(tv1);
            }
            ImageLoaderUtil.getInstance().displayImg(holder.recommendImg, farmModel.getResourcePath(), options);
        }

        @Override
        public int getItemCount() {
            return farmAroundListModels.size();
        }

        @Override
        public void onClick(View v) {
            FarmModel farmModel = (FarmModel) v.getTag();
            Intent intent = new Intent();
            intent.putExtra("farmModel",farmModel);
            intent.setClass(getActivity(), FarmDetailActivity.class);
            startActivity(intent);
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView recommendName;
        private TextView recommendArea;
        private TextView recommendReason;
        private ImageView recommendImg;
        private TextView recommendTuijian;
        private TextView recommendDistance;
        private FlowLayout flowLayout;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            recommendName = (TextView) itemView.findViewById(R.id.recommend_name);
            recommendArea = (TextView) itemView.findViewById(R.id.recommend_area);
            recommendReason = (TextView) itemView.findViewById(R.id.recommend_reason);
            recommendImg = (ImageView) itemView.findViewById(R.id.recommend_img);
            recommendTuijian = (TextView) itemView.findViewById(R.id.tuijian);
            recommendDistance = (TextView) itemView.findViewById(R.id.distancen);
            flowLayout = (FlowLayout) itemView.findViewById(R.id.flowLayout);

        }

    }

    public void onEvent(Integer index) {
        if (index.intValue() == 2) {
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(nearList));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_zhoubian;
    }
}

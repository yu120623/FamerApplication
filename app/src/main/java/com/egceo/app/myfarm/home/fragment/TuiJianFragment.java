package com.egceo.app.myfarm.home.fragment;

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
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import org.apmem.tools.layouts.FlowLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/16.
 */
public class TuiJianFragment extends BaseFragment {
    private RecyclerView recommendList;
    private DisplayImageOptions options;
    private List<FarmModel> farmTuiJianListModels;
    private RecommendAdapter adapter;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        recommendList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new RecommendAdapter();
        recommendList.setAdapter(adapter);
        farmTuiJianListModels = new ArrayList<FarmModel>();
        recommendList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        data.setType("recommend");
        data.setFarmLatitude(Float.valueOf(sp.getFloat(AppUtil.SP_LAT, 0)).doubleValue());
        data.setFarmLongitude(Float.valueOf(sp.getFloat(AppUtil.SP_LOG, 0)).doubleValue());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmTuiJianListModels = data.getFarmModels();
                if (farmTuiJianListModels != null && farmTuiJianListModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
            }
        }, data);
        request.execute();
    }

    private void findViews() {
        recommendList = (RecyclerView) this.findViewById(R.id.recommend_list);

    }

    class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> {

        @Override
        public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_recommend, null);
            RecommendViewHolder holder = new RecommendViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecommendViewHolder holder, int position) {
            FarmModel farmModel = farmTuiJianListModels.get(position);
            holder.recommendName.setText(farmModel.getFarmName());
            holder.recommendArea.setText(farmModel.getFarmArea());
            holder.recommendReason.setText(farmModel.getFarmDesc());
            holder.recommendTuijian.setVisibility(View.INVISIBLE);
            if (farmModel.getRecommend() != null) {
                holder.recommendTuijian.setVisibility(View.VISIBLE);
            }
            ImageLoaderUtil.getInstance().displayImg(holder.recommendImg, farmModel.getResourcePath()+ AppUtil.FARM_IMG_SIZE, options);
            holder.recommendDistance.setText(new DecimalFormat("#.##").format(farmModel.getFarmDistance()) + "km");
            holder.flowLayout.removeAllViews();
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


        }

        @Override
        public int getItemCount() {
            return farmTuiJianListModels.size();
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
        if (index.intValue() == 3) {
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recommendList));
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
        return R.layout.frag_tuijian;
    }
}

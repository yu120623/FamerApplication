package com.egceo.app.myfarm.home.fragment;

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
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.farm.activity.FarmDetailActivity;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.view.CustomUIHandler;
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
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2015/12/16.
 */
public class TuiJianFragment extends BaseFragment {
    private RecyclerView recommendList;
    private DisplayImageOptions options;
    private List<FarmModel> farmTuiJianListModels;
    private RecommendAdapter adapter;
    private PtrFrameLayout frameLayout;
    private LoadMoreFooter loadMoreFooter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private Integer pageNumber = 0;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        recommendList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    private void initData() {
        recommendList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new RecommendAdapter();
        farmTuiJianListModels = new ArrayList<FarmModel>();
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY).build();loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recommendList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(recommendList,loadMoreFooter.getFooter());
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                recommendList.removeOnScrollListener(loadMoreListener);
                recommendList.addOnScrollListener(loadMoreListener);
                loadMoreFooter.reset();
                loadDataFromServer();
            }
        });
        frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLayout.autoRefresh(true);
            }
        },100);
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_HOT_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                List<FarmModel> list = data.getFarmModels();
                if (list != null && list.size() > 0) {
                    if(pageNumber == 0) {
                        farmTuiJianListModels = list;
                    }else{
                        farmTuiJianListModels.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    if(pageNumber > 0) {
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        recommendList.removeOnScrollListener(loadMoreListener);
                    }else{
                        showRetryView();
                        retryText.setText("暂无数据");
                        retryButton.setVisibility(View.GONE);
                    }
                }
                loadMoreFooter.hideLoadMore();
            }
            @Override
            public void onEnd() {
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
                frameLayout.refreshComplete();
            }
        }, data);
        request.execute();
    }


    //加载更多监听
    private EndlessRecyclerOnScrollListener loadMoreListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            if(loadMoreFooter.isLoading() || farmTuiJianListModels.size() <= 0)return;
            pageNumber++;
            loadMoreFooter.showLoadingTips();
            loadDataFromServer();
        }
    };

    private void findViews() {
        recommendList = (RecyclerView) this.findViewById(R.id.recommend_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);

    }

    class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> {
        @Override
        public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_recommend, null);
            RecommendViewHolder holder = new RecommendViewHolder(v);
            v.setOnClickListener(onClick);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecommendViewHolder holder, int position) {
            FarmModel farmModel = farmTuiJianListModels.get(position);
            holder.itemView.setTag(farmModel);
            holder.recommendName.setText(farmModel.getFarmName());
            holder.recommendArea.setText(farmModel.getFarmArea());
            holder.recommendReason.setText(farmModel.getFarmDesc());
            holder.recommendTuijian.setVisibility(View.INVISIBLE);
            if (farmModel.getRecommend() != null) {
                holder.recommendTuijian.setVisibility(View.VISIBLE);
            }
            ImageLoaderUtil.getInstance().displayImg(holder.recommendImg, farmModel.getResourcePath()+ AppUtil.FARM_FACE, options);
            holder.recommendDistance.setText(context.getString(R.string.line_distance)+new DecimalFormat("#.##").format(farmModel.getFarmDistance()) + "km");
            holder.flowLayout.removeAllViews();
            for (int i = 0; i < farmModel.getFarmTags().size(); i++) {
                View tagItem = inflater.inflate(R.layout.item_farm_tag,null,false);
                TextView tv = (TextView) tagItem.findViewById(R.id.farm_tag);
                tv.setTextColor(Color.WHITE);
                tv.setText(farmModel.getFarmTags().get(i));
                holder.flowLayout.addView(tagItem);
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

    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FarmModel farmModel = (FarmModel) view.getTag();
            Intent intent = new Intent();
            intent.putExtra("farmModel", farmModel);
            intent.setClass(getActivity(), FarmDetailActivity.class);
            startActivity(intent);
        }
    };

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

package com.egceo.app.myfarm.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.view.CustomUIHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.topic.activity.TimingTopicDetailsActivity;
import com.egceo.app.myfarm.topic.activity.TopicDetailsActivity;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.QiangGouBtnView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class QiangGouFragment extends BaseFragment {
    private RecyclerView topicPanicBuyingList;
    private DisplayImageOptions options;
    private List<FarmTopicModel> farmTopicPanicBuyingModels = new ArrayList<FarmTopicModel>();
    private FlashSaleAdapter adapter;
    private Chronometer time;
    private Date nowTime;
    private LoadMoreFooter loadMoreFooter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private Integer pageNumber = 0;
    private PtrFrameLayout frameLayout;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.default_img)
                .showImageOnFail(R.mipmap.default_img)
                .showImageOnLoading(R.mipmap.default_img)
                .displayer(new FadeInBitmapDisplayer(1000))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        topicPanicBuyingList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new FlashSaleAdapter();
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        topicPanicBuyingList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(topicPanicBuyingList,loadMoreFooter.getFooter());
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
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
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.FARM_TOPIC_PANICBUYING_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                List<FarmTopicModel> list = data.getFarmTopicModels();
                if (list != null && list.size() > 0) {
                    if(pageNumber == 0) {
                        farmTopicPanicBuyingModels = list;
                        initTime();
                    }else{
                        farmTopicPanicBuyingModels.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    if(pageNumber > 0)
                        pageNumber--;
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

    private void initTime() {
        nowTime = farmTopicPanicBuyingModels.get(0).getNowTime();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nowTime);
                calendar.add(Calendar.MILLISECOND,1000);
                nowTime = calendar.getTime();
            }
        },0,1000);

    }

    private void findViews() {
        topicPanicBuyingList = (RecyclerView) this.findViewById(R.id.flash_sale_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
    }


    private void initClick() {
        topicPanicBuyingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
            }
        });
        topicPanicBuyingList.addOnScrollListener(loadMoreListener);
    }

    //加载更多监听
    private EndlessRecyclerOnScrollListener loadMoreListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            if(loadMoreFooter.isLoading() || farmTopicPanicBuyingModels.size() <= 0)return;
            pageNumber++;
            loadMoreFooter.showLoadingTips();
            loadDataFromServer();
        }
    };

    class FlashSaleAdapter extends RecyclerView.Adapter<TopicViewHolder> implements View.OnClickListener {
        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.flash_sale_item, null);
            TopicViewHolder holder = new TopicViewHolder(v);
            v.setOnClickListener(onTopicClick);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            holder.itemView.setTag(position);
            FarmTopicModel farmTopicModel = farmTopicPanicBuyingModels.get(position);
            holder.flashSaleName.setText(farmTopicModel.getFarmTopicName());
            holder.flashSaleArea.setText(farmTopicModel.getTagName());
            holder.flashSaleReason.setText(farmTopicModel.getFarmTopicRecomReason());
            holder.qiangGouBtnView.setTime(nowTime.getTime(),farmTopicModel.getFarmTopicBeginTime().getTime(),farmTopicModel.getFarmTopicEndTime().getTime());
            ImageLoaderUtil.getInstance().displayImg(holder.flashSaleImage, farmTopicModel.getResourcePath(), options);
        }

        @Override
        public int getItemCount() {
            return farmTopicPanicBuyingModels.size();
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), TopicDetailsActivity.class);
            startActivity(intent);
        }
    }

    private View.OnClickListener onTopicClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            int position = (int) v.getTag();
            FarmTopicModel farmTopicModel = farmTopicPanicBuyingModels.get(position);
            intent.putExtra("farmTopic",farmTopicModel);
            intent.setClass(getActivity(), TimingTopicDetailsActivity.class);
            startActivity(intent);
        }
    };


    class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView flashSaleName;
        private TextView flashSaleArea;
        private TextView flashSaleReason;
        private ImageView flashSaleImage;
        private QiangGouBtnView qiangGouBtnView;
        public TopicViewHolder(View itemView) {
            super(itemView);
            flashSaleName = (TextView) itemView.findViewById(R.id.topic_name);
            flashSaleArea = (TextView) itemView.findViewById(R.id.topic_area);
            flashSaleReason = (TextView) itemView.findViewById(R.id.topic_reason);
            flashSaleImage = (ImageView) itemView.findViewById(R.id.topic_img);
            qiangGouBtnView = (QiangGouBtnView) itemView.findViewById(R.id.qianggou_btn_view);
        }
    }

    public void onEvent(Integer index) {
        if (index.intValue() == 1) {
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(topicPanicBuyingList));
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
        return R.layout.frag_qianggou;
    }
}

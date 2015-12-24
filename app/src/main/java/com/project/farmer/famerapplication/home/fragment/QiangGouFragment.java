package com.project.farmer.famerapplication.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.details.activity.TopicDetailsActivity;
import com.project.farmer.famerapplication.entity.FarmTopicModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/16.
 */
public class QiangGouFragment extends BaseFragment {
    private RecyclerView topicPanicBuyingList;
    private DisplayImageOptions options;
    private List<FarmTopicModel> farmTopicPanicBuyingModels;
    private FlashSaleAdapter adapter;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(1000))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        topicPanicBuyingList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new FlashSaleAdapter();
        farmTopicPanicBuyingModels = new ArrayList<FarmTopicModel>();
        topicPanicBuyingList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_PANICBUYING_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(0);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmTopicPanicBuyingModels = data.getFarmTopicModels();
                if (farmTopicPanicBuyingModels != null && farmTopicPanicBuyingModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
            }
        }, data);
        request.execute();
    }

    private void findViews() {
        topicPanicBuyingList = (RecyclerView) this.findViewById(R.id.flash_sale_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topicPanicBuyingList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
            }
        });
    }

    class FlashSaleAdapter extends RecyclerView.Adapter<TopicViewHolder> implements View.OnClickListener {

        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.flash_sale_item, null);
            TopicViewHolder holder = new TopicViewHolder(v);
            v.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            FarmTopicModel farmTopicModel = farmTopicPanicBuyingModels.get(position);
            holder.flashSaleName.setText(farmTopicModel.getFarmTopicName());
            holder.flashSaleArea.setText(farmTopicModel.getCodeName());
            holder.flashSaleReason.setText(farmTopicModel.getFarmTopicRecomReason());
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

    class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView flashSaleName;
        private TextView flashSaleArea;
        private TextView flashSaleReason;
        private ImageView flashSaleImage;

        public TopicViewHolder(View itemView) {
            super(itemView);
            flashSaleName = (TextView) itemView.findViewById(R.id.flashsale_name);
            flashSaleArea = (TextView) itemView.findViewById(R.id.flashsale_area);
            flashSaleReason = (TextView) itemView.findViewById(R.id.flashsale_reason);
            flashSaleImage = (ImageView) itemView.findViewById(R.id.flashsale_img);

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

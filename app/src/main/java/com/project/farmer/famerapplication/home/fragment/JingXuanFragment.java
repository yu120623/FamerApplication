package com.project.farmer.famerapplication.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.project.farmer.famerapplication.farmset.activity.FarmSetActivity;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class JingXuanFragment extends BaseFragment {
    private RecyclerView topicList;
    private DisplayImageOptions options;
    private TopicAdapter adapter;
    private List<FarmTopicModel> farmTopicModels;
    private ImageView progress;
    private AnimationDrawable progressDrawable;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topicList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        topicList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TopicAdapter();
        farmTopicModels = new ArrayList<FarmTopicModel>();
        topicList.setAdapter(adapter);
        progressDrawable =  ((AnimationDrawable)progress.getDrawable());
        progressDrawable.start();
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(0);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmTopicModels = data.getFarmTopicModels();
                if(farmTopicModels != null && farmTopicModels.size() > 0){
                    adapter.notifyDataSetChanged();
                    hideProgress();
                }
            }
        },data);
        request.execute();
    }

    private void findViews() {
        topicList = (RecyclerView) this.findViewById(R.id.topic_list);
        progress = (ImageView) this.findViewById(R.id.progress);
    }


    private void hideProgress() {
        progress.setVisibility(View.GONE);
        progressDrawable.stop();
    }

    class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder> implements View.OnClickListener {

        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.topic_item, null);
            TopicViewHolder holder = new TopicViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            FarmTopicModel farmTopicModel = farmTopicModels.get(position);
            holder.topicName.setText(farmTopicModel.getFarmTopicName());
            holder.topicArea.setText(farmTopicModel.getTagName());
            holder.topicReason.setText(farmTopicModel.getFarmTopicRecomReason());
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
            ImageLoaderUtil.getInstance().displayImg(holder.topicImage,farmTopicModel.getResourcePath(), options);
        }

        @Override
        public int getItemCount() {
            return farmTopicModels.size();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            int position = (int) v.getTag();
            FarmTopicModel farmTopicModel = farmTopicModels.get(position);
            intent.putExtra("farmTopic",farmTopicModel);
            intent.setClass(getActivity(), TopicDetailsActivity.class);
            startActivity(intent);
        }
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topicName;
        private TextView topicArea;
        private TextView topicReason;
        private ImageView topicImage;

        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topic_name);
            topicArea = (TextView) itemView.findViewById(R.id.topic_area);
            topicReason = (TextView) itemView.findViewById(R.id.topic_reason);
            topicImage = (ImageView) itemView.findViewById(R.id.topic_img);
        }
    }

    public void onEvent(Integer index){
        if(index.intValue() == 0){
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(topicList));
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
        return R.layout.frag_jingxuan;
    }
}

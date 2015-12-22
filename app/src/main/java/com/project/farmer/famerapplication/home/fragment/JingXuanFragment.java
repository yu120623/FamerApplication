package com.project.farmer.famerapplication.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class JingXuanFragment extends BaseFragment {
    private RecyclerView topicList;
    private DisplayImageOptions options;
    private String[] url = {"http://oss.mycff.com/images/00001.png" ,
            "http://oss.mycff.com/images/00002.png",
            "http://oss.mycff.com/images/00003.png",
            "http://oss.mycff.com/images/00004.png",
            "http://s.mycff.com/images/direct/564d2ce239fc9.png",
            "http://s.mycff.com/images/2015/10/04cb5dee5845984129bd6265bcfde0b8.jpg",
            "http://s.mycff.com/images/direct/5653d669599bb.jpg",
            "http://s.mycff.com/images/direct/56385f05a53e6.jpg",
            "http://s.mycff.com/images/direct/56385fdc8d19b.jpg",
            "http://s.mycff.com/images/direct/56385f5b7de10.jpg",
            "http://s.mycff.com/images/direct/566e7f8602140.jpg",
            "http://s.mycff.com/images/direct/56385e6e9e621.jpg",
            "http://s.mycff.com/images/direct/564170c898a43.jpg"};

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topicList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        topicList.setAdapter(new TopicAdapter());
    }

    private void findViews() {
        topicList = (RecyclerView) this.findViewById(R.id.topic_list);
    }


    class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder> implements View.OnClickListener {

        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.topic_item, null);
            TopicViewHolder holder = new TopicViewHolder(v);
            v.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            holder.topicName.setText("农庄标题");
            holder.topicArea.setText("苏州");
            holder.topicReason.setText("推荐理由或者简单介绍");
            ImageLoaderUtil.getInstance().displayImg(holder.topicImage, url[position], options);
        }

        @Override
        public int getItemCount() {
            return url.length;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
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

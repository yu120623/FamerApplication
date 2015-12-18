package com.project.farmer.famerapplication.home.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.project.farmer.famerapplication.R;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class JingXuanFragment extends BaseFragment {
    private RecyclerView topicList;
    private DisplayImageOptions options;
    private String url = "http://img1.imgtn.bdimg.com/it/u=3654508348,624460089&fm=21&gp=0.jpg";

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
                Log.i("++++++++++++","++++++++++++++113131321");
            }
        });
    }

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        topicList.setLayoutManager(new LinearLayoutManager(context));
        topicList.setAdapter(new TopicAdapter());
    }

    private void findViews() {
        topicList = (RecyclerView) this.findViewById(R.id.topic_list);
    }


    class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder> {

        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(),R.layout.topic_item,null);
            TopicViewHolder holder = new TopicViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            holder.topicName.setText("农庄标题AAA");
            holder.topicArea.setText("苏州");
            holder.topicReason.setText("农庄环境很好，庄主人很好");
            ImageLoaderUtil.getInstance().displayImg(holder.topicImage,url,options);

        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topicName;
        private TextView topicArea;
        private TextView topicReason;
        private ImageView topicImage;

        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName= (TextView) itemView.findViewById(R.id.topic_name);
            topicArea= (TextView) itemView.findViewById(R.id.topic_area);
            topicReason= (TextView) itemView.findViewById(R.id.topic_reason);
            topicImage = (ImageView) itemView.findViewById(R.id.topic_img);

        }

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_jingxuan;
    }
}

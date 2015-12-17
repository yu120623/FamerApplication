package com.project.farmer.famerapplication.home.fragment;

import android.graphics.Bitmap;
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
import com.project.farmer.famerapplication.R;

import static com.project.farmer.famerapplication.R.id.id_cancel;

public class JingXuanFragment extends BaseFragment {
    private RecyclerView topicList;
    private DisplayImageOptions options;
    private String url = "http://img1.imgtn.bdimg.com/it/u=3654508348,624460089&fm=21&gp=0.jpg";

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
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
            holder.topicName.setText("某某某的牧场");
            holder.topicArea.setText("苏州");
            holder.topicReason.setText("环境很好，庄主人很好");
            options = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
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

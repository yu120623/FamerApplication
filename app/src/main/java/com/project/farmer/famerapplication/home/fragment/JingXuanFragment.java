package com.project.farmer.famerapplication.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.project.farmer.famerapplication.R;

public class JingXuanFragment extends BaseFragment {
    private RecyclerView topicList;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        topicList.setLayoutManager(new LinearLayoutManager(context));
    }

    private void findViews() {
        topicList = (RecyclerView) this.findViewById(R.id.topic_list);
    }

    class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder>{

        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    class TopicViewHolder extends RecyclerView.ViewHolder{
        private TextView topicName;
        private TextView topicArea;
        private ImageView topicImage;
        public TopicViewHolder(View itemView) {
            super(itemView);
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_jingxuan;
    }
}

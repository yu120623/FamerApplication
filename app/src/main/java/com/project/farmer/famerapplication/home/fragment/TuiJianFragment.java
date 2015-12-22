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
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.project.farmer.famerapplication.R;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/16.
 */
public class TuiJianFragment extends BaseFragment{
    private RecyclerView recommendList;
    private DisplayImageOptions options;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        recommendList.setLayoutManager(new LinearLayoutManager(context));
        recommendList.setAdapter(new RecommendAdapter());
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
//                .displayer(new CircleBitmapDisplayer())
                .imageScaleType(ImageScaleType.EXACTLY).build();
    }

    private void findViews() {
        recommendList = (RecyclerView) this.findViewById(R.id.recommend_list);

    }

    class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> {

        @Override
        public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(),R.layout.recommend_item,null);
            RecommendViewHolder holder = new RecommendViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecommendViewHolder holder, int position) {
            holder.recommendName.setText("农庄标题测试");
            holder.recommendArea.setText("苏州");
            holder.recommendReason.setText("环境不错可以钓鱼也可以吃饭，庄主人很好");
            ImageLoaderUtil.getInstance().displayImg(holder.recommendImg,"http://img.name2012.com/uploads/allimg/2015-06/30-023131_451.jpg",options);


        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView recommendName;
        private TextView recommendArea;
        private TextView recommendReason;
        private ImageView recommendImg;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            recommendName= (TextView) itemView.findViewById(R.id.recommend_name);
            recommendArea= (TextView) itemView.findViewById(R.id.recommend_area);
            recommendReason= (TextView) itemView.findViewById(R.id.recommend_reason);
            recommendImg= (ImageView) itemView.findViewById(R.id.recommend_img);

        }

    }

    public void onEvent(Integer index){
        if(index.intValue() == 3){
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

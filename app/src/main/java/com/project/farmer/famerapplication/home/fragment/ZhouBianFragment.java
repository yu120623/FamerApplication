package com.project.farmer.famerapplication.home.fragment;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.project.farmer.famerapplication.R;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ZhouBianFragment extends BaseFragment{
    private RecyclerView nearList;
    private DisplayImageOptions options;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        nearList.setLayoutManager(new LinearLayoutManager(context));
        nearList.setAdapter(new nearAdapter());
        nearList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        nearList = (RecyclerView) this.findViewById(R.id.near_list);
    }

    class nearAdapter extends RecyclerView.Adapter<nearViewHolder> {

        @Override
        public nearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(),R.layout.near_item,null);
            nearViewHolder holder = new nearViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(nearViewHolder holder, int position) {
            holder.nearName.setText("农庄标题测试");
            holder.nearArea.setText("苏州");
            holder.nearReason.setText("环境不错可以钓鱼也可以吃饭，庄主人很好");
            ImageLoaderUtil.getInstance().displayImg(holder.nearImg,"http://img.name2012.com/uploads/allimg/2015-06/30-023131_451.jpg",options);


        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class nearViewHolder extends RecyclerView.ViewHolder {
        private TextView nearName;
        private TextView nearArea;
        private TextView nearReason;
        private ImageView nearImg;

        public nearViewHolder(View itemView) {
            super(itemView);
            nearName= (TextView) itemView.findViewById(R.id.near_name);
            nearArea= (TextView) itemView.findViewById(R.id.near_area);
            nearReason= (TextView) itemView.findViewById(R.id.near_reason);
            nearImg= (ImageView) itemView.findViewById(R.id.near_img);

        }
    }

    public void onEvent(Integer index){
        if(index.intValue() == 2){
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(nearList));
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
        return R.layout.frag_zhoubian;
    }
}

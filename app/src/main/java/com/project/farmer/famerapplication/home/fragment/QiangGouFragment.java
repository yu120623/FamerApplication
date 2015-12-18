package com.project.farmer.famerapplication.home.fragment;

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

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/16.
 */
public class QiangGouFragment extends BaseFragment {
    private RecyclerView flashSelaList;
    private DisplayImageOptions options;
    private String[] url = {"http://s.mycff.com/images/direct/564d2ce239fc9.png",
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

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(1000))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        flashSelaList.setLayoutManager(new LinearLayoutManager(context));
        flashSelaList.setAdapter(new FlashSaleAdapter());
    }

    private void findViews() {
        flashSelaList = (RecyclerView) this.findViewById(R.id.flash_sale_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flashSelaList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
                Log.i("++++++++++++", "++++++++++++++113131321");
            }
        });
    }

    class FlashSaleAdapter extends RecyclerView.Adapter<TopicViewHolder> {

        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.flash_sale_item, null);
            TopicViewHolder holder = new TopicViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            holder.flashSaleName.setText("农庄标题");
            holder.flashSaleArea.setText("苏州");
            holder.flashSaleReason.setText("推荐理由或者简单介绍");
            ImageLoaderUtil.getInstance().displayImg(holder.flashSaleImage, url[position], options);
        }

        @Override
        public int getItemCount() {
            return url.length;
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

    @Override
    protected int getContentView() {
        return R.layout.frag_qianggou;
    }
}

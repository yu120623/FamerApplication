package com.project.farmer.famerapplication.details.fragment;

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

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by heoa on 2015/12/21.
 */
public class XuZhiFragment extends BaseFragment {
    private RecyclerView xuzhiList;
    private DisplayImageOptions options;
    private String[] url = {"http://oss.mycff.com/images/00001.png",
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
        xuzhiList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        xuzhiList.setLayoutManager(new LinearLayoutManager(context));
        xuzhiList.setAdapter(new XuZhiAdapter());
    }

    private void findViews() {
        xuzhiList = (RecyclerView) this.findViewById(R.id.xuzhi_list);
    }


    class XuZhiAdapter extends RecyclerView.Adapter<XuZhiViewHolder> {

        @Override
        public XuZhiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.xuzhi_item, null);
            XuZhiViewHolder holder = new XuZhiViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(XuZhiViewHolder holder, int position) {
            holder.xuzhiTitle.setText("变更公告");
            holder.xuzhiContext.setText("具体的内容，文字更多，更多的详情具体的内容，文字更多，更多的详情具体的内容，文字更多，更多的详情");
        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }

    class XuZhiViewHolder extends RecyclerView.ViewHolder {
        private TextView xuzhiTitle;
        private TextView xuzhiContext;

        public XuZhiViewHolder(View itemView) {
            super(itemView);
            xuzhiContext = (TextView) itemView.findViewById(R.id.xuzhi_context);
            xuzhiTitle = (TextView) itemView.findViewById(R.id.xuzhi_title);

        }
    }


    @Override
    protected int getContentView() {
        return R.layout.frag_xuzhi;
    }
}

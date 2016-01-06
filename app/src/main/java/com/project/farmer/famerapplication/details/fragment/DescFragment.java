package com.project.farmer.famerapplication.details.fragment;

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
import com.project.farmer.famerapplication.entity.FarmSetModel;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2015/12/22.
 */
public class DescFragment extends BaseFragment {
    private RecyclerView jieshaoList;
    private DisplayImageOptions options;
    private FarmSetModel farmSetModels;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jieshaoList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        jieshaoList.setLayoutManager(new LinearLayoutManager(context));
        jieshaoList.setAdapter(new JieShaoAdapter());
        farmSetModels = (FarmSetModel) this.getArguments().getSerializable("farmSet");
    }

    private void findViews() {
        jieshaoList = (RecyclerView) this.findViewById(R.id.jieshao_list);
    }

    class JieShaoAdapter extends RecyclerView.Adapter<JieShaoViewHolder> {
        @Override
        public JieShaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.jieshao_item, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT));
            JieShaoViewHolder holder = new JieShaoViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(JieShaoViewHolder holder, int position) {
            if (position == 0) {
                holder.jieshaoText.setVisibility(View.VISIBLE);
                holder.jieshaoText.setText(farmSetModels.getFarmSetDesc());
            } else {
                holder.jieshaoText.setVisibility(View.GONE);
                ImageLoaderUtil.getInstance().displayImg(holder.jieshaoImage, farmSetModels.getDeResourceModels().get(position-1).getResourceLocation(), options);
            }
        }

        @Override
        public int getItemCount() {
            return farmSetModels.getDeResourceModels().size() + 1;
        }

    }

    class JieShaoViewHolder extends RecyclerView.ViewHolder {
        private TextView jieshaoText;
        private ImageView jieshaoImage;

        public JieShaoViewHolder(View itemView) {
            super(itemView);
            jieshaoImage = (ImageView) itemView.findViewById(R.id.img_jieshao);
            jieshaoText = (TextView) itemView.findViewById(R.id.text_jieshao);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_jieshao;
    }
}

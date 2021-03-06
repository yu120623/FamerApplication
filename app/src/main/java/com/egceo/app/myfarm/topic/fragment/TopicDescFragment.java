package com.egceo.app.myfarm.topic.fragment;

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
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.util.AppUtil;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class TopicDescFragment extends BaseFragment {
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
        jieshaoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            View v = View.inflate(parent.getContext(), R.layout.item_jieshao, null);
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
                if(position == farmSetModels.getDeResourceModels().size()){
                    holder.itemView.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.button_min_height));
                }else{
                    holder.itemView.setPadding(0,0,0,0);
                }
                holder.jieshaoText.setVisibility(View.GONE);
                ImageLoaderUtil.getInstance().displayImg(holder.jieshaoImage, farmSetModels.getDeResourceModels().get(position-1).getResourceLocation()+ AppUtil.DETAIL_IMG_SIZE, options);
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

    public void onEvent(Integer index){
        if(index.intValue() == 0){
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(jieshaoList));
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
    public void onDestroy() {
        super.onDestroy();
       /* for(ResourseModel res : farmSetModels.getDeResourceModels()){
            Bitmap bmp = ImageLoader.getInstance().getMemoryCache().get();
            if(null != bmp && !bmp.isRecycled()){
                Log.i("++++onDestroy+++++++","onDestroy img");
                bmp.recycle();
                bmp = null;
            }
        }
        farmSetModels.getDeResourceModels().clear();
        jieshaoList.getAdapter().notifyDataSetChanged();*/
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_jieshao;
    }
}

package com.project.farmer.famerapplication.topic.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmItemsModel;
import com.project.farmer.famerapplication.entity.FarmSetModel;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class TimingDescFragment extends BaseFragment {
    private RecyclerView descList;
    private DisplayImageOptions options;
    private FarmSetModel farmSetModel;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private DescDapter descDapter;
    private LinearLayout header;
    private LinearLayout farmSetContent;
    private View currentItemView;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        descList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        farmSetModel = (FarmSetModel) this.getArguments().getSerializable("farmSet");
        initHeader();
        descDapter = new DescDapter();
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(descDapter);
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(header);
        descList.setLayoutManager(new LinearLayoutManager(context));
        descList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }

    private void initHeader() {
        header = (LinearLayout) inflater.inflate(R.layout.header_timing_topic_desc, null, false);
        farmSetContent = (LinearLayout) header.findViewById(R.id.farm_set_content);
        List<FarmItemsModel> farmItemsModels = farmSetModel.getFarmItemsModels();
        if (null == farmItemsModels || farmItemsModels.size() <= 0) return;
        for (int i = 0; i < farmItemsModels.size(); i++) {
            getFarmSetSubItem(farmItemsModels.get(i));
        }
        TextView farmDescTextView = (TextView) header.findViewById(R.id.farm_set_desc);
        farmDescTextView.setText(farmSetModel.getFarmSetDesc());
    }

    public View getFarmSetSubItem(FarmItemsModel farmItemsModel) {
        View item = ((LinearLayout) inflater.inflate(R.layout.item_sub_farmset, farmSetContent, true)).getChildAt(farmSetContent.getChildCount() - 1);
        TextView farmSetName = (TextView) item.findViewById(R.id.farmset_item_name);
        TextView farmSetDesc = (TextView) item.findViewById(R.id.farmset_item_desc);
        TextView farmSetPrice = (TextView) item.findViewById(R.id.farmset_item_price);
        TextView farmSetTag = (TextView) item.findViewById(R.id.farmset_item_tag);
        TextView farmSetDescList = (TextView) item.findViewById(R.id.farmset_item_desclist);
        ImageView farmSetImg = (ImageView) item.findViewById(R.id.farmset_item_img);
        farmSetName.setText(farmItemsModel.getFarmName());
        farmSetDesc.setText(farmItemsModel.getFarmItemName());
        farmSetPrice.setText(getString(R.string.gua_pai_price) + farmItemsModel.getPrice() + "");
        farmSetTag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        farmSetTag.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        farmSetDescList.setText(farmItemsModel.getFarmItemDesc());
        ImageLoaderUtil.getInstance().displayImg(farmSetImg, farmItemsModel.getResources().get(0).getResourceLocation());
        View setHeader = item.findViewById(R.id.farm_set_item_header);
        View setContent = item.findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
        return item;
    }

    public View.OnClickListener onFarmSetItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(currentItemView == v)return;
            View view = (View) v.getTag();
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                if(currentItemView != null)
                    currentItemView.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                currentItemView = view;
            }
        }
    };

    private void findViews() {
        descList = (RecyclerView) this.findViewById(R.id.jieshao_list);
    }

    class DescDapter extends RecyclerView.Adapter<JieShaoViewHolder> {
        @Override
        public JieShaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_jieshao, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            JieShaoViewHolder holder = new JieShaoViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(JieShaoViewHolder holder, int position) {
            holder.jieshaoText.setVisibility(View.GONE);
            ImageLoaderUtil.getInstance().displayImg(holder.jieshaoImage, farmSetModel.getDeResourceModels().get(position).getResourceLocation()+ AppUtil.DESC_IMG_SIZE, options);
        }

        @Override
        public int getItemCount() {
            return farmSetModel.getDeResourceModels().size();
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

    public void onEvent(Integer index) {
        if (index.intValue() == 0) {
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(descList));
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
        return R.layout.frag_timing_desc;
    }
}

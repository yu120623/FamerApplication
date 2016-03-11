package com.egceo.app.myfarm.farmset.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.home.activity.FarmSetNavListActivity;
import com.egceo.app.myfarm.view.PhotoViewPageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.order.actvity.OrderChooseDateActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;

import java.util.ArrayList;
import java.util.List;

public class FarmSetActivity extends BaseActivity {

    private RecyclerView farmSetList;
    private List<FarmSetModel> farmSetModels;
    private List<FarmItemsModel> farmItemsModels;
    private FarmAdapter adapter;
    private DisplayImageOptions options;
    private LinearLayout tmp;
    private int curPosition = -1;
    private String farmTopicAliasId;
    private String farmAliasId;
    private View currentItemView;
    private ImageView currentFaceImg;

    @Override
    protected void initViews() {
        showProgress();
        findViews();
        initDate();
    }

    private void findViews() {
        farmSetList = (RecyclerView) findViewById(R.id.farmset_list);
    }

    private void initDate() {
        farmTopicAliasId = this.getIntent().getStringExtra("farmTopicAliasId");
        farmAliasId = this.getIntent().getStringExtra("farmAliasId");
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
        farmSetList.setLayoutManager(new LinearLayoutManager(context));
        farmSetModels = new ArrayList<FarmSetModel>();
        farmItemsModels = new ArrayList<FarmItemsModel>();
        adapter = new FarmAdapter();
        farmSetList.setAdapter(adapter);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARMSET_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmTopicAliasId(farmTopicAliasId);
        data.setFarmAliasId(farmAliasId);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmSetModels = data.getFarmSetModels();
                if (farmSetModels != null && farmSetModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                    farmSetList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onEnd() {
                super.onEnd();
                hideProgress();
            }
        }, data);
        request.execute();
    }

    private void addTags(FarmSetViewHolder holder, FarmItemsModel farmItemsModel) {
        inflater.inflate(R.layout.item_sub_farmset, holder.linearLayout1, true);
        View item = holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1);
        TextView farmSetItemTag = (TextView) item.findViewById(R.id.farmset_item_tag);
        TextView farmSetItemTagBg = (TextView) item.findViewById(R.id.farmset_item_tag);
        TextView farmSetItemName = (TextView) item.findViewById(R.id.farmset_item_name);
        TextView farmSetItemDesc = (TextView) item.findViewById(R.id.farmset_item_desc);
        TextView farmSetItemPrice = (TextView) item.findViewById(R.id.farmset_item_price);
        ImageView farmSetItemImg = (ImageView) item.findViewById(R.id.farmset_item_img);
        TextView farmSetItemDescList = (TextView) item.findViewById(R.id.farmset_item_desclist);

        farmSetItemTag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        farmSetItemTagBg.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        farmSetItemName.setText(farmItemsModel.getFarmItemName());
        farmSetItemDesc.setText(farmItemsModel.getFarmName());
        farmSetItemPrice.setText(getString(R.string.gua_pai_price) + farmItemsModel.getPrice() + "");
        farmSetItemImg.setTag(farmItemsModel.getResources());
        ImageLoaderUtil.getInstance().displayImg(farmSetItemImg, farmItemsModel.getResources().get(0).getResourceLocation() + AppUtil.FARM_SET_DETAIL_IMG_SIZE);
        farmSetItemDescList.setText(Html.fromHtml(farmItemsModel.getFarmItemDesc()));
        View setHeader = item.findViewById(R.id.farm_set_item_header);
        View setContent = item.findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
        farmSetItemImg.setOnClickListener(onFarmSetImgClick);
    }

    public View.OnClickListener onFarmSetImgClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<Resource> resources = (List<Resource>) view.getTag();
            ArrayList<String> urls = new ArrayList<>();
            for (Resource res : resources) {
                urls.add(res.getResourceLocation());
            }
            Intent intent = new Intent(context, PhotoViewPageActivity.class);
            intent.putExtra("urls", urls);
            startActivity(intent);
        }
    };

    public View.OnClickListener onFarmSetItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentItemView == v) return;
            View view = (View) v.getTag();
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                if (currentItemView != null)
                    currentItemView.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                currentItemView = view;
            }
        }
    };

    class FarmAdapter extends RecyclerView.Adapter<FarmSetViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        @Override
        public FarmSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_farmset, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            FarmSetViewHolder holder = new FarmSetViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(final FarmSetViewHolder holder, int position) {
            FarmSetModel farmSetModel = farmSetModels.get(position);
            List<FarmItemsModel> farmItemsModels = farmSetModel.getFarmItemsModels();
            String imageUrl = farmSetModel.getThResource().get(0).getResourceLocation() + AppUtil.FARM_SET_FACE_IMG_SIZE;
            ImageLoaderUtil.getInstance().displayImg(holder.farmSetItemImage, imageUrl, options);
            holder.farmSetName.setText(farmSetModel.getFarmSetName());
            holder.farmSetMinPrice.setText(farmSetModel.getMinPrice() + getString(R.string.rmb));
            holder.farmSetConPrice.setText(getString(R.string.old_price) + farmSetModel.getConPrice() + getString(R.string.rmb));
            holder.farmSetConPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.farmSetDesc.setText(farmSetModel.getFarmSetDesc());
            holder.navBtn.setTag(farmSetModel);
            holder.orderBtn.setTag(farmSetModel);
            holder.linearLayout1.removeAllViews();
            for (int i = 0; i < farmItemsModels.size(); i++) {
                FarmItemsModel farmItemsModel = farmItemsModels.get(i);
                addTags(holder, farmItemsModel);
            }
            if (position == curPosition) {
                tmp = holder.linearLayout4;
                ((CheckBox) holder.linearLayout4.findViewById(R.id.checkbox1)).setChecked(true);
            } else {
                ((CheckBox) holder.linearLayout4.findViewById(R.id.checkbox1)).setChecked(false);
            }
            holder.linearLayout4.setTag(holder);
            holder.linearLayout4.setOnClickListener(this);
            holder.checkBox.setTag(holder);
            holder.checkBox.setOnCheckedChangeListener(this);
            holder.navBtn.setOnClickListener(onNavBtnClick);
            holder.orderBtn.setOnClickListener(onOrderBtnClick);
            holder.farmSetItemImage.setOnClickListener(this);
            holder.farmSetItemImage.setTag(holder);
        }


        @Override
        public int getItemCount() {
            return farmSetModels.size();
        }

        @Override
        public void onClick(View v) {
            FarmSetViewHolder farmSetViewHolder = (FarmSetViewHolder) v.getTag();
            curPosition = farmSetViewHolder.getAdapterPosition();
            if (tmp != farmSetViewHolder.linearLayout4 && tmp != null) {
                tmp.findViewById(R.id.btn_farmset).setVisibility(View.GONE);
                ((CheckBox) tmp.findViewById(R.id.checkbox1)).setChecked(false);
            }
            tmp = farmSetViewHolder.linearLayout4;
            if (farmSetViewHolder.checkBox.isChecked()) {
                farmSetViewHolder.checkBox.setChecked(false);
            } else {
                farmSetViewHolder.checkBox.setChecked(true);
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            FarmSetViewHolder farmSetViewHolder = (FarmSetViewHolder) buttonView.getTag();
            if (farmSetViewHolder.checkBox.isChecked()) {
                farmSetViewHolder.farmSetLine.setVisibility(View.VISIBLE);
                farmSetViewHolder.linearLayout1.setVisibility(View.VISIBLE);
                farmSetViewHolder.btnFarmset.setVisibility(View.VISIBLE);
            } else {
                farmSetViewHolder.farmSetLine.setVisibility(View.GONE);
                farmSetViewHolder.linearLayout1.setVisibility(View.GONE);
                farmSetViewHolder.btnFarmset.setVisibility(View.GONE);
            }
        }
    }

    private View.OnClickListener onOrderBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FarmSetModel farmSetModel = (FarmSetModel) view.getTag();
            Intent intent = new Intent(context, OrderChooseDateActivity.class);
            intent.putExtra("farmSetModel", farmSetModel);
            startActivity(intent);
        }
    };

    private View.OnClickListener onNavBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FarmSetModel farmSetModel = (FarmSetModel) view.getTag();
            Intent intent = new Intent(context, FarmSetNavListActivity.class);
            intent.putExtra("farmSetModel", farmSetModel);
            startActivity(intent);
        }
    };

    class FarmSetViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView farmSetLine;
        private LinearLayout btnFarmset;
        private LinearLayout linearLayout1;
        private LinearLayout linearLayout4;
        private TextView farmSetName;
        private TextView farmSetConPrice;
        private TextView farmSetDesc;
        private TextView farmSetMinPrice;
        private TextView navBtn;
        private TextView orderBtn;
        private ImageView farmSetItemImage;

        public FarmSetViewHolder(View itemView) {
            super(itemView);
            farmSetLine = (TextView) itemView.findViewById(R.id.farm_set_line);
            farmSetDesc = (TextView) itemView.findViewById(R.id.farmset_desc);
            farmSetName = (TextView) itemView.findViewById(R.id.farmset_name);
            farmSetMinPrice = (TextView) itemView.findViewById(R.id.farmset_min_price);
            farmSetConPrice = (TextView) itemView.findViewById(R.id.farmset_con_price);
            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.linearlayout1);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox1);
            btnFarmset = (LinearLayout) itemView.findViewById(R.id.btn_farmset);
            linearLayout4 = (LinearLayout) itemView.findViewById(R.id.lin4);
            navBtn = (TextView) itemView.findViewById(R.id.navigation_btn);
            orderBtn = (TextView) itemView.findViewById(R.id.order_btn);
            farmSetItemImage = (ImageView) itemView.findViewById(R.id.farm_set_item_img);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_farmset;
    }

    @Override
    protected String setActionBarTitle() {
        return getIntent().getStringExtra("title");
    }
}

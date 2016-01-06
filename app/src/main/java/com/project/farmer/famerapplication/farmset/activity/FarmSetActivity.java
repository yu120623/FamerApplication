package com.project.farmer.famerapplication.farmset.activity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmItemsModel;
import com.project.farmer.famerapplication.entity.FarmSet;
import com.project.farmer.famerapplication.entity.FarmSetModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import static com.project.farmer.famerapplication.R.drawable.zhu_bg;

/**
 * Created by gseoa on 2015/12/24.
 */
public class FarmSetActivity extends BaseActivity {

    private RecyclerView farmSetList;
    private List<FarmSetModel> farmSetModels;
    private List<FarmItemsModel> farmItemsModels;
    private FarmAdapter adapter;
    private DisplayImageOptions options;
    public boolean c = false;
    private LinearLayout tmp;
    private int curPosition = -1;

    @Override
    protected void initViews() {
        findViews();
        initDate();
    }

    private void findViews() {
        farmSetList = (RecyclerView) findViewById(R.id.farmset_list);
    }

    private void initDate() {

        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY).build();
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
        data.setFarmTopicAliasId("dasdsadasdasd");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmSetModels = data.getFarmSetModels();
                if (farmSetModels != null && farmSetModels.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
            }
        }, data);
        request.execute();
    }

    private void addTags(FarmSetViewHolder holder, FarmItemsModel farmItemsModel) {
        inflater.inflate(R.layout.item_sub_farmset, holder.linearLayout1, true);
        ((TextView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_tag)).setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        ((TextView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_tag)).setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        ((TextView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_name)).setText(farmItemsModel.getFarmName());
        ((TextView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_desc)).setText(farmItemsModel.getFarmItemName());
        ((TextView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_price)).setText(getString(R.string.gua_pai_price) + farmItemsModel.getPrice() + "");
        ImageLoaderUtil.getInstance().displayImg(((ImageView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_img)), farmItemsModel.getResources().get(0).getResourceLocation());
        ((TextView) holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farmset_item_desclist)).setText(Html.fromHtml(farmItemsModel.getFarmItemDesc()));
        View setHeader = holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farm_set_item_header);
        View setContent = holder.linearLayout1.getChildAt(holder.linearLayout1.getChildCount() - 1).findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
    }

    public View.OnClickListener onFarmSetItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = (View) v.getTag();
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
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
            holder.farmSetName.setText(farmSetModel.getFarmSetName());
            holder.farmSetMinPrice.setText(farmSetModel.getMinPrice() + "");
            holder.farmSetConPrice.setText("原价200元");
            holder.farmSetConPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.farmSetDesc.setText(farmSetModel.getFarmSetDesc());
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


            holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (holder.linearLayout1.findViewById(R.id.farmset_context_1).getVisibility() == View.VISIBLE)
//                        holder.linearLayout1.findViewById(R.id.farmset_context_1).setVisibility(View.GONE);
//                    else
//                        holder.linearLayout1.findViewById(R.id.farmset_context_1).setVisibility(View.VISIBLE);
                }
            });

//            ImageLoaderUtil.getInstance().displayImg((ImageView) holder.linearLayout.getChildAt(1).findViewById(R.id.img_farmset), "http://s.mycff.com/images/00055.png@!w200h100", options);
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
//                tmp.findViewById(R.id.linearlayout1).setVisibility(View.GONE);

                tmp.findViewById(R.id.btn_farmset).setVisibility(View.GONE);
                ((CheckBox) tmp.findViewById(R.id.checkbox1)).setChecked(false);
            }
            tmp = farmSetViewHolder.linearLayout4;
            if (farmSetViewHolder.checkBox.isChecked())
                farmSetViewHolder.checkBox.setChecked(false);
            else
                farmSetViewHolder.checkBox.setChecked(true);
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

        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_farmset;
    }

    @Override
    protected String setActionBarTitle() {
        return "某个玩的地方的套餐";
    }
}

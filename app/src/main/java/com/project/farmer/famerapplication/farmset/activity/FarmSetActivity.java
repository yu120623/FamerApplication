package com.project.farmer.famerapplication.farmset.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.project.farmer.famerapplication.R;

/**
 * Created by gseoa on 2015/12/24.
 */
public class FarmSetActivity extends BaseActivity {

    private RecyclerView farmSetList;
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
        adapter = new FarmAdapter();
        farmSetList.setAdapter(adapter);
    }

    private void addTags(FarmSetViewHolder holder) {
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                inflater.inflate(R.layout.item1_farmset, holder.linearLayout1, true);
                inflater.inflate(R.layout.item1_context_farmset, holder.linearLayout1, true);

//                holder.linearLayout1.getChildAt(0).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                      click(holder, 1);
//                    }
//                });
            }
//            else if (i == 1) {
//                inflater.inflate(R.layout.item2_farmset, holder.linearLayout, true);
//                holder.linearLayout1.getChildAt(2).setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//            } else {
//
//            }
        }
    }

    class FarmAdapter extends RecyclerView.Adapter<FarmSetViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        @Override
        public FarmSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_farmset, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            FarmSetViewHolder holder = new FarmSetViewHolder(v);
            addTags(holder);
            return holder;
        }


        @Override
        public void onBindViewHolder(final FarmSetViewHolder holder, int position) {
            if (position == curPosition) {
                tmp = holder.linearLayout4;
                ((CheckBox) holder.linearLayout4.findViewById(R.id.checkbox1)).setChecked(true);
                Log.i("11111111111111111111", "onClick: " + tmp.getChildCount());
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
                    if (holder.linearLayout1.findViewById(R.id.farmset_context_1).getVisibility() == View.VISIBLE)
                        holder.linearLayout1.findViewById(R.id.farmset_context_1).setVisibility(View.GONE);
                    else
                        holder.linearLayout1.findViewById(R.id.farmset_context_1).setVisibility(View.VISIBLE);
                }
            });

//            ImageLoaderUtil.getInstance().displayImg((ImageView) holder.linearLayout.getChildAt(1).findViewById(R.id.img_farmset), "http://s.mycff.com/images/00055.png@!w200h100", options);
        }

        @Override
        public int getItemCount() {
            return 20;
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
                farmSetViewHolder.linearLayout1.setVisibility(View.VISIBLE);
                farmSetViewHolder.btnFarmset.setVisibility(View.VISIBLE);
            } else {
                farmSetViewHolder.linearLayout1.setVisibility(View.GONE);
                farmSetViewHolder.btnFarmset.setVisibility(View.GONE);
            }
        }
    }

    class FarmSetViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private LinearLayout btnFarmset;
        private LinearLayout linearLayout1;
        private LinearLayout linearLayout4;

        public FarmSetViewHolder(View itemView) {
            super(itemView);
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

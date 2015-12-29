package com.project.farmer.famerapplication.farmset.activity;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    class FarmAdapter extends RecyclerView.Adapter<FarmSetViewHolder> {
        @Override
        public FarmSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_farmset, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
            FarmSetViewHolder holder = new FarmSetViewHolder(v);
            addTags(holder);

            return holder;
        }

        public boolean c = false;

        private void addTags(final FarmSetViewHolder holder) {
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    inflater.inflate(R.layout.item1_farmset, holder.linearLayout, true);
                    inflater.inflate(R.layout.item1_context_farmset, holder.linearLayout, true);

                    holder.linearLayout.getChildAt(0).setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            click(holder, 1);
                        }
                    });
                } else if (i == 1) {
                    inflater.inflate(R.layout.item2_farmset, holder.linearLayout, true);
                    holder.linearLayout.getChildAt(2).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {

                }

            }

        }

        private void click(FarmSetViewHolder holder, int index) {
            if (this.c) {
                holder.linearLayout.getChildAt(index).findViewById(R.id.farmset_context_1).setVisibility(View.GONE);
                this.c = false;
            } else {
                holder.linearLayout.getChildAt(index).findViewById(R.id.farmset_context_1).setVisibility(View.VISIBLE);
                this.c = true;
            }
        }


        @Override
        public void onBindViewHolder(final FarmSetViewHolder holder, int position) {
            ImageLoaderUtil.getInstance().displayImg((ImageView) holder.linearLayout.getChildAt(1).findViewById(R.id.img_farmset), "http://s.mycff.com/images/00055.png@!w200h100", options);
            holder.linearLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.checkBox.isChecked()) {
                        holder.checkBox.setChecked(false);
                    } else {
                        holder.checkBox.setChecked(true);
                    }
                }
            });

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        holder.linearLayout.setVisibility(View.VISIBLE);
                        holder.btnFarmset.setVisibility(View.VISIBLE);
                    } else {
                        holder.linearLayout.setVisibility(View.GONE);
                        holder.btnFarmset.setVisibility(View.GONE);
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return 10;
        }


    }

    class FarmSetViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private LinearLayout btnFarmset;
        private LinearLayout linearLayout;
        private LinearLayout linearLayout4;

        public FarmSetViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout1);
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

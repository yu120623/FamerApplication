package com.project.farmer.famerapplication.farmset.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.project.farmer.famerapplication.R;

/**
 * Created by gseoa on 2015/12/24.
 */
public class FarmSetActivity extends BaseActivity {

    private RecyclerView farmSetList;
    private FarmAdapter adapter;
    private View c;


    @Override
    protected void initViews() {
        findViews();
        initDate();
    }

    private void findViews() {
        farmSetList = (RecyclerView) findViewById(R.id.farmset_list);
    }

    private void initDate() {
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
                    holder.linearLayout.getChildAt(0).setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            click(c,holder,0);
                        }
                    });
                } else if (i == 1) {
                    inflater.inflate(R.layout.item2_farmset, holder.linearLayout, true);
                    holder.linearLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                        boolean c = false;

                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {

                }

            }
        }

        private void click(boolean c, FarmSetViewHolder holder, int index) {
            if (this.c) {
                holder.linearLayout.getChildAt(index).findViewById(R.id.farmset_zhu).setVisibility(View.VISIBLE);
                this.c = false;
            } else {
                holder.linearLayout.getChildAt(index).findViewById(R.id.farmset_zhu).setVisibility(View.GONE);
                this.c = true;
            }
        }


        @Override
        public void onBindViewHolder(final FarmSetViewHolder holder, int position) {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        holder.linearLayout.setVisibility(View.VISIBLE);
                    else
                        holder.linearLayout.setVisibility(View.GONE);
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
        private LinearLayout linearLayout;

        public FarmSetViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout1);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox1);

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

package com.project.farmer.famerapplication.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.project.farmer.famerapplication.R;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ZhouBianFragment extends BaseFragment{
    private RecyclerView nearList;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        nearList.setLayoutManager(new LinearLayoutManager(context));
        nearList.setAdapter(new nearAdapter());
    }

    private void findViews() {
        nearList = (RecyclerView) this.findViewById(R.id.near_list);
    }

    class nearAdapter extends RecyclerView.Adapter<nearViewHolder> {

        @Override
        public nearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(),R.layout.near_item,null);
            nearViewHolder holder = new nearViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(nearViewHolder holder, int position) {
            holder.nearName.setText("农庄标题BBB");
            holder.nearArea.setText("苏州");
            holder.nearReason.setText("农庄环境很好，庄主人很好");


        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class nearViewHolder extends RecyclerView.ViewHolder {
        private TextView nearName;
        private TextView nearArea;
        private TextView nearReason;

        public nearViewHolder(View itemView) {
            super(itemView);
            nearName= (TextView) itemView.findViewById(R.id.near_name);
            nearArea= (TextView) itemView.findViewById(R.id.near_area);
            nearReason= (TextView) itemView.findViewById(R.id.near_reason);

        }

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_zhoubian;
    }
}

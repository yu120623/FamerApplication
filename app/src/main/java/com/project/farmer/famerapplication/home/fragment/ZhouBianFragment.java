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
    private RecyclerView nearbyList;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        nearbyList.setLayoutManager(new LinearLayoutManager(context));
        nearbyList.setAdapter(new nearbyAdapter());
    }

    private void findViews() {
        nearbyList = (RecyclerView) this.findViewById(R.id.nearby_list);
    }

    class nearbyAdapter extends RecyclerView.Adapter<NearbyViewHolder> {

        @Override
        public NearbyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(),R.layout.nearby_item,null);
            NearbyViewHolder holder = new NearbyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(NearbyViewHolder holder, int position) {
            holder.nearbyName.setText("农庄标题BBB");
            holder.nearbyArea.setText("苏州");
            holder.nearbyReason.setText("农庄环境很好，庄主人很好");


        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class NearbyViewHolder extends RecyclerView.ViewHolder {
        private TextView nearbyName;
        private TextView nearbyArea;
        private TextView nearbyReason;

        public NearbyViewHolder(View itemView) {
            super(itemView);
            nearbyName= (TextView) itemView.findViewById(R.id.nearby_name);
            nearbyArea= (TextView) itemView.findViewById(R.id.nearby_area);
            nearbyReason= (TextView) itemView.findViewById(R.id.nearby_reason);

        }

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_zhoubian;
    }
}

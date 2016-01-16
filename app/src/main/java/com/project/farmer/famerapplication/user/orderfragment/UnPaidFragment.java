package com.project.farmer.famerapplication.user.orderfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.baseandroid.BaseFragment;
import com.project.farmer.famerapplication.R;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UnPaidFragment extends BaseFragment {
    private RecyclerView unPaidList;
    private UnPaidAdapter adapter;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        unPaidList = (RecyclerView) this.findViewById(R.id.unpaid_list);
    }

    private void initData() {
        adapter = new UnPaidAdapter();
        unPaidList.setLayoutManager(new LinearLayoutManager(context));
        unPaidList.setAdapter(adapter);

    }

    class UnPaidAdapter extends RecyclerView.Adapter<UnPaidViewHolder> {
        @Override
        public UnPaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_unpaid, null);
            UnPaidViewHolder holder = new UnPaidViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(UnPaidViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class UnPaidViewHolder extends RecyclerView.ViewHolder {
        public UnPaidViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_order_unpaid;
    }
}

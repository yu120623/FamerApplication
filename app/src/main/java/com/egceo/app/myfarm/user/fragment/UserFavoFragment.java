package com.egceo.app.myfarm.user.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.baseandroid.BaseFragment;
import com.egceo.app.myfarm.R;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UserFavoFragment extends BaseFragment {
    private RecyclerView favoList;
    private FavoAdapter adapter;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        favoList = (RecyclerView) this.findViewById(R.id.msg_list);
    }

    private void initData() {
        adapter = new FavoAdapter();
        favoList.setLayoutManager(new LinearLayoutManager(context));
        favoList.setAdapter(adapter);
    }

    class FavoAdapter extends RecyclerView.Adapter<FavoViewHolder> {
        @Override
        public FavoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_msg, null);
            FavoViewHolder holder = new FavoViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(FavoViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class FavoViewHolder extends RecyclerView.ViewHolder {

        public FavoViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_favo;
    }
}

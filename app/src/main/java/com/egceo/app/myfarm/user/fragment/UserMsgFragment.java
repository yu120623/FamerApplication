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
public class UserMsgFragment extends BaseFragment {
    private RecyclerView msgList;
    private MsgAdapter adapter;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        msgList = (RecyclerView) this.findViewById(R.id.msg_list);
    }

    private void initData() {
        adapter=new MsgAdapter();
        msgList.setLayoutManager(new LinearLayoutManager(context));
        msgList.setAdapter(adapter);
    }

    class MsgAdapter extends RecyclerView.Adapter<MsgViewHolder> {
        @Override
        public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_msg, null);
            MsgViewHolder holder = new MsgViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MsgViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {

        public MsgViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_user_msg;
    }
}

package com.project.farmer.famerapplication.comment.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.CommonUtil;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.CommentModel;
import com.project.farmer.famerapplication.entity.TransferObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public abstract class BaseCommentFragment extends BaseFragment {
    public RecyclerView pingjiaList;
    public List<CommentModel> comments;
    public PingJiaAdapter adapter;
    public SimpleDateFormat dataFormat;
    public TransferObject resData;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pingjiaList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
            }
        });
    }

    protected void initData() {
        pingjiaList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new PingJiaAdapter();
        pingjiaList.setAdapter(adapter);
        dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        comments = new ArrayList<CommentModel>();
        loadDataFromServer();
    }

    protected abstract void loadDataFromServer();

    private void findViews() {
        pingjiaList = (RecyclerView) this.findViewById(R.id.pingjia_list);
    }

    class PingJiaAdapter extends RecyclerView.Adapter<PingJiaViewHolder> {

        @Override
        public PingJiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_pingjia, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT));
            PingJiaViewHolder holder = new PingJiaViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PingJiaViewHolder holder, int position) {
            if (position == 0) {
                holder.commentHead.setVisibility(View.VISIBLE);
                holder.commentBody.setVisibility(View.GONE);
                holder.totalComment.setText("共有"+resData.getCommentCount()+"人评价");
                holder.highPraiseRate.setText("好评率："+resData.getFavorableRate());
                holder.score.setText(resData.getScore());
            } else {
                if(position == comments.size()){
                    holder.itemView.setPadding(0,0,0, (int) getResources().getDimension(R.dimen.button_min_height));
                }else{
                    holder.itemView.setPadding(0,0,0,0);
                }
                holder.commentHead.setVisibility(View.GONE);
                holder.commentBody.setVisibility(View.VISIBLE);
                holder.commentUserName.setText(comments.get(position-1).getCommentName());
                holder.commentDate.setText(dataFormat.format(comments.get(position-1).getCreatedTime()));
                holder.commentContext.setText(comments.get(position-1).getComment().getCommentContent());
            }
        }

        @Override
        public int getItemCount() {
            if(comments.size() <= 0)return 0;
            return comments.size()+1;
        }

    }

    class PingJiaViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout commentBody;
        private LinearLayout commentHead;
        private TextView totalComment;
        private TextView highPraiseRate;
        private TextView score;
        private TextView commentUserName;
        private TextView commentDate;
        private TextView commentContext;

        public PingJiaViewHolder(View itemView) {
            super(itemView);
            commentHead = (LinearLayout) itemView.findViewById(R.id.comment_head);
            totalComment = (TextView) itemView.findViewById(R.id.total_comment);
            highPraiseRate = (TextView) itemView.findViewById(R.id.high_praise_rate);
            score = (TextView) itemView.findViewById(R.id.score);
            commentUserName = (TextView) itemView.findViewById(R.id.comment_user_name);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentContext = (TextView) itemView.findViewById(R.id.comment_context);
            commentBody = (LinearLayout) itemView.findViewById(R.id.comment_body);

        }
    }

    public void refreshComment(){
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_pingjia;
    }
}

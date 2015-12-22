package com.project.farmer.famerapplication.details.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.project.farmer.famerapplication.R;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by heoa on 2015/12/21.
 */
public class PingJiaFragment extends BaseFragment {
    private RecyclerView pingjiaList;

    @Override
    protected void initViews() {
        findViews();
        initData();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pingjiaList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void initData() {

        pingjiaList.setLayoutManager(new LinearLayoutManager(context));
        pingjiaList.setAdapter(new PingJiaAdapter());
    }

    private void findViews() {
        pingjiaList = (RecyclerView) this.findViewById(R.id.pingjia_list);
    }

    class PingJiaAdapter extends RecyclerView.Adapter<PingJiaViewHolder> {

        @Override
        public PingJiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.pingjia_item, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT));
            PingJiaViewHolder holder = new PingJiaViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PingJiaViewHolder holder, int position) {
            if (position == 0) {
                holder.commentHead.setVisibility(View.VISIBLE);
                holder.commentBody.setVisibility(View.GONE);
                holder.totalComment.setText("共有80人评价");
                holder.highPraiseRate.setText("好评率：80%");
                holder.score.setText("4.8");
            } else {
                holder.commentHead.setVisibility(View.GONE);
                holder.commentBody.setVisibility(View.VISIBLE);
                holder.commentUserName.setText("asd103945");
                holder.commentDate.setText("2015/12/14 15:55:23");
                holder.commentContext.setText("不错很好玩不错很好玩不错很好玩不错很好玩不错很好玩");
            }
        }

        @Override
        public int getItemCount() {
            return 10;
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

    @Override
    protected int getContentView() {
        return R.layout.frag_pingjia;
    }
}

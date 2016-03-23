package com.egceo.app.myfarm.comment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.CommentModel;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.PhotoViewPageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

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
    protected LoadMoreFooter loadMoreFooter;
    protected Integer pageNumber = 0;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private DisplayImageOptions options;

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
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        comments = new ArrayList<>();
        pingjiaList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new PingJiaAdapter();
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        pingjiaList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(pingjiaList,loadMoreFooter.getFooter());
        pingjiaList.addOnScrollListener(loadMoreListener);
        dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        loadDataFromServer();
    }

    //加载更多监听
    protected EndlessRecyclerOnScrollListener loadMoreListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            if(loadMoreFooter.isLoading())return;
            pageNumber++;
            loadMoreFooter.showLoadingTips();
            loadDataFromServer();
        }
    };

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
                holder.ratingBar.setRating(Float.parseFloat(resData.getScore()));
                holder.score.setText(resData.getScore());
                holder.photoGrid.setVisibility(View.GONE);
            }else{
                holder.commentHead.setVisibility(View.GONE);
                holder.commentBody.setVisibility(View.VISIBLE);
                holder.commentUserName.setText(comments.get(position-1).getCommentName());
                holder.commentDate.setText(dataFormat.format(comments.get(position-1).getCreatedTime()));
                holder.commentContext.setText(comments.get(position-1).getComment().getCommentContent());
                if(comments.get(position-1).getResoursePath() != null && comments.get(position-1).getResoursePath().size() > 0) {
                    holder.photoGrid.setVisibility(View.VISIBLE);
                    holder.photoGrid.setTag(comments.get(position - 1).getResoursePath());
                    holder.photoGrid.setAdapter(new PhotoAdapter(comments.get(position - 1).getResoursePath()));
                    holder.photoGrid.setOnItemClickListener(onItemClickListener);
                }else{
                    holder.photoGrid.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount() {
            if(comments.size() <= 0)return 0;
            return comments.size()+1;
        }

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            List<Resource> resources = (List<Resource>) parent.getTag();
            ArrayList<String> url = new ArrayList<>();
            for(Resource resource : resources){
                url.add(resource.getResourceLocation());
            }
            Intent intent = new Intent(context, PhotoViewPageActivity.class);
            intent.putExtra("urls",url);
            intent.putExtra("index",position);
            startActivity(intent);
        }
    };

    class PingJiaViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout commentBody;
        private RelativeLayout commentHead;
        private TextView totalComment;
        private TextView score;
        private TextView commentUserName;
        private TextView commentDate;
        private TextView commentContext;
        private RatingBar ratingBar;
        private GridView photoGrid;
        public PingJiaViewHolder(View itemView) {
            super(itemView);
            commentHead = (RelativeLayout) itemView.findViewById(R.id.comment_head);
            totalComment = (TextView) itemView.findViewById(R.id.total_comment);
            score = (TextView) itemView.findViewById(R.id.score);
            commentUserName = (TextView) itemView.findViewById(R.id.comment_user_name);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentContext = (TextView) itemView.findViewById(R.id.comment_context);
            commentBody = (LinearLayout) itemView.findViewById(R.id.comment_body);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            photoGrid = (GridView) itemView.findViewById(R.id.photo_grid);
        }
    }

    class PhotoAdapter extends BaseAdapter {
        private List<Resource> photos;
        public PhotoAdapter(List<Resource> photos){
            this.photos = photos;
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gf_adapter_photo_list_item, parent,false);
                viewHolder = new ViewHolder();
                viewHolder.mIvThumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
                viewHolder.mIvCheck = (ImageView) convertView.findViewById(R.id.iv_check);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mIvCheck.setVisibility(View.GONE);
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(photos.get(position).getResourceLocation()+ AppUtil.FARM_FACE, new ImageViewAware(viewHolder.mIvThumb), options);
            return convertView;
        }
    }

    class ViewHolder {
        public ImageView mIvThumb;
        public ImageView mIvCheck;
    }

    public void refreshComment(){
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_pingjia;
    }
}

package com.project.farmer.famerapplication.loadmore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.farmer.famerapplication.R;

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private View header;
    private View loadMoreFooter;//加载更多layout
    private View noMoreTips;//暂无更多提示
    private View loadingMoreTips;//正在加载更多提示
    private boolean isLoadingMore = false;//是否在加载更多

    public static int FLAG_HEADER_VIEW = 0;
    public static int FLAG_FOOTER_VIEW = 1;
    public static int FLAG_ITEM_VIEW = 2;

    private Context context;
    private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        initLoadMoreLayout();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FLAG_HEADER_VIEW)
            return new RecyclerViewHolder(header);
        else if(viewType == FLAG_FOOTER_VIEW){
            return new RecyclerViewHolder(loadMoreFooter);
        }
        else if(viewType == FLAG_ITEM_VIEW){
            return onCreateItemViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == FLAG_HEADER_VIEW){
            onBindHeaderViewHolder(holder,position);
        }
        else if(viewType == FLAG_FOOTER_VIEW){

        }
        else if(viewType == FLAG_ITEM_VIEW){
            onBindItemViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        if(this.header != null) {
            if(getCount() > 0)
                return getCount() + 2;
            else
                return getCount() + 1;
        }else{
            if(getCount() > 0)
                return getCount() + 1;
        }
        return getCount();
    }


    public abstract int getCount();

    public abstract RecyclerViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    public abstract void findViews(View itemView);

    public abstract void onBindItemViewHolder(RecyclerViewHolder holder, int position);

    public abstract void onBindHeaderViewHolder(RecyclerViewHolder holder, int position);

    public void initLoadMoreLayout(){//初始化加载更多
        loadMoreFooter = inflater.inflate(R.layout.load_more_layout,null,false);
        noMoreTips = loadMoreFooter.findViewById(R.id.no_more_tips);
        loadingMoreTips = loadMoreFooter.findViewById(R.id.loading_more_tips);
    }

    public void showTips(boolean flag){
        if(flag) {
            noMoreTips.setVisibility(View.VISIBLE);
            loadingMoreTips.setVisibility(View.INVISIBLE);
        }else{
            noMoreTips.setVisibility(View.INVISIBLE);
            loadingMoreTips.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            if(this.header == null)
                return FLAG_ITEM_VIEW;
            else
                return FLAG_HEADER_VIEW;
        }else if(position == getItemCount()){
            return FLAG_FOOTER_VIEW;
        }
        return FLAG_ITEM_VIEW;
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
        }
    }

    public void setHeader(View header) {
        this.header = header;
    }
}

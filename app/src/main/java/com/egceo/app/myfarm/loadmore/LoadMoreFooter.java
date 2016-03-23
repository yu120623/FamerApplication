package com.egceo.app.myfarm.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.egceo.app.myfarm.R;

public class LoadMoreFooter {
    private Context context;
    private LayoutInflater inflater;
    private View loadMoreFooter;//加载更多layout
    private TextView noMoreTips;//暂无更多提示
    private View loadingMoreTips;//正在加载更多提示
    private TextView loadingMoreTipsText;
    private boolean isLoading = false;//是否正在加载更多
    private boolean isNoMore = false;//是否没有更多内容了
    public LoadMoreFooter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        loadMoreFooter = inflater.inflate(R.layout.layout_load_more,null,false);
        noMoreTips = (TextView)loadMoreFooter.findViewById(R.id.no_more_tips);
        loadingMoreTips = loadMoreFooter.findViewById(R.id.loading_more_tips);
        loadingMoreTipsText = (TextView) loadMoreFooter.findViewById(R.id.loading_more_tips_text);
    }

    public void showLoadingTips(){
        loadMoreFooter.setVisibility(View.VISIBLE);
        noMoreTips.setVisibility(View.INVISIBLE);
        loadingMoreTips.setVisibility(View.VISIBLE);
    }

    public void showNoMoreTips(){
        isNoMore = true;
        loadMoreFooter.setVisibility(View.VISIBLE);
        loadingMoreTips.setVisibility(View.INVISIBLE);
        noMoreTips.setVisibility(View.VISIBLE);
    }


    public void hideLoadMore(){
        if(isNoMore)return;
        loadMoreFooter.setVisibility(View.VISIBLE);
        loadingMoreTips.setVisibility(View.INVISIBLE);
        noMoreTips.setVisibility(View.INVISIBLE);
    }

    public void setLoadingMoreTipsText(String text){
        loadingMoreTipsText.setText(text);
    }

    public void setNoMoreTipsText(String text){
        noMoreTips.setText(text);
    }

    public void setIsLoading(boolean flag){
        isLoading = flag;
    }

    public void setIsNoMore(boolean flag){
        isNoMore = flag;
    }

    public boolean isNoMore(){
        return isNoMore;
    }

    public void reset(){
        isLoading = false;
        isNoMore = false;
        hideLoadMore();
    }

    public boolean isLoading(){
        return isLoading;
    }

    public View getFooter() {
        return loadMoreFooter;
    }
}

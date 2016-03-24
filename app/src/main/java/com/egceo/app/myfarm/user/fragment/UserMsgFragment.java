package com.egceo.app.myfarm.user.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.baseandroid.BaseFragment;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.Sysinfo;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.CustomUIHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by gseoa on 2016/1/14.
 */
public class UserMsgFragment extends BaseFragment {
    private RecyclerView msgList;
    private MsgAdapter adapter;
    private List<Sysinfo> sysinfos = new ArrayList<>();
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private PtrFrameLayout frameLayout;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private SimpleDateFormat dateFormat;
    private TextView msgNum;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void findViews() {
        msgList = (RecyclerView) this.findViewById(R.id.msg_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
        msgNum = (TextView) this.findViewById(R.id.user_msg_num);
    }

    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        adapter=new MsgAdapter();
        msgList.setLayoutManager(new LinearLayoutManager(context));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        msgList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(msgList,loadMoreFooter.getFooter());
        msgList.addOnScrollListener(loadMoreListener);
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNumber = 0;
                msgList.removeOnScrollListener(loadMoreListener);
                msgList.addOnScrollListener(loadMoreListener);
                loadMoreFooter.reset();
                loadDataFromServer();
            }
        });
        frameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLayout.autoRefresh(true);
            }
        },100);
    }

    private void loadDataFromServer() {
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.SYS_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                msgNum.setText(data.getTotalNum());
                List<Sysinfo> list = data.getSysinfos();
                if(pageNumber == 0){
                    if(list == null)
                        list = new ArrayList<>();
                    sysinfos = list;
                }else{
                    if(list.size() > 0){
                        sysinfos.addAll(list);
                    }else{
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        msgList.removeOnScrollListener(loadMoreListener);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onEnd() {
                frameLayout.refreshComplete();
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
            }
        },data);
        request.execute();
    }

    //加载更多监听
    private EndlessRecyclerOnScrollListener loadMoreListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            if(loadMoreFooter.isLoading())return;
            pageNumber++;
            loadMoreFooter.showLoadingTips();
            loadDataFromServer();
        }
    };

    class MsgAdapter extends RecyclerView.Adapter<MsgViewHolder> {
        @Override
        public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_msg, null);
            v.setOnLongClickListener(onDelMsgClick);
            MsgViewHolder holder = new MsgViewHolder(v);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(layoutParams);
            return holder;
        }

        @Override
        public void onBindViewHolder(MsgViewHolder holder, int position) {
            Sysinfo sysInfo = sysinfos.get(position);
            holder.itemView.setTag(sysInfo);
            holder.msgTime.setText(dateFormat.format(sysInfo.getCreatedTime()));
            holder.msgContent.setText(sysInfo.getSysinfoContent());
        }

        @Override
        public int getItemCount() {
            return sysinfos.size();
        }
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView msgTime;
        private TextView msgContent;
        public MsgViewHolder(View itemView) {
            super(itemView);
            msgContent = (TextView) itemView.findViewById(R.id.msg_content);
            msgTime = (TextView) itemView.findViewById(R.id.msg_time);
        }
    }

    private View.OnLongClickListener onDelMsgClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final Sysinfo sysInfo = (Sysinfo) v.getTag();
            new AlertDialog.Builder(activity)
                    .setItems(new String[]{getString(R.string.del)}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = API.URL + API.API_URL.DEL_MSG;
                            TransferObject data = AppUtil.getHttpData(context);
                            data.setSysinfoId(sysInfo.getSysinfoId()+"");
                            AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
                                @Override
                                public void onSuccess(TransferObject data) {
                                    sysinfos.remove(sysInfo);
                                    adapter.notifyDataSetChanged();
                                }
                            },data);
                            request.execute();
                            dialog.dismiss();
                        }
                    }).show();
            return false;
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.frag_user_msg;
    }
}

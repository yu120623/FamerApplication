package com.project.farmer.famerapplication.details.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmStatement;
import com.project.farmer.famerapplication.entity.FarmTopicModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;

public class XuZhiFragment extends BaseFragment {
    private RecyclerView xuzhiList;
    private DisplayImageOptions options;
    private FarmTopicModel farmTopicModel;
    private XuZhiAdapter adapter;
    private List<FarmStatement> stats;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xuzhiList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.default_img)
                .showImageOnFail(R.mipmap.default_img)
                .showImageOnLoading(R.mipmap.default_img)
                .displayer(new FadeInBitmapDisplayer(1000))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        xuzhiList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new XuZhiAdapter();
        xuzhiList.setAdapter(adapter);
        farmTopicModel = (FarmTopicModel) this.getArguments().getSerializable("farmTopic");
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.FARM_TOPIC_KONW;
        TransferObject data = AppUtil.getHttpData(context);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                stats = data.getFarmStatements();
                if(stats != null && stats.size() > 0){
                    adapter.notifyDataSetChanged();;
                }
            }
        },data);
        request.execute();
    }

    private void findViews() {
        xuzhiList = (RecyclerView) this.findViewById(R.id.xuzhi_list);
    }


    class XuZhiAdapter extends RecyclerView.Adapter<XuZhiViewHolder> {

        @Override
        public XuZhiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.xuzhi_item, null);
            XuZhiViewHolder holder = new XuZhiViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(XuZhiViewHolder holder, int position) {
            holder.xuzhiTitle.setText(stats.get(position).getFarmStatementDesc());
            holder.xuzhiContext.setText(stats.get(position).getFarmStatementDesc());
        }

        @Override
        public int getItemCount() {
            return stats.size();
        }

    }

    class XuZhiViewHolder extends RecyclerView.ViewHolder {
        private TextView xuzhiTitle;
        private TextView xuzhiContext;

        public XuZhiViewHolder(View itemView) {
            super(itemView);
            xuzhiContext = (TextView) itemView.findViewById(R.id.xuzhi_context);
            xuzhiTitle = (TextView) itemView.findViewById(R.id.xuzhi_title);

        }
    }


    @Override
    protected int getContentView() {
        return R.layout.frag_xuzhi;
    }
}

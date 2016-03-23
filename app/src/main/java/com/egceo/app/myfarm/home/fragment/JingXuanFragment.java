package com.egceo.app.myfarm.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseandroid.BaseFragment;
import com.baseandroid.util.ImageLoaderUtil;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.egceo.app.myfarm.db.CodeDao;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.entity.Code;
import com.egceo.app.myfarm.entity.CodeModel;
import com.egceo.app.myfarm.home.activity.MainActivityNew;
import com.egceo.app.myfarm.view.CustomUIHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.topic.activity.TopicDetailsActivity;
import com.egceo.app.myfarm.entity.FarmTopicModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.loadmore.LoadMoreFooter;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.AttachUtil;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class JingXuanFragment extends BaseFragment {
    private RecyclerView topicList;
    private DisplayImageOptions options;
    private TopicAdapter adapter;
    private List<FarmTopicModel> farmTopicModels = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    private LoadMoreFooter loadMoreFooter;
    private Integer pageNumber = 0;
    private PtrFrameLayout frameLayout;
    private RecyclerView cityList;
    private CodeDao codeDao;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }
    private void initClick() {
        topicList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(recyclerView));
            }
        });
        topicList.addOnScrollListener(loadMoreListener);
    }


    private void initData() {
        codeDao = DBHelper.getDaoSession(context).getCodeDao();
        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.topic_img)
                .showImageOnFail(R.mipmap.topic_img)
                .showImageOnLoading(R.mipmap.topic_img)
                .displayer(new FadeInBitmapDisplayer(1000))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        topicList.setLayoutManager(new LinearLayoutManager(context));
        cityList.setLayoutManager(new GridLayoutManager(context,2));
        adapter = new TopicAdapter();
        cityList.setAdapter(new CityAdapter(new ArrayList<CodeModel>()));
        loadMoreFooter = new LoadMoreFooter(context);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        topicList.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(topicList,loadMoreFooter.getFooter());
        CustomUIHandler header = new CustomUIHandler(context);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setHeaderView(header);
        frameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(loadMoreFooter.isLoading())
                    return;
                pageNumber = 0;
                topicList.removeOnScrollListener(loadMoreListener);
                topicList.addOnScrollListener(loadMoreListener);
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
        if(loadMoreFooter.isLoading())
            return;
        loadMoreFooter.setIsLoading(true);
        String url = API.URL + API.API_URL.FARM_TOPIC_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setPageNumber(pageNumber);
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                List<FarmTopicModel> list = data.getFarmTopicModels();
                if (list != null && list.size() > 0) {
                    if(pageNumber == 0) {
                        farmTopicModels = list;
                    }else{
                        farmTopicModels.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    if(pageNumber > 0) {
                        pageNumber--;
                        loadMoreFooter.showNoMoreTips();
                        topicList.removeOnScrollListener(loadMoreListener);
                    }else{
                        showCity(data);
                    }
                }
            }
            @Override
            public void onEnd() {
                loadMoreFooter.setIsLoading(false);
                loadMoreFooter.hideLoadMore();
                frameLayout.refreshComplete();
            }
        },data);
        request.execute();
    }

    private void showCity(TransferObject data) {
        cityList.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        if(data.getCodeModels() == null)
            data.setCodeModels(new ArrayList<CodeModel>());
        cityList.setAdapter(new CityAdapter(data.getCodeModels()));
    }

    private void findViews() {
        topicList = (RecyclerView) this.findViewById(R.id.topic_list);
        frameLayout = (PtrFrameLayout) this.findViewById(R.id.ptr);
        cityList = (RecyclerView) this.findViewById(R.id.city_list);
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

    class CityAdapter extends RecyclerView.Adapter<CityViewHolder>{

        private List<CodeModel> codeModels;

        public CityAdapter(List<CodeModel> codeModels){
            this.codeModels = codeModels;
        }

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_home_city,null,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CodeModel city = (CodeModel) v.getTag();
                    Intent intent = new Intent(context, MainActivityNew.class);
                    Code code = new Code();
                    code.setCodeId(1l);
                    code.setCodeDesc(city.getCityName());
                    code.setCodeName(city.getCodeName());
                    code.setCodetype(AppUtil.CODE_TYPE_HANDLE);
                    codeDao.insertOrReplace(code);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("city",code);
                    startActivity(intent);
                }
            });
            CityViewHolder cityViewHolder = new CityViewHolder(view);
            return cityViewHolder;
        }

        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            CodeModel codeModel = codeModels.get(position);
            holder.itemView.setTag(codeModel);
            ImageLoaderUtil.getInstance().displayImg(holder.cityImg,codeModel.getResourcePath(),options);
            holder.cityDesc.setText(codeModel.getCodeContent());
            holder.cityName.setText(codeModel.getCityName());
        }

        @Override
        public int getItemCount() {
            return codeModels.size();
        }
    }


    class CityViewHolder extends RecyclerView.ViewHolder{
        private TextView cityName;
        private TextView cityDesc;
        private ImageView cityImg;
        public CityViewHolder(View itemView) {
            super(itemView);
            cityDesc = (TextView) itemView.findViewById(R.id.city_desc);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            cityImg = (ImageView) itemView.findViewById(R.id.city_img);
        }
    }
    class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder> implements View.OnClickListener {
        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_topic, null);
            TopicViewHolder holder = new TopicViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            FarmTopicModel farmTopicModel = farmTopicModels.get(position);
            holder.topicName.setText("『"+farmTopicModel.getFarmTopicName()+"』");
            holder.topicArea.setText(farmTopicModel.getTagName());
            holder.topicReason.setText(farmTopicModel.getFarmTopicRecomReason());
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
            holder.topicMoney.setText(farmTopicModel.getFarmsetMinPrice()+context.getString(R.string.up));
            ImageLoaderUtil.getInstance().displayImg(holder.topicImage,farmTopicModel.getResourcePath()+AppUtil.TOPIC_IMG_SIZE, options);
        }

        @Override
        public int getItemCount() {
            return farmTopicModels.size();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            int position = (int) v.getTag();
            FarmTopicModel farmTopicModel = farmTopicModels.get(position);
            intent.putExtra("farmTopic",farmTopicModel);
            intent.setClass(getActivity(), TopicDetailsActivity.class);
            startActivity(intent);
        }
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topicName;
        private TextView topicArea;
        private TextView topicReason;
        private ImageView topicImage;
        private TextView topicMoney;

        public TopicViewHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topic_name);
            topicArea = (TextView) itemView.findViewById(R.id.topic_area);
            topicReason = (TextView) itemView.findViewById(R.id.topic_reason);
            topicImage = (ImageView) itemView.findViewById(R.id.topic_img);
            topicMoney = (TextView) itemView.findViewById(R.id.topic_money);
        }
    }

    public void onEvent(Integer index){
        if(index.intValue() == 0){
            EventBus.getDefault().post(AttachUtil.isRecyclerViewAttach(topicList));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_jingxuan;
    }
}

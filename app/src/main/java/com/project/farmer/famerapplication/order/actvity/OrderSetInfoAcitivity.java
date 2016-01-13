package com.project.farmer.famerapplication.order.actvity;

import android.graphics.drawable.AnimationDrawable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmItemsModel;
import com.project.farmer.famerapplication.entity.FarmSetModel;
import com.project.farmer.famerapplication.entity.OrderDateModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.order.view.OrderProcessHeader;
import com.project.farmer.famerapplication.util.AppUtil;
import com.samsistemas.calendarview.widget.CalendarView;

import java.text.SimpleDateFormat;

public class OrderSetInfoAcitivity extends BaseActivity{
    private FarmSetModel farmSetModel;
    private OrderDateModel orderDataModel;
    private OrderProcessHeader orderHeader;
    private TextView farmSetNumber;
    private ImageView addBtn;
    private ImageView jianBtn;
    private Integer num = 1;
    private ImageView progress;
    private AnimationDrawable progressDrawable;
    private View cotentView;
    private LinearLayout farmSetItemContent;
    private SimpleDateFormat dateFormat;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void checkNum() {
        if(num <= 1){
            jianBtn.setImageResource(R.mipmap.jian);
            jianBtn.setOnClickListener(null);
            addBtn.setImageResource(R.mipmap.add_s);
            addBtn.setOnClickListener(onAddClick);
        }else{
            if(num >= 5){
                addBtn.setImageResource(R.mipmap.add);
                addBtn.setOnClickListener(null);
            }else {
                addBtn.setImageResource(R.mipmap.add_s);
                addBtn.setOnClickListener(onAddClick);
            }
            jianBtn.setImageResource(R.mipmap.jian_s);
            jianBtn.setOnClickListener(onJianClick);
        }
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_CHOOSE_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmSetAliasId(farmSetModel.getFarmSetAliasId());
        data.setDate(orderDataModel.getDate());
        data.setUserAliasId("aaa");
        AppRequest requset = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                farmSetModel = data.getFarmSetModel();
                if(farmSetModel != null){
                    showFarmSet();
                }
            }
        },data);
        requset.execute();
    }

    private void showFarmSet() {
        cotentView.setVisibility(View.VISIBLE);
        progressDrawable.stop();
        progress.setVisibility(View.GONE);
        for(int i = 0;i < farmSetModel.getFarmItemsModels().size();i++){
            getFarmSetSubItem(farmSetModel.getFarmItemsModels().get(i));
        }
    }

    public View getFarmSetSubItem(FarmItemsModel farmItemsModel){
        View item = ((LinearLayout)inflater.inflate(R.layout.item_sub_farmset,farmSetItemContent,true)).getChildAt(farmSetItemContent.getChildCount()-1);
        TextView farmSetName = (TextView) item.findViewById(R.id.farmset_item_name);
        TextView farmSetDesc = (TextView) item.findViewById(R.id.farmset_item_desc);
        TextView farmSetPrice = (TextView) item.findViewById(R.id.farmset_item_price);
        TextView farmSetTag = (TextView) item.findViewById(R.id.farmset_item_tag);
        TextView farmSetDescList = (TextView) item.findViewById(R.id.farmset_item_desclist);
        ImageView farmSetImg = (ImageView) item.findViewById(R.id.farmset_item_img);
        farmSetName.setText(farmItemsModel.getFarmName());
        farmSetDesc.setText(farmItemsModel.getFarmItemName());
        farmSetPrice.setText(getString(R.string.gua_pai_price)+farmItemsModel.getPrice()+"");
        farmSetTag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        farmSetTag.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        farmSetDescList.setText(Html.fromHtml(farmItemsModel.getFarmItemDesc()));
        ImageLoaderUtil.getInstance().displayImg(farmSetImg,farmItemsModel.getResources().get(0).getResourceLocation());
        View setHeader = item.findViewById(R.id.farm_set_item_header);
        View setContent = item.findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
        return item;
    }
    public View.OnClickListener onFarmSetItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = (View) v.getTag();
            if(view.getVisibility() == View.VISIBLE){
                view.setVisibility(View.GONE);
            }else{
                view.setVisibility(View.VISIBLE);
            }
        }
    };



    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        farmSetModel = (FarmSetModel) this.getIntent().getSerializableExtra("farmSetModel");
        orderDataModel = (OrderDateModel) this.getIntent().getSerializableExtra("orderDataModel");
        orderHeader.setStep2();
        progressDrawable.start();
        loadDataFromServer();
    }


    private void findViews() {
        orderHeader = (OrderProcessHeader) this.findViewById(R.id.order_process_header);
        addBtn = (ImageView) this.findViewById(R.id.add_btn);
        jianBtn = (ImageView) this.findViewById(R.id.jian_btn);
        farmSetNumber = (TextView) this.findViewById(R.id.farm_set_number);
        progress = (ImageView) this.findViewById(R.id.progress);
        progressDrawable = (AnimationDrawable) progress.getDrawable();
        cotentView = this.findViewById(R.id.content);
        farmSetItemContent = (LinearLayout) this.findViewById(R.id.farm_set_item_content);
    }

    private View.OnClickListener onAddClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            num++;
            farmSetNumber.setText(num+"");
            checkNum();
        }
    };

    private View.OnClickListener onJianClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            num--;
            farmSetNumber.setText(num+"");
            checkNum();
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_set_order_info;
    }

    @Override
    protected String setActionBarTitle() {
        return "预订流程";
    }
}

package com.egceo.app.myfarm.order.actvity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.contact.activity.ContactListAcitivity;
import com.egceo.app.myfarm.entity.Contact;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.entity.OrderDateModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.entity.WalletModel;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.view.OrderProcessHeader;
import com.egceo.app.myfarm.util.AppUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    private Button genOrder;
    private TextView orderMoney;
    private TextView contactName;
    private View changeContact;
    private Contact contact;
    private AlertDialog timeDialog;
    private TextView fundText;
    private CheckBox fundCheckBox;
    private WalletModel walletModel;
    private View fundLayout;
    private boolean fundFlag = false;
    private BigDecimal fundMoney;
    @Override
    protected void initViews() {
        showProgress();
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        genOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contact == null) {
                    CommonUtil.showMessage(context, getString(R.string.choose_contact));
                    return;
                }
                CommonUtil.showSimpleProgressDialog(getString(R.string.gen_ordering),activity);
                String url = API.URL + API.API_URL.GEN_ORDER;
                TransferObject data = buildGenOrderData();
                AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
                    @Override
                    public void onSuccess(TransferObject data) {
                        OrderModel order = data.getOrderModel();
                        FarmSetModel farmSetModel = data.getFarmSetModel();
                        Intent intent = new Intent(context,OrderChoosePayActivity.class);
                        intent.putExtra("order",order);
                        intent.putExtra("farmSetModel",farmSetModel);
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finish();
                    }
                },data);
                request.execute();
            }
        });
        changeContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactListAcitivity.class);
                startActivityForResult(intent,1);
            }
        });
        fundCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fundFlag = isChecked;
                if(isChecked){
                    BigDecimal money = checkNum();
                    if(walletModel.getLvValue().floatValue() > farmSetModel.getFund()){
                        money = money.subtract(new BigDecimal(farmSetModel.getFund()));
                        fundMoney = new BigDecimal(farmSetModel.getFund());
                    }else{
                        money = money.subtract(new BigDecimal(walletModel.getLvValue().floatValue()));
                        fundMoney = new BigDecimal(walletModel.getLvValue());
                    }
                    orderMoney.setText("￥"+money.floatValue());
                }else{
                    checkNum();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                contact = (Contact) data.getSerializableExtra("contact");
                contactName.setText(contact.getConnectName()+","+contact.getConnectPhone());
            }
        }
    }

    //生成下单参数
    private TransferObject buildGenOrderData() {
        TransferObject data = AppUtil.getHttpData(context);
        data.setDate(orderDataModel.getDate());
        data.setCopies(num);
        data.setContactId(contact.getContactId()+"");
        if(fundFlag) {
            farmSetModel.setFund(fundMoney.floatValue());
        }else{
            farmSetModel.setFund(0f);
        }
        List<FarmItemsModel> farmItemsModels = new ArrayList<>();
        for(FarmItemsModel item : farmSetModel.getFarmItemsModels()){
            FarmItemsModel farmItem = new FarmItemsModel();
            farmItem.setFarmItemAliasId(item.getFarmItemAliasId());
            if(item.getConsumeTime() != null){
                farmItem.setConsumeTime(item.getConsumeTime());
            }else
                farmItem.setConsumeTime(orderDataModel.getDate());
            farmItemsModels.add(farmItem);
        }
        farmSetModel.setFarmItemsModels(farmItemsModels);
        data.setFarmSetModel(farmSetModel);
        return data;
    }

    private BigDecimal checkNum() {
        int max = farmSetModel.getMaxCanBuy();
        if(num <=1){
            jianBtn.setImageResource(R.mipmap.jian);
            jianBtn.setOnClickListener(null);
            if(num < max){
                addBtn.setImageResource(R.mipmap.add_s);
                addBtn.setOnClickListener(onAddClick);
            }
        }else{
            jianBtn.setImageResource(R.mipmap.jian_s);
            jianBtn.setOnClickListener(onJianClick);
            if(num >= max){
                addBtn.setImageResource(R.mipmap.add);
                addBtn.setOnClickListener(null);
            }
        }
        BigDecimal price = new BigDecimal(farmSetModel.getMinPrice());
        BigDecimal money =  price.multiply(new BigDecimal(num));
        orderMoney.setText("￥"+money.floatValue()+"");
        return money;
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_CHOOSE_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmSetAliasId(farmSetModel.getFarmSetAliasId());
        data.setDate(orderDataModel.getDate());
        AppRequest requset = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                walletModel = data.getWalletModel();
                farmSetModel = data.getFarmSetModel();
                if(farmSetModel != null){
                    showFarmSet();
                    checkNum();
                }
            }
            @Override
            public void onEnd() {
                super.onEnd();
                hideProgress();
            }
        },data);
        requset.execute();
    }

    private void showFarmSet() {
        if(farmSetModel.getFund() > 0) {
            fundText.setText("此套餐允许使用" + farmSetModel.getFund() + "旅游基金");
        }else{
            fundLayout.setVisibility(View.GONE);
        }
        cotentView.setVisibility(View.VISIBLE);
        progressDrawable.stop();
        progress.setVisibility(View.GONE);
        Collections.sort(farmSetModel.getFarmItemsModels());
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
        TextView farmSetTime = (TextView) item.findViewById(R.id.farmset_item_time);
        ImageView farmSetImg = (ImageView) item.findViewById(R.id.farmset_item_img);
        farmSetName.setText(farmItemsModel.getFarmName());
        farmSetDesc.setText(farmItemsModel.getFarmItemName());
        farmSetPrice.setVisibility(View.GONE);
        farmSetTime.setVisibility(View.VISIBLE);
        setFarmItemTime(farmSetTime,farmItemsModel);
        farmSetTag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        farmSetTag.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        farmSetDescList.setText(Html.fromHtml(farmItemsModel.getFarmItemDesc()));
        ImageLoaderUtil.getInstance().displayImg(farmSetImg,farmItemsModel.getResources().get(0).getResourceLocation()+AppUtil.FARM_SET_DETAIL_IMG_SIZE);
        View setHeader = item.findViewById(R.id.farm_set_item_header);
        View setContent = item.findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
        return item;
    }

    private void setFarmItemTime(TextView farmSetTime, FarmItemsModel farmItemsModel) {
        farmSetTime.setText(dateFormat.format(orderDataModel.getDate()));
        if("1".equals(farmItemsModel.getFarmItemType())){//如果是住 不允许更换时间
            farmSetTime.setTextColor(getResources().getColor(R.color.red));
            farmSetTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else{
            farmSetTime.setTextColor(getResources().getColor(R.color.green2));
            farmSetTime.setOnClickListener(onFarmSetTime);
            farmSetTime.setTag(farmItemsModel);
        }
    }

    private View.OnClickListener onFarmSetTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showTimeDialog(view);
        }
    };

    private void showTimeDialog(View view) {
        FarmItemsModel farmItemsModel = (FarmItemsModel) view.getTag();
        View time_view = inflater.inflate(R.layout.layout_order_farm_set_item_choose_date,null,false);
        LinearLayout timeView = (LinearLayout) time_view.findViewById(R.id.time_view);
        TextView timeText = getTimeText();
        timeText.setText(dateFormat.format(orderDataModel.getDate()));
        timeView.addView(timeText);
        timeView.setOnClickListener(onTimeChangeClick);
        timeView.setTag(farmItemsModel);
        timeDialog = new AlertDialog.Builder(activity)
                .setView(time_view)
                .create();
        timeDialog.getWindow().setWindowAnimations(R.style.dialog_time_style);
        timeDialog.getWindow().setGravity(Gravity.BOTTOM);
        timeDialog.show();
        loadTimeFromServer(view,timeView,farmItemsModel);
    }

    private void loadTimeFromServer(final View view, final LinearLayout timeView, final FarmItemsModel farmItemsModel) {
        String url = API.URL + API.API_URL.ORDER_CHANGE_FARM_ITEM_DATE;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmSetAliasId(farmSetModel.getFarmSetAliasId());
        data.setFarmItemAliasId(farmItemsModel.getFarmItemAliasId());
        data.setDate(orderDataModel.getDate());
        AppRequest requset = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                timeView.removeAllViews();
                for(int i=0;i < data.getDates().size();i++){
                    TextView timeText = getTimeText();
                    timeText.setText(dateFormat.format(data.getDates().get(i)));
                    timeView.addView(timeText);
                    timeText.setTag(R.id.tag_farm_sub_item,farmItemsModel);
                    timeText.setTag(R.id.tag_farm_sub_item_time,data.getDates().get(i));
                    timeText.setTag(R.id.tag_farm_sub_item_text,view);
                    timeText.setOnClickListener(onTimeChangeClick);
                }
            }
        },data);
        requset.execute();
    }

    private TextView getTimeText(){
        TextView time = new TextView(context);
        int padding = CommonUtil.Dp2Px(context,10);
        time.setPadding(padding,padding,padding,padding);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        time.setLayoutParams(params);
        time.setTextColor(Color.BLACK);
        time.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        time.setGravity(Gravity.CENTER);
        return time;
    }

    public View.OnClickListener onTimeChangeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FarmItemsModel farmItemsModel = (FarmItemsModel) view.getTag(R.id.tag_farm_sub_item);
            Date time = (Date) view.getTag(R.id.tag_farm_sub_item_time);
            TextView farmSetTime = (TextView) view.getTag(R.id.tag_farm_sub_item_text);
            farmItemsModel.setConsumeTime(time);
            farmSetTime.setText(dateFormat.format(time));
            timeDialog.dismiss();
        }
    };

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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        genOrder = (Button) this.findViewById(R.id.gen_order);
        orderMoney = (TextView) this.findViewById(R.id.order_money);
        contactName = (TextView) this.findViewById(R.id.contact_name);
        changeContact = this.findViewById(R.id.change_contact);
        fundText = (TextView) this.findViewById(R.id.jijin_text);
        fundCheckBox = (CheckBox) this.findViewById(R.id.fund_checkbox);
        fundLayout = this.findViewById(R.id.fund_layout);
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
        return getString(R.string.order_pay_process);
    }
}

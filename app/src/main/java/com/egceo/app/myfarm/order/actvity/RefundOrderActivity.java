package com.egceo.app.myfarm.order.actvity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.RefundRequestModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.OrderProcessHeader;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FreeMason on 2016/1/27.
 */
public class RefundOrderActivity extends BaseActivity{
    private OrderProcessHeader header;
    private TextView orderName;
    private TextView orderDesc;
    private TextView orderPrice;
    private SimpleDateFormat dateFormat;
    private OrderModel order;
    private LinearLayout backOrderReason;

    @Override
    protected void initViews() {
        findViews();
        initData();
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.BACK_ORDER_REASON;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                showRefundReason(data.getOrderModel().getRefundRequestModel());
            }
        },data);
        request.execute();
        header.setStep2();
    }

    private void showRefundReason(RefundRequestModel refundRequestModel) {
        setRefundStatus(refundRequestModel.getRefundDesc(),getString(R.string.refund_reason),refundRequestModel.getRefundRequestTime());
        if(refundRequestModel.getStatus().equals("2")){//同意退款
            setRefundStatus(getString(R.string.agree_refund),getString(R.string.admin_examine),refundRequestModel.getRefundConfirmTime());
        }else if(refundRequestModel.getStatus().equals("1")){//拒绝退款
            header.setStep3();
            setRefundStatus("拒绝退单",getString(R.string.admin_examine),refundRequestModel.getRefundConfirmTime());
        }else if(refundRequestModel.getStatus().equals("3")){//退单完成
            header.setStep3();
            setRefundStatus(getString(R.string.agree_refund),getString(R.string.admin_examine),refundRequestModel.getRefundConfirmTime());
            setRefundStatus(getString(R.string.refund_success),getString(R.string.result),refundRequestModel.getRefundCompletedTime());
        }
    }

    private void setRefundStatus(String desc,String title,Date time) {
        View item = inflater.inflate(R.layout.item_refund_reason,null,false);
        TextView refundDesc = (TextView) item.findViewById(R.id.refund_desc);
        TextView refundTitle = (TextView) item.findViewById(R.id.refund_title);
        TextView refundTime = (TextView) item.findViewById(R.id.refund_time);
        refundDesc.setText(desc);
        refundTitle.setText(title);
        refundTime.setText(dateFormat.format(time));
        backOrderReason.addView(item);
    }

    private void initData() {
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        header.setText(new String[]{getString(R.string.refund_info),getString(R.string.info_examine),getString(R.string.result)});
        header.setStep1();
        orderName.setText(order.getFarmSetModel().getFarmSetName());
        orderDesc.setText(order.getFarmSetModel().getFarmSetDesc());
        orderPrice.setText(order.getOrdePrice()+getString(R.string.rmb));
    }

    private void findViews() {
        header = (OrderProcessHeader) this.findViewById(R.id.order_header);
        orderName = (TextView) this.findViewById(R.id.order_item_name);
        orderDesc = (TextView) this.findViewById(R.id.order_item_desc);
        orderPrice = (TextView) this.findViewById(R.id.order_item_price);
        backOrderReason = (LinearLayout) this.findViewById(R.id.back_order_reason);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_back_order;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.back_order_process);
    }
}

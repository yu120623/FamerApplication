package com.egceo.app.myfarm.order.actvity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.RefundRequestModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.Date;

/**
 * Created by FreeMason on 2016/1/28.
 */
public class SubmitRefundActivity extends BaseActivity {
    private TextView orderName;
    private TextView orderDesc;
    private TextView orderPrice;
    private EditText reason;
    private Button sumbitRefund;
    private OrderModel order;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        sumbitRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = reason.getText().toString();
                if(TextUtils.isEmpty(text)){
                    CommonUtil.showMessage(context,getString(R.string.pls_enter_refund_reason));
                    return;
                }
                submit();
            }
        });
    }

    private void submit() {
        CommonUtil.showSimpleProgressDialog(getString(R.string.pls_wait_submit_order),activity);
        String url = API.URL + API.API_URL.BACK_ORDER;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        RefundRequestModel refundRequestModel = new RefundRequestModel();
        refundRequestModel.setRefundDesc(reason.getText().toString());
        refundRequestModel.setRefundRequestTime(new Date());
        data.setRefundRequestModel(refundRequestModel);
        AppRequest request  = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                Intent intent = new Intent();
                intent.putExtra("order",order);
                setResult(RESULT_OK,intent);
                finish();
            }
        },data);
        request.execute();
    }

    private void initData() {
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
        orderName.setText(order.getFarmSetModel().getFarmSetName());
        orderDesc.setText(order.getFarmSetModel().getFarmSetDesc());
        orderPrice.setText(order.getOrdePrice()+getString(R.string.rmb));
    }

    private void findViews() {
        orderName = (TextView) this.findViewById(R.id.order_item_name);
        orderDesc = (TextView) this.findViewById(R.id.order_item_desc);
        orderPrice = (TextView) this.findViewById(R.id.order_item_price);
        reason = (EditText) this.findViewById(R.id.refund_reason);
        sumbitRefund = (Button) this.findViewById(R.id.submit_refund);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_submit_refund;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.submit_refund);
    }
}

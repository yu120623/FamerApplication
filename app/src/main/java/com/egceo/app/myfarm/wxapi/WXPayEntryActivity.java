package com.egceo.app.myfarm.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.order.actvity.OrderDetailActivity;
import com.egceo.app.myfarm.util.AppUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private OrderModel order;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        order = (OrderModel) getIntent().getSerializableExtra("order");
        api = WXAPIFactory.createWXAPI(this, AppUtil.APP_ID);
        api.handleIntent(getIntent(), this);
        CommonUtil.showSimpleProgressDialog("正在跳转微信支付，请稍后",this);
        String url = API.URL + API.API_URL.WECHAT_ORDER;
        TransferObject data = AppUtil.getHttpData(getApplicationContext());
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(getApplicationContext(), url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                api.registerApp(AppUtil.APP_ID);
                Map<String,String> map = data.getMap();
                PayReq req = new PayReq();
                req.appId = AppUtil.APP_ID;
                req.partnerId = map.get("partnerId");
                req.prepayId = map.get("prepay_id");
                req.nonceStr = map.get("nonce_str");
                req.timeStamp = map.get("timestamp");
                req.packageValue = map.get("packageValue");
                req.sign = map.get("sign");
                req.extData	= "app data"; // optional
                api.sendReq(req);
            }
        },data);
        request.execute();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        CommonUtil.dismissSimpleProgressDialog();
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            Toast.makeText(this, "code = " + ((SendAuth.Resp) resp).code, Toast.LENGTH_SHORT).show();
        }

        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                break;
        }
        Intent intent = new Intent(getApplicationContext(),OrderDetailActivity.class);
        intent.putExtra("order",order);
        startActivity(intent);
        finish();
    }
}
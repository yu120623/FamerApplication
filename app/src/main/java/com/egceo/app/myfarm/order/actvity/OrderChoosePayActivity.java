package com.egceo.app.myfarm.order.actvity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.alipay.PayResult;
import com.egceo.app.myfarm.alipay.SignUtils;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.order.view.PayTypeView;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.util.RSAUtil;
import com.egceo.app.myfarm.view.OrderProcessHeader;
import com.egceo.app.myfarm.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class OrderChoosePayActivity extends BaseActivity {
    private Button payBtn;
    private OrderModel order;
    private FarmSetModel farmSetModel;
    private OrderProcessHeader orderProcessHeader;
    private TextView productInfo;
    private TextView time;
    private TextView orderMoney;
    private TextView actualMoney;
    private SimpleDateFormat dateFormat;
    private PayTypeView payTypeView;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initData() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
        farmSetModel = (FarmSetModel) this.getIntent().getSerializableExtra("farmSetModel");
        orderProcessHeader.setStep3();
        productInfo.setText(getString(R.string.product_info) + order.getFarmSetModel().getFarmSetName());
        time.setText(getString(R.string.order_time) + dateFormat.format(order.getJourneyTime()));
        orderMoney.setText(getString(R.string.order_money) + "￥" + order.getOrdePrice());
        actualMoney.setText("￥" + order.getOrdePrice());
    }

    private void initClick() {
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type = payTypeView.getPayType();
                if (type == PayTypeView.NO_PAY) {//没选择付款方式
                    CommonUtil.showMessage(context, getString(R.string.pls_choose_pay_type));
                } else if (type == PayTypeView.PAY_BANK) {//选择银联付款
                    payByBank();
                } else if (type == PayTypeView.PAY_WALLET) {//钱包付款
                    payByWallet();
                } else if (type == PayTypeView.PAY_WECHAT) {//微信支付
                    payByWeChat();
                } else if (type == PayTypeView.PAY_ZHIFUBAO) {//支付宝付款
                    payByZhiFuBao();
                }
            }
        });
    }

    private void payByBank() {
        CommonUtil.showSimpleProgressDialog("正在启动银联支付，请稍后", activity);
        String url = API.URL + API.API_URL.PAY_BANK;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        data.setType(" ");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if (data.getTn() != null) {
                    UPPayAssistEx.startPay(activity, null, null, data.getTn(), AppUtil.BANK_MODE);
                }
            }
            @Override
            public void onEnd() {
                super.onEnd();
                CommonUtil.dismissSimpleProgressDialog();
            }
        }, data);
        request.execute();
    }

    private void payByWeChat() {
        Intent intent = new Intent(context, WXPayEntryActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
        finish();
    }

    //钱包余额付款
    private void payByWallet() {
        String url = API.URL + API.API_URL.PAY_MENT;
        TransferObject data = AppUtil.getHttpData(context);
        data.setType("wallet");
        data.setType("");
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {

            }
        }, data);
        request.execute();
    }

    //用支付宝付款
    private void payByZhiFuBao() {
        payBtn.setClickable(false);
        CommonUtil.showSimpleProgressDialog(getString(R.string.go_to_zhifubao), activity, false);
        String orderInfo = SignUtils.getOrderInfo("测试", "测试", order.getOrdePrice() + "", order.getOrderSn(),"normal");
        final String payInfo = SignUtils.getPayInfo(orderInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            CommonUtil.dismissSimpleProgressDialog();
            finish();
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    //CommonUtil.showMessage(context, resultInfo);
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        CommonUtil.showMessage(context, getString(R.string.order_pay_success));
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            CommonUtil.showMessage(context, getString(R.string.order_pay_failed));
                        }
                    }
                    break;
                }
                default:
                    break;
            }
            gotoOrderActivity();
        }

        ;
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }


    private void gotoOrderActivity() {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    private void findViews() {
        payBtn = (Button) this.findViewById(R.id.pay_order);
        orderProcessHeader = (OrderProcessHeader) this.findViewById(R.id.order_process_header);
        productInfo = (TextView) this.findViewById(R.id.order_info_name);
        orderMoney = (TextView) this.findViewById(R.id.order_money);
        time = (TextView) this.findViewById(R.id.order_time);
        actualMoney = (TextView) this.findViewById(R.id.actual_money);
        payTypeView = (PayTypeView) this.findViewById(R.id.pay_type);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_choose_pay;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.order_pay_process);
    }
}

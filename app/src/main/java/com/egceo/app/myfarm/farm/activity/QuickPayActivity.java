package com.egceo.app.myfarm.farm.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.alipay.PayResult;
import com.egceo.app.myfarm.alipay.SignUtils;
import com.egceo.app.myfarm.entity.FarmModel;
import com.egceo.app.myfarm.entity.FarmQuickPayModel;
import com.egceo.app.myfarm.entity.FarmSetModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.home.activity.LoginActivity;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.order.view.PayTypeView;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.wxapi.WXPayEntryActivity;
import com.unionpay.UPPayAssistEx;

/**
 * Created by FreeMason on 2016/2/20.
 */
public class QuickPayActivity extends BaseActivity {
    private EditText quickPayPrice;
    private Button quickPayBtn;
    private PayTypeView payTypeView;
    private FarmModel farmModel;
    private TextView farmName;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initData() {
        farmModel = (FarmModel) this.getIntent().getSerializableExtra("farmModel");
        farmName.setText(farmModel.getFarmName());
    }

    private void initClick() {
        quickPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int type = payTypeView.getPayType();
                String price = quickPayPrice.getText().toString();
                if (type == PayTypeView.NO_PAY) {//没选择付款方式
                    CommonUtil.showMessage(context, getString(R.string.pls_choose_pay_type));
                    return;
                }
                if(TextUtils.isEmpty(price)){
                    CommonUtil.showMessage(context, getString(R.string.plz_enter_money));
                    return;
                }
                try {
                    price = Double.parseDouble(price)+"";
                }catch (NumberFormatException e){
                    CommonUtil.showMessage(context, getString(R.string.plz_enter_money));
                    return;
                }
                CommonUtil.showSimpleProgressDialog(getString(R.string.gen_ordering),activity);
                String url =  API.URL + API.API_URL.QUICK_PAY;
                TransferObject data = AppUtil.getHttpData(context);
                FarmQuickPayModel farmQuickPayModel = new FarmQuickPayModel();
                farmQuickPayModel.setFarmAliasId(farmModel.getFarmAliasId());
                farmQuickPayModel.setUserAliasId(data.getUserAliasId());
                farmQuickPayModel.setPrice(price);
                data.setFarmQuickPayModel(farmQuickPayModel);
                AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
                    @Override
                    public void onSuccess(TransferObject data) {
                        FarmQuickPayModel farmQuickPayModel = data.getFarmQuickPayModel();
                        if (type == PayTypeView.PAY_BANK) {//选择银联付款
                            payByBank(farmQuickPayModel);
                        } else if (type == PayTypeView.PAY_WECHAT) {//微信支付
                            payByWeChat(farmQuickPayModel);
                        } else if (type == PayTypeView.PAY_ZHIFUBAO) {//支付宝付款
                            payByZhiFuBao(farmQuickPayModel);
                        }
                    }
                },data);
                request.execute();
            }
        });
    }

    private void payByBank(FarmQuickPayModel farmQuickPayModel) {
        CommonUtil.showSimpleProgressDialog("正在启动银联支付，请稍后", activity);
        String url = API.URL + API.API_URL.PAY_BANK;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(farmQuickPayModel.getQuickPaySn());
        data.setType("quick");
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if (data.getTn() != null) {
                    UPPayAssistEx.startPay(activity, null, null, data.getTn(), AppUtil.BANK_MODE);
                    finish();
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

    private void payByWeChat(FarmQuickPayModel farmQuickPayModel) {
        Intent intent = new Intent(context, WXPayEntryActivity.class);
        OrderModel order = new OrderModel();
        order.setOrderSn(farmQuickPayModel.getQuickPaySn());
        intent.putExtra("order", order);
        intent.putExtra("type","quick");
        startActivity(intent);
        finish();
    }

    //用支付宝付款
    private void payByZhiFuBao(FarmQuickPayModel farmQuickPayModel) {
        CommonUtil.showSimpleProgressDialog(getString(R.string.go_to_zhifubao), activity, false);
        String orderInfo = SignUtils.getOrderInfo("测试", "测试", farmQuickPayModel.getPrice() + "", farmQuickPayModel.getQuickPaySn(),"quick");
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
        }

        ;
    };


    private void findViews() {
        quickPayPrice = (EditText) this.findViewById(R.id.quick_pay_price);
        quickPayBtn = (Button) this.findViewById(R.id.quick_pay_btn);
        payTypeView = (PayTypeView) this.findViewById(R.id.pay_type);
        farmName = (TextView) this.findViewById(R.id.farm_name);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_quickpay;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.fast_pay);
    }
}

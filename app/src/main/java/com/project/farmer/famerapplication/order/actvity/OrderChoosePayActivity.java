package com.project.farmer.famerapplication.order.actvity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.alipay.PayResult;
import com.project.farmer.famerapplication.alipay.SignUtils;
import com.project.farmer.famerapplication.entity.OrderModel;

public class OrderChoosePayActivity extends BaseActivity{
    private Button payBtn;
    private OrderModel order;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initData() {
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
    }

    private void initClick() {
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderInfo = SignUtils.getOrderInfo("测试", "测试", order.getOrdePrice()+"",order.getOrderSn());
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
        });
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
                        CommonUtil.showMessage(context,"成功");
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中",Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(context, "支付失败",Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void findViews() {
        payBtn = (Button) this.findViewById(R.id.pay_order);
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

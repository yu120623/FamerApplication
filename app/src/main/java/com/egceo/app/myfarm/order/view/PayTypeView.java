package com.egceo.app.myfarm.order.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.egceo.app.myfarm.R;

/**
 * Created by FreeMason on 2016/1/26.
 */
public class PayTypeView extends RelativeLayout{
    private LayoutInflater inflater;
    private RadioButton walletRadioBtn;
    private RadioButton wechatRadioBtn;
    private RadioButton zhifubaoRadioBtn;
    private RadioButton bankRadioBtn;
    public PayTypeView(Context context) {
        super(context);
        init();
    }

    public PayTypeView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public PayTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_pay_type,this,true);
        walletRadioBtn = (RadioButton) this.findViewById(R.id.wallet_radio_btn);
        wechatRadioBtn = (RadioButton) this.findViewById(R.id.wechat_radio_btn);
        zhifubaoRadioBtn = (RadioButton) this.findViewById(R.id.zhifubao_radio_btn);
        bankRadioBtn = (RadioButton) this.findViewById(R.id.bank_radio_btn);
    }

}

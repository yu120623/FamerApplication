package com.egceo.app.myfarm.order.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.egceo.app.myfarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FreeMason on 2016/1/26.
 */
public class PayTypeView extends RelativeLayout{
    private LayoutInflater inflater;
    private RadioButton walletRadioBtn;
    private RadioButton wechatRadioBtn;
    private RadioButton zhifubaoRadioBtn;
    private RadioButton bankRadioBtn;
    private View walletBtn;
    private View wechatBtn;
    private View zhifubaoBtn;
    private View bankBtn;
    private List<RadioButton> radioButtonList;

    public static int NO_PAY = 0;//没选择任何支付方式
    public static int PAY_WECHAT = 1;//微信支付
    public static int PAY_WALLET = 2;//钱包支付
    public static int PAY_ZHIFUBAO = 3;//支付宝支付
    public static int PAY_BANK = 4;//银联支付

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
        walletBtn = this.findViewById(R.id.wallet_btn);
        wechatBtn = this.findViewById(R.id.wechat_btn);
        zhifubaoBtn = this.findViewById(R.id.zhifubao_btn);
        bankBtn = this.findViewById(R.id.bank_btn);

        walletBtn.setTag(walletRadioBtn);
        wechatBtn.setTag(wechatRadioBtn);
        zhifubaoBtn.setTag(zhifubaoRadioBtn);
        bankBtn.setTag(bankRadioBtn);

        radioButtonList = new ArrayList<>();
        radioButtonList.add(walletRadioBtn);
        radioButtonList.add(wechatRadioBtn);
        radioButtonList.add(zhifubaoRadioBtn);
        radioButtonList.add(bankRadioBtn);
        initClick();
    }

    private void initClick() {
        walletRadioBtn.setOnCheckedChangeListener(onChangeListener);
        wechatRadioBtn.setOnCheckedChangeListener(onChangeListener);
        zhifubaoRadioBtn.setOnCheckedChangeListener(onChangeListener);
        bankRadioBtn.setOnCheckedChangeListener(onChangeListener);
        walletBtn.setOnClickListener(onCLick);
        wechatBtn.setOnClickListener(onCLick);
        zhifubaoBtn.setOnClickListener(onCLick);
        bankBtn.setOnClickListener(onCLick);
    }

    private OnClickListener onCLick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton btn = (RadioButton) view.getTag();
            btn.setChecked(!btn.isChecked());
        }
    };

    private CompoundButton.OnCheckedChangeListener onChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                for(RadioButton radio : radioButtonList){
                    radio.setChecked(false);
                }
                compoundButton.setChecked(true);
            }
        }
    };

    public int getPayType(){
        for(RadioButton btn : radioButtonList){
            if(btn.isChecked()){
                if(btn.getId() == R.id.wallet_radio_btn)
                    return PAY_WALLET;
                if(btn.getId() == R.id.wechat_radio_btn)
                    return PAY_WECHAT;
                if(btn.getId() == R.id.zhifubao_radio_btn)
                    return PAY_ZHIFUBAO;
                if(btn.getId() == R.id.bank_radio_btn)
                    return PAY_BANK;
            }
        }
        return NO_PAY;
    }
}

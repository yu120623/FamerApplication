package com.project.farmer.famerapplication.order.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.farmer.famerapplication.R;

public class OrderProcessHeader extends RelativeLayout{
    private View orderProcessStep1;
    private View orderProcessStep2;
    private View orderProcessStep3;
    private TextView orderProcessLine1;
    private TextView orderProcessLine2;
    private TextView orderChooseTimeText;
    private TextView orderSetInfoText;
    private TextView orderPayText;


    public OrderProcessHeader(Context context) {
        super(context);
        init();
    }

    public OrderProcessHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrderProcessHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.header_order_process,this,true);
        findViews();
    }

    private void initPostion() {
        int x1 = (int)((orderProcessStep1.getX() + (orderProcessStep1.getWidth()/2))-orderChooseTimeText.getWidth()/2);
        orderChooseTimeText.setX(x1);
        int x2 = (int)((orderProcessStep2.getX() + (orderProcessStep2.getWidth()/2))-orderSetInfoText.getWidth()/2);
        orderSetInfoText.setX(x2);
        int x3 = (int)((orderProcessStep3.getX() + (orderProcessStep3.getWidth()/2))-orderPayText.getWidth()/2);
        orderPayText.setX(x3);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initPostion();
    }

    private void findViews() {
        orderProcessStep1 = this.findViewById(R.id.order_process_step_1);
        orderProcessStep2 = this.findViewById(R.id.order_process_step_2);
        orderProcessStep3 = this.findViewById(R.id.order_process_step_3);
        orderProcessLine1 = (TextView) this.findViewById(R.id.order_process_line_1);
        orderProcessLine2 = (TextView) this.findViewById(R.id.order_process_line_2);
        orderChooseTimeText = (TextView) this.findViewById(R.id.order_choose_time_text);
        orderSetInfoText = (TextView) this.findViewById(R.id.order_set_info_text);
        orderPayText = (TextView) this.findViewById(R.id.order_pay_text);
    }

    public void setStep1(){
        resetStep();
        orderProcessStep1.setSelected(true);
        orderProcessLine1.setBackgroundColor(getResources().getColor(R.color.order_process_seleted_bg));
    }

    public void setStep2(){
        resetStep();
        orderProcessStep1.setSelected(true);
        orderProcessStep2.setSelected(true);
        orderProcessLine1.setBackgroundColor(getResources().getColor(R.color.order_process_seleted_bg));
        orderProcessLine2.setBackgroundColor(getResources().getColor(R.color.order_process_seleted_bg));
    }

    public void setStep3(){
        orderProcessStep1.setSelected(true);
        orderProcessStep2.setSelected(true);
        orderProcessStep3.setSelected(true);
        orderProcessLine1.setBackgroundColor(getResources().getColor(R.color.order_process_seleted_bg));
        orderProcessLine2.setBackgroundColor(getResources().getColor(R.color.order_process_seleted_bg));
    }

    private void resetStep(){
        orderProcessStep1.setSelected(false);
        orderProcessStep2.setSelected(false);
        orderProcessStep3.setSelected(false);
    }

}

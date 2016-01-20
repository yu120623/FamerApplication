package com.project.farmer.famerapplication.order.actvity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.baseandroid.BaseActivity;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.FarmSetModel;
import com.project.farmer.famerapplication.entity.OrderDateModel;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.view.OrderProcessHeader;
import com.project.farmer.famerapplication.util.AppUtil;
import com.samsistemas.calendarview.util.CalendarUtil;
import com.samsistemas.calendarview.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderChooseDateActivity extends BaseActivity{
    private FarmSetModel farmSetModel;
    private OrderProcessHeader orderPrcess;
    private CalendarView calendarView;
    private ImageView progress;
    private AnimationDrawable progressDrawable;
    private View cotentView;
    private List<OrderDateModel> orderDataModels;
    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        calendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull Date selectedDate) {
                for(OrderDateModel date : orderDataModels){
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar1.setTime(date.getDate());
                    calendar2.setTime(selectedDate);
                    if(CalendarUtil.isSameDay(calendar1,calendar2)){
                        Intent intent = new Intent(context,OrderSetInfoAcitivity.class);
                        intent.putExtra("farmSetModel",farmSetModel);
                        intent.putExtra("orderDataModel",date);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initData() {
        farmSetModel = (FarmSetModel) this.getIntent().getSerializableExtra("farmSetModel");
        orderPrcess.setStep1();
        progressDrawable.start();
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_CHOOSE_TIME;
        TransferObject data = AppUtil.getHttpData(context);
        data.setFarmSetAliasId(farmSetModel.getFarmSetAliasId());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                orderDataModels = data.getOrderDateModels();
                if(orderDataModels != null && orderDataModels.size() > 0){
                    showDate(data);
                    progressDrawable.stop();
                    progress.setVisibility(View.GONE);
                    cotentView.setVisibility(View.VISIBLE);
                }
            }
        },data);
        request.execute();
    }

    //显示日期 选择并下单
    private void showDate(TransferObject data) {
        orderDataModels = data.getOrderDateModels();
        for(OrderDateModel date : orderDataModels){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date.getDate());
            calendarView.addDateAndPrice(calendar,"¥"+date.getPrice());
        }
        Calendar mCalendar = Calendar.getInstance(Locale.getDefault());
        calendarView.refreshCalendar(mCalendar);
    }

    private void findViews() {
        orderPrcess = (OrderProcessHeader) this.findViewById(R.id.order_process_header);
        progress = (ImageView) this.findViewById(R.id.progress);
        progressDrawable = (AnimationDrawable) progress.getDrawable();
        calendarView = (CalendarView) this.findViewById(R.id.calendar_view);
        cotentView = this.findViewById(R.id.content);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_choose_date;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.order_pay_process);
    }
}

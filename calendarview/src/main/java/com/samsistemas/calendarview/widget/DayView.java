package com.samsistemas.calendarview.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samsistemas.calendarview.R;
import com.samsistemas.calendarview.decor.DayDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayView extends RelativeLayout {
    private List<DayDecorator> mDayDecoratorList;
    private Date mDate;
    private LayoutInflater inflater;
    private TextView dayText;
    private TextView price;
    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.day_view,this,true);
        dayText = (TextView) this.findViewById(R.id.day_text);
        price = (TextView) this.findViewById(R.id.day_price);
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.mDayDecoratorList = decorators;
        this.mDate = date;
        final SimpleDateFormat df = new SimpleDateFormat("d", Locale.getDefault());
        int day = Integer.parseInt(df.format(date));
        dayText.setText(String.valueOf(day));
    }

    public TextView getDayText(){
        return dayText;
    }

    public TextView getPriceText(){
        return price;
    }

    public void decorate() {
        //Set custom mDayDecoratorList
        if (null != mDayDecoratorList) {
            for (DayDecorator decorator : mDayDecoratorList) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return mDate;
    }
}

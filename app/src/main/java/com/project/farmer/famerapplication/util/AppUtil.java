package com.project.farmer.famerapplication.util;

import android.content.Context;
import android.graphics.Point;

import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.TransferObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/22.
 */
public class AppUtil {
    public static final String SP_CITY_CODE = "c";
    public static final String SP_CITY_NAME = "cn";
    public static final String SP_LAT = "t";
    public static final String SP_LOG = "g";


    public static final String SP_NEW_CITY_CODE = "n_c";
    public static final String SP_NEW_CITY_NAME = "n_cn";
    public static final String SP_NEW_LAT = "n_t";
    public static final String SP_NEW_LOG = "n_g";

    public static String getFarmSetTag(String tag) {
        if (tag.equals("1")) {
            return "吃";
        } else if (tag.equals("2")) {
            return "住";
        } else if (tag.equals("3")) {
            return "玩";
        } else if (tag.equals("4")) {
            return "提";
        } else if (tag.equals("5")) {
            return "品";
        }
        return "玩";
    }

    public static int getFarmSetTagBg(String tag) {
        if (tag.equals("1")) {
            return R.drawable.chi_bg;
        } else if (tag.equals("2")) {
            return R.drawable.zhu_bg;
        } else if (tag.equals("3")) {
            return R.drawable.wan_bg;
        } else if (tag.equals("4")) {
            return R.drawable.ti_bg;
        } else if (tag.equals("5")) {
            return R.drawable.pin_bg;
        } else
            return R.drawable.wan_bg;
    }


    public static Point random(int viewWidth,int viewHeight,int width,int height){
        //List<Point> points = new ArrayList<>();
        Random random = new Random();
        int x = random.nextInt(width-viewWidth-20)+20;
        int y = random.nextInt(height-viewHeight-20)+20;
        Point point = new Point(x,y);
        return point;
    }

    public static TransferObject getHttpData(Context context){
        return new TransferObject();
    }
}

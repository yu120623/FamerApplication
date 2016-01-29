package com.egceo.app.myfarm.util;

import android.content.Context;
import android.graphics.Point;

import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.TransferObject;

import java.util.Random;

/**
 * Created by Administrator on 2015/12/22.
 */
public class AppUtil {
    public static final String SP_CITY_CODE = "c";
    public static final String SP_CITY_NAME = "cn";
    public static final String SP_LAT = "t";
    public static final String SP_LOG = "g";
    public static final String FARM_IMG_SIZE = "@!w150h150";
    public static final String DESC_IMG_SIZE = "@!textimg";


    public static final String SP_NEW_CITY_CODE = "n_c";
    public static final String SP_NEW_CITY_NAME = "n_cn";

    public static final String ordPD = "ordPD";//已过期
    public static final String ordNP = "ordNP";//未付款
    public static final String ordHC = "ordHC";//已消费
    public static final String ordHP = "ordHP";//待消费
    public static final String ordNC = "ordNC";//已支付
    public static final String ordRB = "ordRB";//已退款

    public static final String rqAPY = "rqAPY";//申请
    public static final String rqAGR = "rqAGR";//同意
    public static final String reREF = "reREF";//拒绝
    public static final String rqCOM = "rqCOM";//完成

    public static final class RES_STATUS{
        public static final String STATUS_OK = "00000";
    }

    public static String getFarmSetTag(String tag) {
        if (tag.equals("1")) {
            return "住";
        } else if (tag.equals("2")) {
            return "吃";
        } else if (tag.equals("3")) {
            return "玩";
        } else if (tag.equals("4")) {
            return "提";
        } else if (tag.equals("5")) {
            return "品";
        }
        return "玩";
    }

    public static String getSearchTag(String tag) {
        if (tag.equals("p")) {
            return "专题";
        } else if (tag.equals("n")) {
            return "抢购";
        } else if (tag.equals("s")) {
            return "农场";
        }
        return "";
    }

    public static int getSearchTagBg(String tag) {
        if (tag.equals("p")) {
            return R.drawable.tag_p_bg;
        } else if (tag.equals("n")) {
            return R.drawable.tag_n_bg;
        } else if (tag.equals("s")) {
            return R.drawable.tag_s_bg;
        }
        return 0;
    }

    public static int getFarmSetTagBg(String tag) {
        if (tag.equals("1")) {
            return R.drawable.zhu_bg;
        } else if (tag.equals("2")) {
            return R.drawable.chi_bg;
        } else if (tag.equals("3")) {
            return R.drawable.wan_bg;
        } else if (tag.equals("4")) {
            return R.drawable.ti_bg;
        } else if (tag.equals("5")) {
            return R.drawable.pin_bg;
        } else
            return R.drawable.wan_bg;
    }


    public static Point random(int viewWidth, int viewHeight, int width, int height) {
        //List<Point> points = new ArrayList<>();
        Random random = new Random();
        int x = random.nextInt(width - viewWidth - 20) + 20;
        int y = random.nextInt(height - viewHeight - 20) + 20;
        Point point = new Point(x, y);
        return point;
    }

    public static TransferObject getHttpData(Context context) {
        TransferObject data =  new TransferObject();
        data.setUserAliasId("aaa");
        return data;
    }
}

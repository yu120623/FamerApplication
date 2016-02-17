package com.egceo.app.myfarm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.TransferObject;

import java.util.Random;

public class AppUtil {
    public static final String SP_CITY_CODE = "c";
    public static final String SP_CITY_NAME = "cn";
    public static final String SP_LAT = "t";
    public static final String SP_LOG = "g";
    public static final String FARM_IMG_SIZE = "@!w150h150";
    public static final String DESC_IMG_SIZE = "@!textimg";
    public static final String APP_ID = "wxb7092588e08a7e4f";//微信ID

    public static final String SP_NEW_CITY_CODE = "n_c";
    public static final String SP_NEW_CITY_NAME = "n_cn";

    public static final String DEFAULT_CITY_CODE = "0512";
    public static final String DEFAULT_CITY_NAME = "苏州市";

    public static final String IS_FIRST_APP ="is_first";

    public static final String L_N = "l_n";

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

    public static final int SMS_TIME = 120;//短信倒计时

    public static final String REG_SMS_ID = "sms_id";
    public static final String REG_SMS_TIME = "reg_sms_time";

    public static final String HANDLER_CHANGE_CITY = "h_c_c";//手动选择城市

    public static final String BANK_MODE = "00";//测试 01 上线00

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
        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
        TransferObject data =  new TransferObject();
        data.setFarmLatitude(Float.valueOf(sp.getFloat(AppUtil.SP_LAT, 0)).doubleValue());
        data.setFarmLongitude(Float.valueOf(sp.getFloat(AppUtil.SP_LOG, 0)).doubleValue());
        data.setCityCode(sp.getString(AppUtil.SP_CITY_CODE,AppUtil.DEFAULT_CITY_CODE));
        if(!"".equals(sp.getString(L_N,""))) {
            data.setUserAliasId(sp.getString(L_N,""));
            //ddata.setUserAliasId("aaa");
        }
        return data;
    }

    public static boolean checkIsLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
        if("".equals(sp.getString(L_N,""))){
            return false;
        }
        return true;
    }
}

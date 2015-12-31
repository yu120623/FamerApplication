package com.project.farmer.famerapplication.util;

import android.content.Context;

import com.project.farmer.famerapplication.entity.TransferObject;

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

    public static TransferObject getHttpData(Context context){
        return new TransferObject();
    }
}

package com.sunline.qi.utils;

import android.content.Context;

import com.sunline.qi.db.impl.DBUtilsImpl;

/**
 * Created by sunline on 2016/9/9.
 */
public interface BaseDeviceUtils {
    void createDevice(Context context,int width,int height,int leftMargin,int topMargin,DBUtilsImpl dbUtils);
    void deleteDevice();
    void updateDevice();
    void findDevice();
}

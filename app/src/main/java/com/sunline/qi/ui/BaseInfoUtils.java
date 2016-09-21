package com.sunline.qi.ui;

import com.sunline.qi.entity.AndroidInfo;

/**
 * Created by sunline on 2016/9/19.
 */
public interface BaseInfoUtils {
    public static final int INFO_CREATE = 0x01;
    public static final int INFO_UPDATE = 0x02;
    boolean addInfos(AndroidInfo[] infos);
    boolean updateInfos(AndroidInfo[] infos);
    boolean deleteInfos(String fk);
    AndroidInfo[] findInfos(String fk);
}

package com.sunline.qi.ui;

import com.sunline.qi.entity.EquipmentInfo;

/**
 * Created by sunline on 2016/9/19.
 */
public interface BaseInfoUtils {
    public static final int INFO_CREATE = 0x01;
    public static final int INFO_UPDATE = 0x02;
    boolean addInfos(EquipmentInfo[] infos);
    boolean updateInfos(EquipmentInfo[] infos);
    boolean deleteInfos(String fk);
    EquipmentInfo[] findInfos(String fk);
}

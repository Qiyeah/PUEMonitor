package com.sunline.qi.db;

import com.sunline.qi.entity.EquipmentInfo;

/**
 * Created by sunline on 2016/9/18.
 */
public interface InfoDao {
    boolean addInfo(EquipmentInfo info);
    boolean deleteInfo(String fk);
    boolean updateInfo(EquipmentInfo info);
    EquipmentInfo[] findInfos(String fk);
    boolean isExists(String fk);
}

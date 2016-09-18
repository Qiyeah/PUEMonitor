package com.sunline.qi.db;

import com.sunline.qi.entity.AS_Equipment;
import com.sunline.qi.entity.AS_EquipmentInfo;

import java.util.List;

/**
 * Created by sunline on 2016/9/18.
 */
public interface InfoDao {
    boolean addInfo(AS_EquipmentInfo info);
    boolean deleteInfo(String fk);
    boolean updateInfo(AS_EquipmentInfo info);
    AS_EquipmentInfo findInfo(String fk);
    AS_EquipmentInfo findInfo(int rid);
    List<AS_EquipmentInfo> findAll();
}

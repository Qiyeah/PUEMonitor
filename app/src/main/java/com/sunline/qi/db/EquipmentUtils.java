package com.sunline.qi.db;

import com.sunline.qi.entity.AS_Equipment;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface EquipmentUtils {
    boolean addEquipment(AS_Equipment ASEquipment);
    boolean deleteEquipment(String id);
    boolean updateEquipment(AS_Equipment ASEquipment);
    AS_Equipment findEquipment(String id);
    AS_Equipment findEquipment(int rid);
    List<AS_Equipment> findAll();
}

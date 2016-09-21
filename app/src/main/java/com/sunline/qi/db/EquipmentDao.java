package com.sunline.qi.db;

import com.sunline.qi.entity.AndroidEquipment;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface EquipmentDao {
    boolean addEquipment(AndroidEquipment ASEquipment);
    boolean deleteEquipment(String id);
    boolean updateEquipment(AndroidEquipment ASEquipment);
    AndroidEquipment findEquipment(String id);
    AndroidEquipment findEquipment(int rid);
    List<AndroidEquipment> findAll();
    String findForeignKey(int rid);
}

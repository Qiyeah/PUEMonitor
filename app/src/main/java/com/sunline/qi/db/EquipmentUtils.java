package com.sunline.qi.db;

import com.sunline.qi.entity.Equipment;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface EquipmentUtils {
    boolean addEquipment(Equipment equipment);
    boolean deleteEquipment(String id);
    boolean updateEquipment(Equipment equipment);
    Equipment findEquipment(String id);
    Equipment findEquipment(int rid);
    List<Equipment> findAll();
}

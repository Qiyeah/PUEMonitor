package com.sunline.qi.db;

import com.sunline.qi.entity.Equipment;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface BaseDBUtils {
    boolean addEquipment(String sql,Equipment equipment);
    boolean deleteEquipment(String sql,String id);
    boolean updateEquipment(String sql,Equipment equipment);
    Equipment findEquipment(String sql,String id);
    List<Equipment> findAll(String sql);
}

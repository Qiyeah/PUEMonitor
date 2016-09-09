package com.sunline.qi.db.impl;

import com.sunline.qi.db.BaseDBUtils;
import com.sunline.qi.entity.Equipment;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class DBUtilsImpl implements BaseDBUtils {
    @Override
    public boolean addEquipment(String sql, Equipment equipment) {
        return false;
    }

    @Override
    public boolean deleteEquipment(String sql, String id) {
        return false;
    }

    @Override
    public boolean updateEquipment(String sql, Equipment equipment) {
        return false;
    }

    @Override
    public Equipment findEquipment(String sql, String id) {
        return null;
    }

    @Override
    public List<Equipment> findAll(String sql) {
        return null;
    }
}

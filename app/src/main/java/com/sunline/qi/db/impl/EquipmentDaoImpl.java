package com.sunline.qi.db.impl;

import android.content.Context;
import android.database.Cursor;

import com.sunline.qi.db.EquipmentUtils;
import com.sunline.qi.db.DBHelper;
import com.sunline.qi.entity.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class EquipmentDaoImpl extends DBHelper implements EquipmentUtils {
    public EquipmentDaoImpl(Context context) {
        super(context);
    }

    @Override
    public boolean addEquipment(Equipment equipment) {
        String sql = "insert into Equipment (" +
                "_id,rid,name,port,rate,addr,timeout,data,stop,parity,switch,delayed" +
                ") values(" +
                "?,?,?,?,?,?,?,?,?,?,?,?)";
        return update(sql, new Object[]{equipment.getId(),equipment.getRid(), equipment.getName(), equipment.getPort(), equipment.getRate(),
                equipment.getAddr(),equipment.getTimeOut(), equipment.getDataBits(), equipment.getStopBits(),
                equipment.getParity(),equipment.getSwitch(), equipment.getDelay()});
    }

    @Override
    public boolean deleteEquipment(String id) {
        String sql = "delete * from Equipment where _id = ?";
        return update(sql,id);
    }

    @Override
    public boolean updateEquipment(Equipment equipment) {
        String sql = "update set (name = ? ,port = ?,rate = ?,addr = ?,timeout = ?,data = ?,stop = ?," +
                "parity = ?,switch = ?,delayed = ? ,dt = ?) from Equipment where _id = ?";
        return update(sql, new Object[]{equipment.getName(), equipment.getPort(), equipment.getRate(),
                equipment.getAddr(),equipment.getTimeOut(), equipment.getDataBits(), equipment.getStopBits(),
                equipment.getParity(),equipment.getSwitch(), equipment.getDelay(),equipment.getDate(), equipment.getId()});
    }

    @Override
    public Equipment findEquipment( String id) {
        String sql = "select (id,name,port,rate,addr,timeout,data,stop,parity,switch,delayed) " +
                "from Equipment where _id = ?";
        Cursor cursor = query(sql,new String[]{id});
        Equipment equipment = new Equipment();
        while (0 < cursor.getCount()){
            equipment.setId(cursor.getString(cursor.getColumnIndex("_id")));
            equipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            equipment.setPort(cursor.getString(cursor.getColumnIndex("port")));
            equipment.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            equipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
            equipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")));
            equipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")));
            equipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")));
            equipment.setParity(cursor.getString(cursor.getColumnIndex("parity")));
            equipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")));
            equipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")));
        }
        return equipment;
    }

    @Override
    public List<Equipment> findAll() {
        String sql = "select * from Equipment";
        Cursor cursor = query(sql);
        List<Equipment> list = new ArrayList<Equipment>();

        while (cursor.moveToNext()){
            Equipment equipment = new Equipment();
            equipment.setId(cursor.getString(cursor.getColumnIndex("_id")));
            equipment.setRid(cursor.getInt(cursor.getColumnIndex("rid")));
            equipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            equipment.setPort(cursor.getString(cursor.getColumnIndex("port")));
            equipment.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            equipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
            equipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")));
            equipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")));
            equipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")));
            equipment.setParity(cursor.getString(cursor.getColumnIndex("parity")));
            equipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")));
            equipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")));
            list.add(equipment);
        }
        return list;
    }
}

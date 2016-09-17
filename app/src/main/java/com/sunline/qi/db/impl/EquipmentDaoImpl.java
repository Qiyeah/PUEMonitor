package com.sunline.qi.db.impl;

import android.content.Context;
import android.database.Cursor;

import com.sunline.qi.db.EquipmentUtils;
import com.sunline.qi.db.DBHelper;
import com.sunline.qi.entity.AS_Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class EquipmentDaoImpl extends DBHelper implements EquipmentUtils {
    private Context mContext;
    public EquipmentDaoImpl(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean addEquipment(AS_Equipment ASEquipment) {
        String sql = "insert into Equipment (" +
                "_id,rid,name,port,rate,addr,timeout,data,stop,parity,switch,delayed" +
                ") values(" +
                "?,?,?,?,?,?,?,?,?,?,?,?)";
        if (ASEquipment.isNull()){
            return update(sql, new Object[]{ASEquipment.getId(), ASEquipment.getRid(), ASEquipment.getName(), ASEquipment.getPort(), ASEquipment.getRate(),
                    ASEquipment.getAddr(), ASEquipment.getTimeOut(), ASEquipment.getDataBits(), ASEquipment.getStopBits(),
                    ASEquipment.getParity(), ASEquipment.getSwitch(), ASEquipment.getDelay()});
        }
        return false;
    }

    @Override
    public boolean deleteEquipment(String id) {
        String sql = "delete from Equipment where _id = ?";
        return update(sql,id);
    }

    @Override
    public boolean updateEquipment(AS_Equipment ASEquipment) {
        System.out.println("ID:" + ASEquipment.getId()
                + "\nRID:" + "" + ASEquipment.getRid()
                + "\nName" + ASEquipment.getName()
                + "\nPort:" + ASEquipment.getPort()
                + "\nRate:" + ASEquipment.getRate()
                + "\nAddr:" + ASEquipment.getAddr()
                + "\nData:" + ASEquipment.getDataBits()
                + "\nStop:" + ASEquipment.getStopBits()
                + "\nState:" + ASEquipment.getSwitch()
                + "\nDelay:" + ASEquipment.getDelay()
             );
        String sql = "update Equipment set name = ? ,port = ?,rate = ?,addr = ?,timeout = ?,data = ?,stop = ?," +
                "parity = ?,switch = ?,delayed = ? ,dt = (datetime('now','localtime')) where rid = ?";
        return update(sql, new Object[]{ASEquipment.getName(), ASEquipment.getPort(), ASEquipment.getRate(),
                ASEquipment.getAddr(), ASEquipment.getTimeOut(), ASEquipment.getDataBits(), ASEquipment.getStopBits(),
                ASEquipment.getParity(), ASEquipment.getSwitch(), ASEquipment.getDelay(), ASEquipment.getRid()});
    }

    @Override
    public AS_Equipment findEquipment(String id) {
        String sql = "select id,name,port,rate,addr,timeout,data,stop,parity,switch,delayed " +
                "from Equipment where _id = ?";
        Cursor cursor = query(sql,new String[]{id});
        AS_Equipment ASEquipment = new AS_Equipment();
        while (0 < cursor.getCount()){
            ASEquipment.setId(cursor.getString(cursor.getColumnIndex("_id")));
            ASEquipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            ASEquipment.setPort(cursor.getString(cursor.getColumnIndex("port")));
            ASEquipment.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            ASEquipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
            ASEquipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")));
            ASEquipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")));
            ASEquipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")));
            ASEquipment.setParity(cursor.getString(cursor.getColumnIndex("parity")));
            ASEquipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")));
            ASEquipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")));
        }
        return ASEquipment;
    }

    @Override
    public AS_Equipment findEquipment(int rid) {
        String sql = "select _id,name,port,rate,addr,timeout,data,stop,parity,switch,delayed " +
                "from Equipment where rid = ?";
        Cursor cursor = query(sql,Integer.toString(rid));
        AS_Equipment ASEquipment = new AS_Equipment();
        while (cursor.moveToNext()){
            ASEquipment.setId(cursor.getString(cursor.getColumnIndex("_id")).trim());
            ASEquipment.setName(cursor.getString(cursor.getColumnIndex("name")).trim());
            ASEquipment.setPort(cursor.getString(cursor.getColumnIndex("port")).trim());
            ASEquipment.setRate(cursor.getString(cursor.getColumnIndex("rate")).trim());
            ASEquipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")).trim());
            ASEquipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")).trim());
            ASEquipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")).trim());
            ASEquipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")).trim());
            ASEquipment.setParity(cursor.getString(cursor.getColumnIndex("parity")).trim());
            ASEquipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")).trim());
            ASEquipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")).trim());
        }
        return ASEquipment;
    }

    @Override
    public List<AS_Equipment> findAll() {
        String sql = "select * from Equipment";
        Cursor cursor = query(sql);
        List<AS_Equipment> list = new ArrayList<AS_Equipment>();

        while (cursor.moveToNext()){
            AS_Equipment ASEquipment = new AS_Equipment();
            ASEquipment.setId(cursor.getString(cursor.getColumnIndex("_id")));
            ASEquipment.setRid(cursor.getInt(cursor.getColumnIndex("rid")));
            ASEquipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            ASEquipment.setPort(cursor.getString(cursor.getColumnIndex("port")));
            ASEquipment.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            ASEquipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
            ASEquipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")));
            ASEquipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")));
            ASEquipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")));
            ASEquipment.setParity(cursor.getString(cursor.getColumnIndex("parity")));
            ASEquipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")));
            ASEquipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")));
            list.add(ASEquipment);
        }
        return list;
    }
}

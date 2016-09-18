package com.sunline.qi.db.impl;

import android.content.Context;
import android.database.Cursor;

import com.sunline.qi.db.EquipmentDao;
import com.sunline.qi.db.DBHelper;
import com.sunline.qi.entity.AS_Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class EquipmentDaoImpl extends DBHelper implements EquipmentDao {
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
    public boolean updateEquipment(AS_Equipment asEquipment) {
        System.out.println("ID:" + asEquipment.getId()
                + "\nRID:" + "" + asEquipment.getRid()
                + "\nName" + asEquipment.getName()
                + "\nPort:" + asEquipment.getPort()
                + "\nRate:" + asEquipment.getRate()
                + "\nAddr:" + asEquipment.getAddr()
                + "\nData:" + asEquipment.getDataBits()
                + "\nStop:" + asEquipment.getStopBits()
                + "\nState:" + asEquipment.getSwitch()
                + "\nDelay:" + asEquipment.getDelay()
             );
        String sql = "update Equipment set name = ? ,port = ?,rate = ?,addr = ?,timeout = ?,data = ?,stop = ?," +
                "parity = ?,switch = ?,delayed = ? ,dt = (datetime('now','localtime')) where rid = ?";
        return update(sql, new Object[]{asEquipment.getName(), asEquipment.getPort(), asEquipment.getRate(),
                asEquipment.getAddr(), asEquipment.getTimeOut(), asEquipment.getDataBits(), asEquipment.getStopBits(),
                asEquipment.getParity(), asEquipment.getSwitch(), asEquipment.getDelay(), asEquipment.getRid()});
    }

    @Override
    public AS_Equipment findEquipment(String id) {
        String sql = "select id,name,port,rate,addr,timeout,data,stop,parity,switch,delayed " +
                "from Equipment where _id = ?";
        Cursor cursor = query(sql,new String[]{id});
        AS_Equipment asEquipment = new AS_Equipment();
        while (0 < cursor.getCount()){
            asEquipment.setId(cursor.getString(cursor.getColumnIndex("_id")));
            asEquipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            asEquipment.setPort(cursor.getString(cursor.getColumnIndex("port")));
            asEquipment.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            asEquipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
            asEquipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")));
            asEquipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")));
            asEquipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")));
            asEquipment.setParity(cursor.getString(cursor.getColumnIndex("parity")));
            asEquipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")));
            asEquipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")));
        }
        return asEquipment;
    }

    @Override
    public AS_Equipment findEquipment(int rid) {
        String sql = "select _id,name,port,rate,addr,timeout,data,stop,parity,switch,delayed " +
                "from Equipment where rid = ?";
        Cursor cursor = query(sql,Integer.toString(rid));
        AS_Equipment asEquipment = new AS_Equipment();
        while (cursor.moveToNext()){
            asEquipment.setId(cursor.getString(cursor.getColumnIndex("_id")).trim());
            asEquipment.setName(cursor.getString(cursor.getColumnIndex("name")).trim());
            asEquipment.setPort(cursor.getString(cursor.getColumnIndex("port")).trim());
            asEquipment.setRate(cursor.getString(cursor.getColumnIndex("rate")).trim());
            asEquipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")).trim());
            asEquipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")).trim());
            asEquipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")).trim());
            asEquipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")).trim());
            asEquipment.setParity(cursor.getString(cursor.getColumnIndex("parity")).trim());
            asEquipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")).trim());
            asEquipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")).trim());
        }
        return asEquipment;
    }

    @Override
    public List<AS_Equipment> findAll() {
        String sql = "select * from Equipment";
        Cursor cursor = query(sql);
        List<AS_Equipment> list = new ArrayList<AS_Equipment>();

        while (cursor.moveToNext()){
            AS_Equipment asEquipment = new AS_Equipment();
            asEquipment.setId(cursor.getString(cursor.getColumnIndex("_id")));
            asEquipment.setRid(cursor.getInt(cursor.getColumnIndex("rid")));
            asEquipment.setName(cursor.getString(cursor.getColumnIndex("name")));
            asEquipment.setPort(cursor.getString(cursor.getColumnIndex("port")));
            asEquipment.setRate(cursor.getString(cursor.getColumnIndex("rate")));
            asEquipment.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
            asEquipment.setTimeOut(cursor.getString(cursor.getColumnIndex("timeout")));
            asEquipment.setDataBits(cursor.getString(cursor.getColumnIndex("data")));
            asEquipment.setStopBits(cursor.getString(cursor.getColumnIndex("stop")));
            asEquipment.setParity(cursor.getString(cursor.getColumnIndex("parity")));
            asEquipment.setSwitch(cursor.getString(cursor.getColumnIndex("switch")));
            asEquipment.setDelay(cursor.getString(cursor.getColumnIndex("delayed")));
            list.add(asEquipment);
        }
        return list;
    }
}

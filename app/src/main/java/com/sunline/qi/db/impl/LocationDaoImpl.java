package com.sunline.qi.db.impl;

import android.content.Context;
import android.database.Cursor;

import com.sunline.qi.db.DBHelper;
import com.sunline.qi.db.LocationUtils;
import com.sunline.qi.entity.Location;

/**
 * Created by qi on 2016/9/8.
 */
public class LocationDaoImpl extends DBHelper implements LocationUtils {
    public LocationDaoImpl(Context context) {
        super(context);
    }

    @Override
    public boolean addLocation(Location location) {
        String sql = "insert into Location (" +
                "_id,fk,width,height,leftMargin,topMargin) values(?,?,?,?,?,?)";
        return update(sql, new Object[]{location.getId(), location.getfId(), location.getWidth(),
                location.getHeight(), location.getxAxis(), location.getyAxis()});
    }

    @Override
    public boolean deleteLocation(String fk) {
        String sql = "delete from Location where fk = ?";
        return update(sql, fk);
    }

    @Override
    public boolean updateLocation(Location location) {
        String sql = "update Location set leftMargin = ?,topMargin = ?,dt = (datetime('now','localtime')) where _id = ?";
        return update(sql, new Object[]{location.getxAxis(),location.getyAxis(), location.getId()});
    }

    @Override
    public Location findLocation(String fk) {
        String sql = "select _id,width,height,leftMargin,topMargin from Location where fk = ?";
        Cursor cursor = query(sql, fk);
        while (cursor.moveToNext()) {
            return new Location(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    fk,
                    cursor.getInt(cursor.getColumnIndex("width")),
                    cursor.getInt(cursor.getColumnIndex("height")),
                    cursor.getInt(cursor.getColumnIndex("leftMargin")),
                    cursor.getInt(cursor.getColumnIndex("topMargin")));
        }

        return null;
    }

    @Override
    public Location findLocation(int rid) {
        String sql = "select _id,fk,width,height,leftMargin,topMargin from Location where _id = ?";
        Cursor cursor = query(sql, Integer.toString(rid));
        while (cursor.moveToNext()) {
            return new Location(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("fk")).trim(),
                    cursor.getInt(cursor.getColumnIndex("width")),
                    cursor.getInt(cursor.getColumnIndex("height")),
                    cursor.getInt(cursor.getColumnIndex("leftMargin")),
                    cursor.getInt(cursor.getColumnIndex("topMargin")));
        }
        return null;
    }

    @Override
    public String findDeviceId(int rid) {
        String sql = "select fk from Location where _id = ?";
        Cursor cursor = query(sql,Integer.toString(rid));
        String fk = "";
        if (cursor.moveToNext()){
            fk = cursor.getString(cursor.getColumnIndex("fk"));
        }
        return fk;
    }
}

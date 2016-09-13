package com.sunline.qi.db.impl;

import android.content.Context;
import android.database.Cursor;

import com.sunline.qi.db.DBHelper;
import com.sunline.qi.db.LocationUtils;
import com.sunline.qi.entity.EquipmentLocation;

/**
 * Created by qi on 2016/9/8.
 */
public class LocationDaoImpl extends DBHelper implements LocationUtils {
    public LocationDaoImpl(Context context) {
        super(context);
    }

    @Override
    public boolean addLocation(EquipmentLocation location) {
        String sql = "insert into EquipmentLocation (" +
                " _id,fk,width,height,leftMargin,topMargin) values(?,?,?,?,?,?)";
        return update(sql, new Object[]{location.getId(), location.getfId(), location.getWidth(),
                location.getHeight(), location.getLeftMargin(), location.getTopMargin()});
    }

    @Override
    public boolean deleteLocation(String fk) {
        String sql = "delete * from EquipmentLocation where fk = ?";
        return update(sql, fk);
    }

    @Override
    public boolean updateLocation(EquipmentLocation location) {
        String sql = "update EquipmentLocation set leftMargin = ?,topMargin = ?,dt = (datetime('now','localtime')) where _id = ?";
        return update(sql, new Object[]{location.getLeftMargin(),location.getTopMargin(), location.getId()});
    }

    @Override
    public EquipmentLocation findLocation(String fk) {
        String sql = "select _id,width,height,leftMargin,topMargin from EquipmentLocation where fk = ?";
        Cursor cursor = query(sql, new String[]{fk});
        while (cursor.moveToNext()) {
            return new EquipmentLocation(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    fk,
                    cursor.getInt(cursor.getColumnIndex("width")),
                    cursor.getInt(cursor.getColumnIndex("height")),
                    cursor.getInt(cursor.getColumnIndex("leftMargin")),
                    cursor.getInt(cursor.getColumnIndex("topMargin")));
        }

        return null;
    }
}

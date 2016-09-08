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
        String sql = "update Equipment set (width = ? ,height = ?,leftMargin = ?,topMargin = ?)  where _id = ?";
        return update(sql, new Object[]{location.getWidth(), location.getHeight(), location.getLeftMargin(),
                location.getTopMargin(), location.getfId()});
    }

    @Override
    public EquipmentLocation findLocation(String fk) {
        String sql = "select (width,height,leftMargin,topMargin) from EquipmentLocation where fid = ?";
        Cursor cursor = query(sql, new String[]{fk});
        if (0 < cursor.getCount()) {
            return new EquipmentLocation(fk,
                    cursor.getInt(cursor.getColumnIndex("width")),
                    cursor.getInt(cursor.getColumnIndex("height")),
                    cursor.getInt(cursor.getColumnIndex("leftMargin")),
                    cursor.getInt(cursor.getColumnIndex("topMargin")));
        }

        return null;
    }
}

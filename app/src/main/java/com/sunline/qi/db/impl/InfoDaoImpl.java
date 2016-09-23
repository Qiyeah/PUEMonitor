package com.sunline.qi.db.impl;

import android.content.Context;
import android.database.Cursor;

import com.sunline.qi.db.DBHelper;
import com.sunline.qi.db.InfoDao;
import com.sunline.qi.entity.EquipmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/18.
 */
public class InfoDaoImpl extends DBHelper implements InfoDao {
    private Context mContext;
    public InfoDaoImpl(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean addInfo(EquipmentInfo info) {

        String sql = "insert into EquipmentInfo (" +
                "_id,fk,route,name,total_symbol,total_per,it_symbol,it_per" +
                ") values(" +
                "?,?,?,?,?,?,?,?)";

        return update(sql,new Object[]{info.getId(),info.getfId(), info.getRoute(),info.getRouteName(),
                info.getTotalSymbol(),info.getTotalPer(),info.getITSymbol(),info.getITPer()});
    }

    @Override
    public boolean deleteInfo(String fk) {
        String sql = "delete from EquipmentInfo where fk = ?";
        return update(sql,fk);
    }

    @Override
    public boolean updateInfo(EquipmentInfo info) {
        String sql = "update EquipmentInfo set name = ?,total_per = ?,total_symbol = ? ,it_per = ? ,it_symbol = ? ," +
                "dt = (datetime('now','localtime')) where fk = ? and route = ? ";
        return update(sql,new Object[]{info.getRouteName(),info.getTotalPer(),info.getTotalSymbol(),
                info.getITPer(),info.getITSymbol(),info.getfId(),info.getRoute()});
    }

    @Override
    public EquipmentInfo[] findInfos(String fk) {
        List<EquipmentInfo> infos = new ArrayList<>();
        String sql = "select _id,route,name,total_symbol,total_per,it_symbol,it_per from EquipmentInfo where fk = ?";
        Cursor cursor = query(sql,fk);
        while (cursor.moveToNext()){
            String _id = cursor.getString(cursor.getColumnIndex("_id"));
            int route = cursor.getInt(cursor.getColumnIndex("route"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int total_symbol = cursor.getInt(cursor.getColumnIndex("total_symbol"));
            int total_per = cursor.getInt(cursor.getColumnIndex("total_per"));
            int it_symbol = cursor.getInt(cursor.getColumnIndex("it_symbol"));
            int it_per = cursor.getInt(cursor.getColumnIndex("it_per"));
            EquipmentInfo info = new EquipmentInfo(fk,_id,route,name,total_per,it_per,total_symbol,it_symbol);
            infos.add(info);
        }
        return infos.toArray(new EquipmentInfo[0]);
    }

    @Override
    public boolean isExists(String fk) {
        boolean flag = false;
        String sql = "select count(fk) as sum from EquipmentInfo where fk = ? ";
        Cursor cursor = query(sql,fk);
        if (cursor.moveToNext()){
            int sum = cursor.getInt(cursor.getColumnIndex("sum"));
            if (sum>0){
                flag = true;
            }
        }
        return flag;
    }


}

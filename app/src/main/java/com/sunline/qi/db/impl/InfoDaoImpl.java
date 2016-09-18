package com.sunline.qi.db.impl;

import android.content.Context;

import com.sunline.qi.db.DBHelper;
import com.sunline.qi.db.InfoDao;
import com.sunline.qi.entity.AS_EquipmentInfo;

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
    public boolean addInfo(AS_EquipmentInfo info) {
        String sql = "insert into EquipmentInfo (" +
                "_id,route,name,attr,fk,per,symbol" +
                ") values(" +
                "?,?,?,?,?,?)";

        return update(sql,new Object[]{info.getId(),info.getRoute(),info.getRouteName(),info.getRouteAttr(),info.getfId(),
        info.getPer(),info.getSymbol()});
    }

    @Override
    public boolean deleteInfo(String fk) {
        String sql = "delete from EquipmentInfo where fk = ?";
        return update(sql,fk);
    }

    @Override
    public boolean updateInfo(AS_EquipmentInfo info) {
        String sql = "update EquipmentInfo set name = ?,attr = ?,per = ?,symbol = ? ," +
                "dt = (datetime('now','localtime')) where fk = ? and route = ? ";
        return update(sql,new Object[]{info.getRouteName(),info.getRouteAttr(),
                info.getPer(),info.getSymbol(),info.getfId(),info.getRoute()});
    }

    @Override
    public AS_EquipmentInfo findInfo(String fk) {
        return null;
    }

    @Override
    public AS_EquipmentInfo findInfo(int rid) {
        return null;
    }

    @Override
    public List<AS_EquipmentInfo> findAll() {
        return null;
    }
}

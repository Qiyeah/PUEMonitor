package com.sunline.qi.ui.impl;

import android.content.Context;

import com.sunline.qi.db.InfoDao;
import com.sunline.qi.db.impl.InfoDaoImpl;
import com.sunline.qi.entity.EquipmentInfo;
import com.sunline.qi.ui.BaseInfoUtils;

/**
 * Created by sunline on 2016/9/19.
 */
public class InfoUtilsImpl implements BaseInfoUtils {
    InfoDao dao ;
    public InfoUtilsImpl(Context context) {
       dao = new InfoDaoImpl(context);
    }

    @Override
    public boolean addInfos(EquipmentInfo[] infos) {
        boolean flag = false;
        for (EquipmentInfo info : infos) {
         /*   System.out.println(info.getfId());
            System.out.println();*/
           flag = dao.addInfo(info);
        }
        return flag;
    }

    @Override
    public boolean updateInfos(EquipmentInfo[] infos) {
        boolean flag = false;
        for (EquipmentInfo info : infos) {
            flag = dao.updateInfo(info);
        }
        return flag;
    }

    @Override
    public boolean deleteInfos(String fk) {
        return dao.deleteInfo(fk);
    }

    @Override
    public EquipmentInfo[] findInfos(String fk) {
        return dao.findInfos(fk);
    }
}

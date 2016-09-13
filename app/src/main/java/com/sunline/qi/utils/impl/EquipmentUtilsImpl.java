package com.sunline.qi.utils.impl;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunline.qi.activity.R;
import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.EquipmentLocation;
import com.sunline.qi.listener.EquipmentOnClickListenerImpl;
import com.sunline.qi.listener.EquipmentOnTouchListenerImpl;
import com.sunline.qi.utils.BaseEquipmentUtils;
import com.sunline.qi.utils.IDUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class EquipmentUtilsImpl implements BaseEquipmentUtils {
    private Context mContext;

    public EquipmentUtilsImpl(Context context) {
        mContext = context;
    }

    @Override
    public Button createEquipments(Context context,Equipment equipment,EquipmentLocation location) {
        EquipmentDaoImpl dbUtils = new EquipmentDaoImpl(context);
        Button button = new Button(context);
        button.setText(equipment.getName());

        int width = location.getWidth();
        int height = location.getHeight();
        int xAxis = location.getLeftMargin();
        int yAxis = location.getTopMargin();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
        params.leftMargin = location.getLeftMargin();
        params.topMargin = location.getTopMargin();
        button.setLayoutParams(params);
        boolean flag = false;
        try {
            flag = dbUtils.addEquipment(equipment);
        } catch (Exception e) {
            Toast.makeText(mContext,"数据库添加失败！",Toast.LENGTH_LONG).show();
        }
        if (flag){
            LocationDaoImpl locationDao = new LocationDaoImpl(context);
            locationDao.addLocation(new EquipmentLocation(IDUtils.generateRID(),
                    equipment.getId(), width, height, xAxis, yAxis));
            /**
             * 给创建的控制添加监听
             */
            button.setOnClickListener(new EquipmentOnClickListenerImpl(mContext));
            button.setOnTouchListener(new EquipmentOnTouchListenerImpl(mContext, width, height));
            return button;
        }
       return null;
    }

    @Override
    public void deleteEquipments(Context context,Button equipment, String id) {
        EquipmentDaoImpl dbUtils = new EquipmentDaoImpl(context);
        equipment.setVisibility(View.GONE);
        dbUtils.deleteEquipment(id);
    }

    @Override
    public void updateEquipments(Context context, Equipment equipment) {
        EquipmentDaoImpl dbUtils = new EquipmentDaoImpl(context);
        dbUtils.updateEquipment(equipment);
    }

    @Override
    public Equipment findEquipments(Context context, String id) {
        EquipmentDaoImpl dbUtils = new EquipmentDaoImpl(context);
        return dbUtils.findEquipment(id);
    }

    @Override
    public List<Button> loadEquipments(Context context) {
        List<Button> list = new ArrayList<>();
        EquipmentDaoImpl equipmentUtils = new EquipmentDaoImpl(context);
        LocationDaoImpl locationUtils = new LocationDaoImpl(context);
        List<Equipment> equipments = equipmentUtils.findAll();
        if (null != equipments && 0 < equipments.size()){
            for (Equipment equipment : equipments) {
                EquipmentLocation location = locationUtils.findLocation(equipment.getId());
                list.add(loadEquipment(equipment.getName(),location));
            }
            return list;
        }
        return null;
    }

    @Override
    public Button loadEquipment(String name,  EquipmentLocation location) {
        Button button = new Button(mContext);
        button.setText(name);
        button.setId(location.getId());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(location.getWidth(),location.getHeight());
        params.leftMargin = location.getLeftMargin();
        params.topMargin = location.getTopMargin();
        button.setLayoutParams(params);
        button.setOnTouchListener(new EquipmentOnTouchListenerImpl(mContext,location.getWidth(),location.getHeight()));
        button.setOnClickListener(new EquipmentOnClickListenerImpl(mContext));
        return button;
    }

}

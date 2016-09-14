package com.sunline.qi.ui.impl;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.Location;
import com.sunline.qi.listener.EquipmentOnClickListenerImpl;
import com.sunline.qi.ui.BaseEquipmentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class EquipmentUtilsImpl implements BaseEquipmentUtils {
    private Context mContext;
    EquipmentDaoImpl dbUtils = null;
    LocationDaoImpl locationDao = null;

    public EquipmentUtilsImpl(Context context) {
        mContext = context;
        dbUtils = new EquipmentDaoImpl(context);
    }

    @Override
    public Button createEquipments(Context context, Equipment equipment, Location location) {
        Button button = new Button(context);
        button.setText(equipment.getName());
        button.setId(location.getId());
        int width = location.getWidth();
        int height = location.getHeight();
        int xAxis = location.getxAxis();
        int yAxis = location.getyAxis();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.leftMargin = xAxis;
        params.topMargin = yAxis;
        button.setLayoutParams(params);
        boolean flag = false;
        try {
            flag = dbUtils.addEquipment(equipment);
        } catch (Exception e) {
            Toast.makeText(mContext, "数据库添加失败！", Toast.LENGTH_LONG).show();
        }
        if (flag) {
            locationDao = new LocationDaoImpl(mContext);
            locationDao.addLocation(location);
            /**
             * 给创建的控制添加监听
             */
            button.setOnClickListener(new EquipmentOnClickListenerImpl());
            //button.setOnTouchListener(new EquipmentOnTouchListenerImpl(mContext, width, height));
            return button;
        }
        return null;
    }

    @Override
    public boolean deleteEquipments(Context context, Button equipment, String id) {
        equipment.setVisibility(View.GONE);
        return dbUtils.deleteEquipment(id);
    }

    @Override
    public boolean updateEquipments(Context context, Equipment equipment) {
        return dbUtils.updateEquipment(equipment);
    }

    @Override
    public Equipment findEquipments(Context context, String id) {
        return dbUtils.findEquipment(id);
    }

    @Override
    public List<Button> loadEquipments(Context context) {
        List<Button> list = new ArrayList<>();
        EquipmentDaoImpl equipmentDao = new EquipmentDaoImpl(context);
        LocationDaoImpl locationDao = new LocationDaoImpl(context);
        List<Equipment> equipments = equipmentDao.findAll();
        if (null != equipments && 0 < equipments.size()) {
            for (Equipment equipment : equipments) {
                Location location = locationDao.findLocation(equipment.getId());
//                Toast.makeText(mContext, "id = "+location.getId(), Toast.LENGTH_SHORT).show();
                list.add(loadEquipment(equipment.getName(), location));
            }
            return list;
        }
        return null;
    }

    @Override
    public Button loadEquipment(String name, Location location) {
        Button button = new Button(mContext);
        button.setText(name);
        button.setId(location.getId());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(location.getWidth(), location.getHeight());
        params.leftMargin = location.getxAxis();
        params.topMargin = location.getyAxis();
        button.setLayoutParams(params);
        //button.setOnTouchListener(new EquipmentOnTouchListenerImpl(mContext,location.getWidth(),location.getHeight()));
        button.setOnClickListener(new EquipmentOnClickListenerImpl());
        return button;
    }

    @Override
    public boolean updateLocation(Location location) {

        return false;
    }

}

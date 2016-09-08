package com.sunline.qi.utils.impl;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.EquipmentLocation;
import com.sunline.qi.utils.BaseEquipmentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public class EquipmentUtilsImpl implements BaseEquipmentUtils {

    @Override
    public Button createEquipments(Context context,Equipment equipment, int width, int height, int leftMargin,
                                   int topMargin) {
        EquipmentDaoImpl dbUtils = new EquipmentDaoImpl(context);
        Button button = new Button(context);
        button.setText(equipment.getName());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        button.setLayoutParams(params);
        dbUtils.addEquipment(equipment);
        return button;
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
        EquipmentDaoImpl dbUtils = new EquipmentDaoImpl(context);
        LocationDaoImpl locationUtils = new LocationDaoImpl(context);
        List<Equipment> equipments = dbUtils.findAll();
        for (Equipment equipment : equipments) {
            EquipmentLocation location = locationUtils.findLocation(equipment.getId());
            list.add(createEquipments(context,equipment,location.getWidth(),
                    location.getHeight(),location.getLeftMargin(),location.getTopMargin()));
        }
        return list;
    }
}

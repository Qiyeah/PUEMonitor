package com.sunline.qi.ui;

import android.content.Context;
import android.text.Layout;
import android.widget.Button;

import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.EquipmentLocation;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface BaseEquipmentUtils {
    Button createEquipments(Context context,Equipment equipment,EquipmentLocation location);
    void deleteEquipments(Context context,Button equipment, String id);
    void updateEquipments(Context context, Equipment equipment);
    Equipment findEquipments(Context context, String id);
    List<Button> loadEquipments(Context context);
    Button loadEquipment(String name,  EquipmentLocation location);
    boolean updateLocation(EquipmentLocation  location);
}
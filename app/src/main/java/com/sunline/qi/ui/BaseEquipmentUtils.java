package com.sunline.qi.ui;

import android.content.Context;
import android.widget.Button;

import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.Location;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface BaseEquipmentUtils {
    Button createEquipments(Context context,Equipment equipment,Location location);
    boolean deleteEquipments(Context context,Button equipment, String id);
    boolean updateEquipments(Context context, Equipment equipment);
    Equipment findEquipments(Context context, String id);
    List<Button> loadEquipments(Context context);
    Button loadEquipment(String name,  Location location);
    boolean updateLocation(Location location);
}

package com.sunline.qi.ui;

import android.content.Context;
import android.widget.Button;

import com.sunline.qi.entity.AS_Equipment;
import com.sunline.qi.entity.Location;

import java.util.List;

/**
 * Created by sunline on 2016/9/9.
 */
public interface BaseEquipmentUtils {
    public static final int CREATE = 0x01;
    public static final int UPDATE = 0x02;
    Button createEquipments(Context context, AS_Equipment ASEquipment, Location location);
    boolean deleteEquipments(Context context,Button equipment, String id);
    boolean updateEquipments(Context context, AS_Equipment ASEquipment);
    AS_Equipment findEquipments(Context context, String id);
    List<Button> loadEquipments(Context context);
    Button loadEquipment(String name,  Location location);
    boolean updateLocation(Location location);
}

package com.sunline.qi.db;

import com.sunline.qi.entity.EquipmentLocation;

/**
 * Created by qi on 2016/9/8.
 */
public interface LocationUtils {
    boolean addLocation(EquipmentLocation location);
    boolean deleteLocation(String foreign);
    boolean updateLocation(EquipmentLocation location);
    EquipmentLocation findLocation(String foreign);
}

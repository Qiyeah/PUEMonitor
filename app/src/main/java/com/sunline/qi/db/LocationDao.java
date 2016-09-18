package com.sunline.qi.db;

import com.sunline.qi.entity.Location;

/**
 * Created by qi on 2016/9/8.
 */
public interface LocationDao {
    boolean addLocation(Location location);
    boolean deleteLocation(String foreign);
    boolean updateLocation(Location location);
    Location findLocation(String foreign);
    Location findLocation(int rid);

    String findDeviceId(int rid);
}

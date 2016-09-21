package com.sunline.qi.db;

import com.sunline.qi.entity.AndroidInfo;

/**
 * Created by sunline on 2016/9/18.
 */
public interface InfoDao {
    boolean addInfo(AndroidInfo info);
    boolean deleteInfo(String fk);
    boolean updateInfo(AndroidInfo info);
    AndroidInfo[] findInfos(String fk);
    boolean isExists(String fk);
}

package com.sunline.qi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunline on 2016/6/2.
 */
public class RandomID {
    private DBUtils manager;
    public RandomID(DBUtils manager){
        this.manager = manager;
    }
    public int getId(){
        //manager.
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy").format(new Date()));
        for (int i = 0; i < 2 ; i++) {
            sb.append(""+(int)(Math.random() * 10));
        }
        return Integer.parseInt(sb.toString().trim());
    }
}

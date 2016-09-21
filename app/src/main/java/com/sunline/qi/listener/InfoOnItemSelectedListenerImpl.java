package com.sunline.qi.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sunline on 2016/9/19.
 */
public abstract class InfoOnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
    int position;
    public InfoOnItemSelectedListenerImpl() {

    }

    public InfoOnItemSelectedListenerImpl(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (0 == position) {
            callBack(1);
        } else if (1 == position) {
            callBack(-1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public abstract void callBack(int value);
}

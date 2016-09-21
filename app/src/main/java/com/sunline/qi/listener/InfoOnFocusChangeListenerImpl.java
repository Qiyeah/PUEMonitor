package com.sunline.qi.listener;

import android.view.View;
import android.widget.EditText;

/**
 * Created by sunline on 2016/9/21.
 */
public abstract class InfoOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
    private int position;
    public InfoOnFocusChangeListenerImpl() {
    }

    public InfoOnFocusChangeListenerImpl(int position) {
        this.position = position;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof EditText){
            EditText editText = (EditText) v;
            if (!hasFocus){
                callBack(editText.getText().toString().trim());
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public abstract void callBack(String str);
}

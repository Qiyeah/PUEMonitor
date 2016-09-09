package com.sunline.qi.listener;

import android.content.Context;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.sunline.qi.activity.R;

import java.lang.reflect.Field;

/**
 * Created by qi on 2016/9/9.
 */
public class EquipmentOnClickListenerImpl implements View.OnClickListener {
    private Context mContext;

    public EquipmentOnClickListenerImpl(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        showPopupMenu(v);
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(mContext, view);

        // menu布局
        popupMenu.inflate(R.menu.popumenu/*, popupMenu.getMenu()*/);
        // menu的item点击事件

        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(popupMenu);
            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            //
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        }

        popupMenu.show();
    }
}

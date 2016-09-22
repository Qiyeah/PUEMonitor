package com.sunline.qi.listener;

import android.content.Context;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sunline.qi.activity.R;
import com.sunline.qi.db.InfoDao;
import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.db.impl.InfoDaoImpl;
import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.dialog.InfoPopuMenu;
import com.sunline.qi.dialog.MainPopupMenu;
import com.sunline.qi.ui.BaseEquipmentUtils;
import com.sunline.qi.ui.BaseInfoUtils;

import java.lang.reflect.Field;

/**
 * Created by qi on 2016/9/9.
 */
public class EquipmentOnClickListenerImpl implements View.OnClickListener {
    private Context mContext;

    public EquipmentOnClickListenerImpl() {
    }

    public EquipmentOnClickListenerImpl(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        //if (v.getId()==R.id.)
        showPopupMenu(v);
    }

    private void showPopupMenu(final View view) {
        mContext = view.getContext();
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
        } catch (Exception e) {
            //
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LocationDaoImpl locationDao = new LocationDaoImpl(mContext);
                EquipmentDaoImpl equipmentDao = new EquipmentDaoImpl(mContext);
                InfoDao infoDao = new InfoDaoImpl(mContext);
                int rid = view.getId();
                String fk = equipmentDao.findForeignKey(rid);
                switch (item.getItemId()) {
                    case R.id.menu_setting_main:
                        //Toast.makeText(mContext, ""+rid, Toast.LENGTH_SHORT).show();
                        MainPopupMenu dialog = new MainPopupMenu(mContext, view) {
                            @Override
                            public void callBack(Button button) {

                            }
                        };
                        dialog.initDialog(BaseEquipmentUtils.EQUIPMENT_UPDATE);
                        dialog.show();
                        break;
                    case R.id.menu_setting_info:
                        InfoPopuMenu infoMenu = new InfoPopuMenu(fk, mContext);
                        boolean isExists = false;
                        isExists = infoDao.isExists(fk);
                        if (isExists) {
                            infoMenu.initDialog(BaseInfoUtils.INFO_UPDATE);
                        } else {
                            infoMenu.initDialog(BaseInfoUtils.INFO_CREATE);
                        }
                        infoMenu.show();
                        break;
                    //TODO 更新设备位置
                    case R.id.menu_setting_move:
                        view.setOnTouchListener(new EquipmentOnTouchListenerImpl());
                        break;
                    //TODO 删除设备
                    case R.id.menu_setting_drop:
                        view.setVisibility(View.GONE);
                        Toast.makeText(mContext, fk, Toast.LENGTH_SHORT).show();
                        locationDao.deleteLocation(fk);
                        equipmentDao.deleteEquipment(fk);
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }
}

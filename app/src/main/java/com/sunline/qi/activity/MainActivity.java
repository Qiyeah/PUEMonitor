package com.sunline.qi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sunline.qi.dialog.MainPopupMenu;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.ui.impl.EquipmentUtilsImpl;

import java.util.List;

/**
 * Created by qi on 2016/9/8.
 */
public class MainActivity extends Activity {
    RelativeLayout container;
    Equipment mEquipment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_config);
        super.onCreate(savedInstanceState);
        container = (RelativeLayout) findViewById(R.id.container);
        registerForContextMenu(container);
        initView();
    }
    private void initView(){
        EquipmentUtilsImpl utils = new EquipmentUtilsImpl(this);
        List<Button> list = utils.loadEquipments(this);
        if (null != list){
            for (Button button : list) {
                container.addView(button);
            }
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.content_menu_title);
        menu.add(0, 0, 0, R.string.content_menu_add);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                //Log.i(TAG,"onContextItemSelected(item)");
                initDialog();
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void initDialog(){
        MainPopupMenu dialog = new MainPopupMenu(this) {
            @Override
            public void callBack(Button button) {
                if (null != button){
                    container.addView(button);
                }
            }
        };
        dialog.showDialog(MainPopupMenu.CREATE);
    }
    private class ViewTag{

    }
}

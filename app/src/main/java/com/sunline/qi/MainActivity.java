package com.sunline.qi;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sunline.qi.activity.R;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.utils.IDUtils;
import com.sunline.qi.utils.impl.EquipmentUtilsImpl;

/**
 * Created by qi on 2016/9/8.
 */
public class MainActivity extends Activity {
    RelativeLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_config);
        super.onCreate(savedInstanceState);
        container = (RelativeLayout) findViewById(R.id.container);
        registerForContextMenu(container);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("创建设备菜单");
        menu.add(0, 0, 0, "添加设备");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                //Log.i(TAG,"onContextItemSelected(item)");
                EquipmentUtilsImpl utils = new EquipmentUtilsImpl();
                Equipment equipment = new Equipment();
                equipment.setId(IDUtils.getId(IDUtils.AC));
                equipment.setName("多路表x1");
                equipment.setRate("9600");
                equipment.setParity("0");
                equipment.setPort("COM4");
                equipment.setAddr("01");
                equipment.setDataBits("8");
                equipment.setStopBits("1");
                equipment.setDelay("1");
                equipment.setTimeOut("200");
                Button button = utils.createEquipments(this,equipment,200,100,100,100);
                container.addView(button);
                container.invalidate();
                break;
        }
        return super.onContextItemSelected(item);
    }
}

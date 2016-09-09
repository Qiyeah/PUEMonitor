package com.sunline.qi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sunline.qi.activity.R;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.utils.IDUtils;
import com.sunline.qi.utils.impl.EquipmentUtilsImpl;

import java.util.List;

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
        initView();
    }
    private void initView(){
        EquipmentUtilsImpl utils = new EquipmentUtilsImpl(this);
        List<Button> list = utils.loadEquipments(this);
        for (Button button : list) {
            container.addView(button);
        }
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
                initDialog();
                EquipmentUtilsImpl utils = new EquipmentUtilsImpl(this);
                Equipment equipment = new Equipment();
                equipment.setId(IDUtils.getId(IDUtils.AC));
                equipment.setRid(IDUtils.generateRID());
                equipment.setName("多路表x1");
                equipment.setRate("9600");
                equipment.setParity("0");
                equipment.setPort("COM4");
                equipment.setAddr("01");
                equipment.setDataBits("8");
                equipment.setStopBits("1");
                equipment.setDelay("1");
                equipment.setSwitch("1");
                equipment.setTimeOut("200");
                Button button = utils.createEquipments(this,equipment,200,100,100,100);
                container.addView(button);
                container.invalidate();
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("create equipment");
        View view = LayoutInflater.from(this).inflate(R.layout.layout_alert,null);

        builder.setView(view);
        builder.show();
    }
}

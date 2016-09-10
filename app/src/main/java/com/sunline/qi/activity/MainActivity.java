package com.sunline.qi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        for (Button button : list) {
            container.addView(button);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setInverseBackgroundForced(true);
        builder.setIcon(R.drawable.honor);
        TextView title = new TextView(this);
        title.setHeight(50);
        title.setGravity(Gravity.CENTER);
        title.setText("Android");
        title.setBackgroundColor(R.color.colorPrimary);
        builder.setCustomTitle(title);
        //builder.setTitle(R.string.content_menu_title);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_alert,null);
        builder.setView(view);

        builder.setCancelable(false);
        builder = builder.setPositiveButton(R.string.confirm, (DialogInterface dialog, int which)->{
            Toast.makeText(this, "ok button", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton(R.string.cancel, (DialogInterface dialog, int which)->{
            dialog.dismiss();
        });
        builder.show();
    }
    private class ViewTag{

    }
}

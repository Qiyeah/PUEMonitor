package com.sunline.qi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sunline.qi.activity.R;
import com.sunline.qi.dialog.MainPopupMenu;
import com.sunline.qi.entity.AS_Equipment;
import com.sunline.qi.ui.impl.EquipmentUtilsImpl;

import java.util.List;

/**
 * Created by sunline on 2016/6/7.
 */
public class ConfigFragment extends Fragment {
    RelativeLayout mContainer;
    Context mContext;
    AS_Equipment asEquipment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = null;
        if (v == null){
            v = inflater.inflate(R.layout.fragment_config,null);
        }
        mContext = getActivity();
        mContainer = (RelativeLayout) v.findViewById(R.id.config_container);
        registerForContextMenu(container);
        initView();
        return v;
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

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
    private void initView(){
        EquipmentUtilsImpl utils = new EquipmentUtilsImpl(mContext);
        List<Button> list = utils.loadEquipments(mContext);
        if (null != list){
            for (Button button : list) {
                mContainer.addView(button);
            }
        }
    }
    private void initDialog(){
        MainPopupMenu dialog = new MainPopupMenu(mContext) {
            @Override
            public void callBack(Button button) {
                if (null != button){
                    mContainer.addView(button);
                }
            }
        };
        dialog.showDialog(MainPopupMenu.CREATE);
    }
}

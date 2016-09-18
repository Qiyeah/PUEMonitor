package com.sunline.qi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sunline.qi.activity.R;
import com.sunline.qi.db.InfoDao;
import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.db.impl.InfoDaoImpl;
import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.entity.AS_EquipmentInfo;
import com.sunline.qi.tag.ACInfoTag;
import com.sunline.qi.tag.DCInfoTag;
import com.sunline.qi.ui.BaseEquipmentUtils;
import com.sunline.qi.ui.impl.EquipmentUtilsImpl;
import com.sunline.qi.utils.IDUtils;

import java.util.List;

/**
 * Created by sunline on 2016/9/18.
 */
public class InfoPopuMenu extends AlertDialog.Builder implements AdapterView.OnItemSelectedListener{

    private AS_EquipmentInfo asInfo;
    private AS_EquipmentInfo[] acInfos;
    private AS_EquipmentInfo[] dcInfos;
    private Context mContext;
    private int mRid;
    private InfoDao dao;
    private View mView;
    public InfoPopuMenu(Context context) {
        super(context);
        this.asInfo = asInfo;
        mContext = context;
    }
    public InfoPopuMenu(int id, Context context) {
        super(context);
        mRid = id;
        mContext = context;
        dao = new InfoDaoImpl(mContext);
    }

    public void showDialog(final int type,String fk) {
        //Toast.makeText(mContext,"MainPopupMenu.showDialog is run!",Toast.LENGTH_LONG).show();
        asInfo =new AS_EquipmentInfo();
        setInverseBackgroundForced(true);
        /**
         * 设置自定义图标
         */
        setIcon(R.drawable.honor);
        /**
         * 设置自定义标题
         */
        TextView title = new TextView(mContext);
        title.setHeight(50);
        title.setGravity(Gravity.CENTER);
        title.setText("Android");
        setCustomTitle(title);
        /**
         * 设置对话框背景色
         */
        title.setBackgroundColor(R.color.colorPrimary);
        if (fk.startsWith("AC")){

            mView = LayoutInflater.from(mContext).inflate(R.layout.info_ac, null);
            /**
             * 初始化对话框控件
             */
            initACTag();
            acTag.attr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else if (fk.startsWith("DC")){
            mView = LayoutInflater.from(mContext).inflate(R.layout.info_dc, null);
            /**
             * 初始化对话框控件
             */
            initDCTag();
        }

        /**
         * 设置设备类型
         */
        if (BaseEquipmentUtils.CREATE == type) {
            tag.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext,"position:"+position,Toast.LENGTH_LONG).show();
                    if (0 == position) {
                        mEquipment.setId(IDUtils.getId(IDUtils.AC));
                    } else if (1 == position) {
                        mEquipment.setId(IDUtils.getId(IDUtils.DC));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //没有用到
                }
            });
        }
        if (BaseEquipmentUtils.UPDATE == type) {
            mEquipment = equipmentDao.findEquipment(mRid);
            if (mEquipment.getSwitch().equals("1")) {
                tag.isOpen.setChecked(true);
            } else {
                tag.isOpen.setChecked(false);
            }
            if (mEquipment.getId().startsWith("AC")) {
                tag.type.setSelection(0);
            } else if (mEquipment.getId().startsWith("DC")) {
                tag.type.setSelection(1);
            }

            tag.name.setText(mEquipment.getName());
            tag.port.setText(mEquipment.getPort());
            tag.rate.setText(mEquipment.getRate());
            tag.addr.setText(mEquipment.getAddr());
            tag.timeout.setText(mEquipment.getTimeOut());
            tag.data.setText(mEquipment.getDataBits());
            tag.stop.setText(mEquipment.getStopBits());
            tag.parity.setText(mEquipment.getParity());
            tag.delayed.setText(mEquipment.getDelay());
            tag.eWidth.setText(mEquipment.getDelay());
            tag.delayed.setText(mEquipment.getDelay());

            mLocation = locationDao.findLocation(mRid);
            tag.eWidth.setText(Integer.toString(mLocation.getWidth()));
            tag.eHeight.setText(Integer.toString(mLocation.getHeight()));
            tag.xAxis.setText(Integer.toString(mLocation.getxAxis()));
            tag.yAxis.setText(Integer.toString(mLocation.getyAxis()));
        }
        /**
         * 设置自定义视图
         */
       /*
        Toast.makeText(mContext, "mView == null :"+(null == mView), Toast.LENGTH_LONG).show();*/
        setView(mView);
        /**
         * 未点击确定或取消键时，不能取消对话框
         */
        setCancelable(false);

        /**
         * 设置按键监听
         */
        setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 数据持久化操作，电脑端与Android端
                //Toast.makeText(mContext, "ok button", Toast.LENGTH_SHORT).show();
                //TODO 更新设备时的操作
                //TODO 创建设备里的操作
                String name = tag.name.getText().toString().trim();
                String port = tag.port.getText().toString().trim();
                String rate = tag.rate.getText().toString().trim();
                String addr = tag.addr.getText().toString().trim();
                String timeout = tag.timeout.getText().toString().trim();
                String data = tag.data.getText().toString().trim();
                String stop = tag.stop.getText().toString().trim();
                String parity = tag.parity.getText().toString().trim();
                String delay = tag.delayed.getText().toString().trim();

                String width = tag.eWidth.getText().toString().trim();
                String height = tag.eHeight.getText().toString().trim();
                String xAxis = tag.xAxis.getText().toString().trim();
                String yAxis = tag.yAxis.getText().toString().trim();
                /**
                 * 设置设备主参数
                 */
                mEquipment.setName(name);
                mEquipment.setPort(port);
                mEquipment.setRate(rate);
                mEquipment.setAddr(addr);
                mEquipment.setTimeOut(timeout);
                mEquipment.setDataBits(data);
                mEquipment.setStopBits(stop);
                mEquipment.setParity(parity);
                mEquipment.setDelay(delay);
                /**
                 * 设置控件定位参数。
                 */
                mLocation.setfId(mEquipment.getId());
                mLocation.setWidth(str2Integer(width));
                mLocation.setHeight(str2Integer(height));
                mLocation.setxAxis(str2Integer(xAxis));
                mLocation.setyAxis(str2Integer(yAxis));
                utils = new EquipmentUtilsImpl(mContext);
                if (CREATE == type) {
                    int rid = IDUtils.generateRID();
                    mEquipment.setRid(rid);
                    mLocation.setId(rid);
                    button = utils.createEquipments(mContext, mEquipment, mLocation);
                    System.out.println(null == button);
                    callBack(button);
                    //TODO 保存到服务器端,需要判断是否创建成功
                    /*String path = "http://192.168.1.117:8080/PseudoProgram/AddEquipmentServlet";
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doPost(path,
                            new Equipment(mEquipment.getId(),
                                    mEquipment.getName(),
                                    mEquipment.getPort(),
                                    mEquipment.getRate(),
                                    mEquipment.getAddr(),
                                    mEquipment.getTimeOut(),
                                    mEquipment.getDataBits(),
                                    mEquipment.getStopBits(),
                                    mEquipment.getParity(),
                                    mEquipment.getSwitch(),
                                    mEquipment.getDelay()));*/
                } else if (UPDATE == type) {
                    mEquipment.setRid(mRid);
                    mLocation.setId(mRid);
                    boolean flag = utils.updateEquipments(mContext, mEquipment);
                    if (flag) {
                        Toast.makeText(mContext, "设备更新成功", Toast.LENGTH_SHORT).show();
                        //TODO 更新到服务器
                        //Write code here

                    }
                }
                dialog.dismiss();
                dialog.cancel();
            }
        });
        /**
         * 设置按键监听
         */

        setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        show();
    }
    DCInfoTag dcTag;
    private void initDCTag() {
        dcTag.route1 = (TextView) mView.findViewById(R.id.dc_route1);
        dcTag.route2 = (TextView) mView.findViewById(R.id.dc_route2);
        dcTag.route3 = (TextView) mView.findViewById(R.id.dc_route3);
        dcTag.route4 = (TextView) mView.findViewById(R.id.dc_route4);
        dcTag.route5 = (TextView) mView.findViewById(R.id.dc_route5);
        dcTag.route6 = (TextView) mView.findViewById(R.id.dc_route6);
        dcTag.route7 = (TextView) mView.findViewById(R.id.dc_route7);
        dcTag.route8 = (TextView) mView.findViewById(R.id.dc_route8);
        dcTag.route9 = (TextView) mView.findViewById(R.id.dc_route9);
        dcTag.route10 = (TextView) mView.findViewById(R.id.dc_route10);
        dcTag.route11 = (TextView) mView.findViewById(R.id.dc_route11);
        dcTag.route12 = (TextView) mView.findViewById(R.id.dc_route12);
        dcTag.route13 = (TextView) mView.findViewById(R.id.dc_route13);
        dcTag.route14 = (TextView) mView.findViewById(R.id.dc_route14);
        dcTag.route15 = (TextView) mView.findViewById(R.id.dc_route15);
        dcTag.route16 = (TextView) mView.findViewById(R.id.dc_route16);
        dcTag.attr1 = (Spinner) mView.findViewById(R.id.dc_attr1);
        dcTag.attr2 = (Spinner) mView.findViewById(R.id.dc_attr2);
        dcTag.attr3 = (Spinner) mView.findViewById(R.id.dc_attr3);
        dcTag.attr4 = (Spinner) mView.findViewById(R.id.dc_attr4);
        dcTag.attr5 = (Spinner) mView.findViewById(R.id.dc_attr5);
        dcTag.attr6 = (Spinner) mView.findViewById(R.id.dc_attr6);
        dcTag.attr7 = (Spinner) mView.findViewById(R.id.dc_attr7);
        dcTag.attr8 = (Spinner) mView.findViewById(R.id.dc_attr8);
        dcTag.attr9 = (Spinner) mView.findViewById(R.id.dc_attr9);
        dcTag.attr10 = (Spinner) mView.findViewById(R.id.dc_attr10);
        dcTag.attr11 = (Spinner) mView.findViewById(R.id.dc_attr11);
        dcTag.attr12 = (Spinner) mView.findViewById(R.id.dc_attr12);
        dcTag.attr13 = (Spinner) mView.findViewById(R.id.dc_attr13);
        dcTag.attr14 = (Spinner) mView.findViewById(R.id.dc_attr14);
        dcTag.attr15 = (Spinner) mView.findViewById(R.id.dc_attr15);
        dcTag.attr16 = (Spinner) mView.findViewById(R.id.dc_attr16);
        dcTag.name1 = (EditText) mView.findViewById(R.id.dc_name1);
        dcTag.name2 = (EditText) mView.findViewById(R.id.dc_name2);
        dcTag.name3 = (EditText) mView.findViewById(R.id.dc_name3);
        dcTag.name4 = (EditText) mView.findViewById(R.id.dc_name4);
        dcTag.name5 = (EditText) mView.findViewById(R.id.dc_name5);
        dcTag.name6 = (EditText) mView.findViewById(R.id.dc_name6);
        dcTag.name7 = (EditText) mView.findViewById(R.id.dc_name7);
        dcTag.name8 = (EditText) mView.findViewById(R.id.dc_name8);
        dcTag.name9 = (EditText) mView.findViewById(R.id.dc_name9);
        dcTag.name10 = (EditText) mView.findViewById(R.id.dc_name10);
        dcTag.name11 = (EditText) mView.findViewById(R.id.dc_name11);
        dcTag.name12 = (EditText) mView.findViewById(R.id.dc_name12);
        dcTag.name13 = (EditText) mView.findViewById(R.id.dc_name13);
        dcTag.name14 = (EditText) mView.findViewById(R.id.dc_name14);
        dcTag.name15 = (EditText) mView.findViewById(R.id.dc_name15);
        dcTag.name16 = (EditText) mView.findViewById(R.id.dc_name16);
        dcTag.symbol1 = (Spinner) mView.findViewById(R.id.dc_symbol1);
        dcTag.symbol2 = (Spinner) mView.findViewById(R.id.dc_symbol2);
        dcTag.symbol3 = (Spinner) mView.findViewById(R.id.dc_symbol3);
        dcTag.symbol4 = (Spinner) mView.findViewById(R.id.dc_symbol4);
        dcTag.symbol5 = (Spinner) mView.findViewById(R.id.dc_symbol5);
        dcTag.symbol6 = (Spinner) mView.findViewById(R.id.dc_symbol6);
        dcTag.symbol7 = (Spinner) mView.findViewById(R.id.dc_symbol7);
        dcTag.symbol8 = (Spinner) mView.findViewById(R.id.dc_symbol8);
        dcTag.symbol9 = (Spinner) mView.findViewById(R.id.dc_symbol9);
        dcTag.symbol10 = (Spinner) mView.findViewById(R.id.dc_symbol10);
        dcTag.symbol11 = (Spinner) mView.findViewById(R.id.dc_symbol11);
        dcTag.symbol12 = (Spinner) mView.findViewById(R.id.dc_symbol12);
        dcTag.symbol13 = (Spinner) mView.findViewById(R.id.dc_symbol13);
        dcTag.symbol14 = (Spinner) mView.findViewById(R.id.dc_symbol14);
        dcTag.symbol15 = (Spinner) mView.findViewById(R.id.dc_symbol15);
        dcTag.symbol16 = (Spinner) mView.findViewById(R.id.dc_symbol16);
        dcTag.per1 = (EditText) mView.findViewById(R.id.dc_per1);
        dcTag.per2 = (EditText) mView.findViewById(R.id.dc_per2);
        dcTag.per3 = (EditText) mView.findViewById(R.id.dc_per3);
        dcTag.per4 = (EditText) mView.findViewById(R.id.dc_per4);
        dcTag.per5 = (EditText) mView.findViewById(R.id.dc_per5);
        dcTag.per6 = (EditText) mView.findViewById(R.id.dc_per6);
        dcTag.per7 = (EditText) mView.findViewById(R.id.dc_per7);
        dcTag.per8 = (EditText) mView.findViewById(R.id.dc_per8);
        dcTag.per9 = (EditText) mView.findViewById(R.id.dc_per9);
        dcTag.per10 = (EditText) mView.findViewById(R.id.dc_per10);
        dcTag.per11 = (EditText) mView.findViewById(R.id.dc_per11);
        dcTag.per12 = (EditText) mView.findViewById(R.id.dc_per12);
        dcTag.per13 = (EditText) mView.findViewById(R.id.dc_per13);
        dcTag.per14 = (EditText) mView.findViewById(R.id.dc_per14);
        dcTag.per15 = (EditText) mView.findViewById(R.id.dc_per15);
        dcTag.per16 = (EditText) mView.findViewById(R.id.dc_per16);
    }
    ACInfoTag acTag;
    private void initACTag() {
        acTag.route1 = (TextView) mView.findViewById(R.id.ac_route1);
        acTag.route2 = (TextView) mView.findViewById(R.id.ac_route2);
        acTag.route3 = (TextView) mView.findViewById(R.id.ac_route3);
        acTag.route4 = (TextView) mView.findViewById(R.id.ac_route4);
        acTag.route5 = (TextView) mView.findViewById(R.id.ac_route5);
        acTag.attr1 = (Spinner) mView.findViewById(R.id.ac_attr1);
        acTag.attr2 = (Spinner) mView.findViewById(R.id.ac_attr2);
        acTag.attr3 = (Spinner) mView.findViewById(R.id.ac_attr3);
        acTag.attr4 = (Spinner) mView.findViewById(R.id.ac_attr4);
        acTag.attr5 = (Spinner) mView.findViewById(R.id.ac_attr5);
        acTag.name1 = (EditText) mView.findViewById(R.id.ac_name1);
        acTag.name2 = (EditText) mView.findViewById(R.id.ac_name2);
        acTag.name3 = (EditText) mView.findViewById(R.id.ac_name3);
        acTag.name4 = (EditText) mView.findViewById(R.id.ac_name4);
        acTag.name5 = (EditText) mView.findViewById(R.id.ac_name5);
        acTag.symbol1 = (Spinner) mView.findViewById(R.id.ac_symbol1);
        acTag.symbol2 = (Spinner) mView.findViewById(R.id.ac_symbol2);
        acTag.symbol3 = (Spinner) mView.findViewById(R.id.ac_symbol3);
        acTag.symbol4 = (Spinner) mView.findViewById(R.id.ac_symbol4);
        acTag.symbol5 = (Spinner) mView.findViewById(R.id.ac_symbol5);
        acTag.per1 = (EditText) mView.findViewById(R.id.ac_per1);
        acTag.per2 = (EditText) mView.findViewById(R.id.ac_per2);
        acTag.per3 = (EditText) mView.findViewById(R.id.ac_per3);
        acTag.per4 = (EditText) mView.findViewById(R.id.ac_per4);
        acTag.per5 = (EditText) mView.findViewById(R.id.ac_per5);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

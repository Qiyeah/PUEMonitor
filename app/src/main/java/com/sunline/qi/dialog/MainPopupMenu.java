package com.sunline.qi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sunline.qi.activity.R;
import com.sunline.qi.db.impl.EquipmentDaoImpl;
import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.entity.AndroidEquipment;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.Location;
import com.sunline.qi.http.HttpUtils;
import com.sunline.qi.tag.EquipmentMainTag;
import com.sunline.qi.ui.BaseEquipmentUtils;
import com.sunline.qi.utils.IDUtils;
import com.sunline.qi.ui.impl.EquipmentUtilsImpl;

/**
 * Created by sunline on 2016/9/10.
 */
public abstract class MainPopupMenu extends AlertDialog.Builder {

    private Context mContext;
    public static final String EQUIPMENT_AC = "1";
    public static final String EQUIPMENT_DC = "2";
    private AndroidEquipment mAndroidEquipment;
    private Location mLocation;
    private View mView;
    private Button button;
    private EquipmentUtilsImpl utils;
    private int mRid;
    private String fk;
    private EquipmentDaoImpl equipmentDao;
    private LocationDaoImpl locationDao;
    EquipmentMainTag tag = null;

    public MainPopupMenu(Context context) {
        super(context);
        mContext = context;
        mAndroidEquipment = new AndroidEquipment();
        mLocation = new Location();
    }

    public MainPopupMenu( Context context,View v) {
        super(context);
        mRid = v.getId();
        mContext = context;
        button = (Button) v;
        equipmentDao = new EquipmentDaoImpl(mContext);
        locationDao = new LocationDaoImpl(mContext);
    }


    public void initDialog(final int type) {
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
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_alert, null);
        /**
         * 初始化对话框控件
         */
        initTag();
        /**
         * 设置设备状态
         */
        tag.isOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAndroidEquipment.setSwitch("1");
                } else
                    mAndroidEquipment.setSwitch("0");
            }
        });
        /**
         * 设置设备类型
         */
        if (BaseEquipmentUtils.EQUIPMENT_CREATE == type) {
            tag.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (0 == position) {
                        mAndroidEquipment.setId(IDUtils.getId(IDUtils.AC));
                    } else if (1 == position) {
                        mAndroidEquipment.setId(IDUtils.getId(IDUtils.DC));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //没有用到
                }
            });
        }
        if (BaseEquipmentUtils.EQUIPMENT_UPDATE == type) {
            mAndroidEquipment = equipmentDao.findEquipment(mRid);
            if (mAndroidEquipment.getSwitch().equals("1")) {
                tag.isOpen.setChecked(true);
            } else {
                tag.isOpen.setChecked(false);
            }
            if (mAndroidEquipment.getId().startsWith("AC")) {
                tag.type.setSelection(0);
            } else if (mAndroidEquipment.getId().startsWith("DC")) {
                tag.type.setSelection(1);
            }

            tag.name.setText(mAndroidEquipment.getName());
            tag.port.setText(mAndroidEquipment.getPort());
            tag.rate.setText(mAndroidEquipment.getRate());
            tag.addr.setText(mAndroidEquipment.getAddr());
            tag.timeout.setText(mAndroidEquipment.getTimeOut());
            tag.data.setText(mAndroidEquipment.getDataBits());
            tag.stop.setText(mAndroidEquipment.getStopBits());
            tag.parity.setText(mAndroidEquipment.getParity());
            tag.delayed.setText(mAndroidEquipment.getDelay());
            tag.eWidth.setText(mAndroidEquipment.getDelay());
            tag.delayed.setText(mAndroidEquipment.getDelay());

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
        /*System.out.println("ID:" + mEquipment.getId()
                + "\nRID:" + "" + mRid
                + "\nName:" + mEquipment.getName()
                + "\nPort:" + mEquipment.getPort()
                + "\nRate:" + mEquipment.getRate()
                + "\nAddr:" + mEquipment.getAddr()
                + "\nData:" + mEquipment.getDataBits()
                + "\nStop:" + mEquipment.getStopBits()
                + "\nState:" + mEquipment.getSwitch()
                + "\nDelay:" + mEquipment.getDelay()
                + "\nLID:" + mLocation.getId()
                + "\nWidth:" + mLocation.getWidth()
                + "\nHeight:" + mLocation.getHeight()
                + "\nxAxis:" + mLocation.getxAxis()
                + "\nyAxis:" + mLocation.getyAxis());*/
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
                mAndroidEquipment.setName(name);
                mAndroidEquipment.setPort(port);
                mAndroidEquipment.setRate(rate);
                mAndroidEquipment.setAddr(addr);
                mAndroidEquipment.setTimeOut(timeout);
                mAndroidEquipment.setDataBits(data);
                mAndroidEquipment.setStopBits(stop);
                mAndroidEquipment.setParity(parity);
                mAndroidEquipment.setDelay(delay);
                /**
                 * 设置控件定位参数。
                 */
                mLocation.setfId(mAndroidEquipment.getId());
                mLocation.setWidth(str2Integer(width));
                mLocation.setHeight(str2Integer(height));
                mLocation.setxAxis(str2Integer(xAxis));
                mLocation.setyAxis(str2Integer(yAxis));
                utils = new EquipmentUtilsImpl(mContext);
                if (BaseEquipmentUtils.EQUIPMENT_CREATE == type) {
                    int rid = IDUtils.generateRID();
                    mAndroidEquipment.setRid(rid);
                    mLocation.setId(rid);
                    button = utils.createEquipments( mAndroidEquipment, mLocation);
                    System.out.println(null == button);
                    callBack(button);
                    //TODO 保存到服务器端,需要判断是否创建成功
                    String path = "http://192.168.1.117:8080/Server/AddEquipmentServlet";
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doPost(path,
                            new Equipment(mAndroidEquipment.getId(),
                                    mAndroidEquipment.getName(),
                                    mAndroidEquipment.getPort(),
                                    mAndroidEquipment.getRate(),
                                    mAndroidEquipment.getAddr(),
                                    mAndroidEquipment.getTimeOut(),
                                    mAndroidEquipment.getDataBits(),
                                    mAndroidEquipment.getStopBits(),
                                    mAndroidEquipment.getParity(),
                                    mAndroidEquipment.getSwitch(),
                                    mAndroidEquipment.getDelay()));
                } else if (BaseEquipmentUtils.EQUIPMENT_UPDATE == type) {
                    mAndroidEquipment.setRid(mRid);
                    mLocation.setId(mRid);
                    boolean flag = utils.updateEquipments(mAndroidEquipment);
                    if (flag) {
                        button.setText(mAndroidEquipment.getName());
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
    }

    private void initTag() {
        if (null == tag) {
//            Toast.makeText(mContext, "initTag", Toast.LENGTH_SHORT).show();
            tag = new EquipmentMainTag();
            tag.isOpen = (Switch) mView.findViewById(R.id.eState);
            tag.type = (Spinner) mView.findViewById(R.id.eType);
            tag.name = (EditText) mView.findViewById(R.id.eName);
            tag.port = (EditText) mView.findViewById(R.id.port);
            tag.rate = (EditText) mView.findViewById(R.id.rate);
            tag.addr = (EditText) mView.findViewById(R.id.addr);
            tag.timeout = (EditText) mView.findViewById(R.id.timeout);
            tag.data = (EditText) mView.findViewById(R.id.data);
            tag.stop = (EditText) mView.findViewById(R.id.stop);
            tag.parity = (EditText) mView.findViewById(R.id.parity);
            tag.delayed = (EditText) mView.findViewById(R.id.delayed);
            tag.eWidth = (EditText) mView.findViewById(R.id.eWidth);
            tag.eHeight = (EditText) mView.findViewById(R.id.eHeight);
            tag.xAxis = (EditText) mView.findViewById(R.id.xAxis);
            tag.yAxis = (EditText) mView.findViewById(R.id.yAxis);
        }
        mView.setTag(tag);
        tag = (EquipmentMainTag) mView.getTag();
    }

    private int str2Integer(String str) {
        if (!str.equals("") && null != str) {
            return Integer.parseInt(str);
        }
        return 0;
    }

    private String isNull(String str) {
        if (!str.equals("") && null != str) {
            return str;
        }
        return "";
    }

    public abstract void callBack(Button button);
}
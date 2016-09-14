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
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.Location;
import com.sunline.qi.tag.EquipmentMainTag;
import com.sunline.qi.utils.IDUtils;
import com.sunline.qi.ui.impl.EquipmentUtilsImpl;

/**
 * Created by sunline on 2016/9/10.
 */
public abstract class MainPopupMenu extends AlertDialog.Builder {
    public static final int CREATE = 0x01;
    public static final int UPDATE = 0x02;
    private Context mContext;
    public static final String EQUIPMENT_AC = "1";
    public static final String EQUIPMENT_DC = "2";
    private Equipment mEquipment;
    private Location mLocation;
    private View mView;
    private Button button;
    private EquipmentUtilsImpl utils;
    private int rid;
    private EquipmentDaoImpl equipmentDao;
    private LocationDaoImpl locationDao;
    EquipmentMainTag tag = null;

    public MainPopupMenu(Context context) {
        super(context);
        mContext = context;
        mEquipment = new Equipment();
        mLocation = new Location();
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_alert, null);
    }

    public MainPopupMenu(int id, Context context) {
        super(context);
        mEquipment = new Equipment();
        mLocation = new Location();
        rid = id;
        mContext = context;
        equipmentDao = new EquipmentDaoImpl(mContext);
        locationDao = new LocationDaoImpl(mContext);
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_alert, null);
    }


    public void showDialog(final int type) {
        //Toast.makeText(mContext,"MainPopupMenu.showDialog is run!",Toast.LENGTH_LONG).show();
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
                    mEquipment.setSwitch("1");
                } else
                    mEquipment.setSwitch("0");
            }
        });
        /**
         * 设置设备类型
         */
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
        if (UPDATE == type) {
            mEquipment = equipmentDao.findEquipment(rid);
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

            mLocation = locationDao.findLocation(rid);
            tag.eWidth.setText(Integer.toString(mLocation.getWidth()));
            tag.eHeight.setText(Integer.toString(mLocation.getHeight()));
            tag.xAxis.setText(Integer.toString(mLocation.getxAxis()));
            tag.yAxis.setText(Integer.toString(mLocation.getyAxis()));
        }
        /**
         * 设置自定义视图
         */
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

                int rid = IDUtils.generateRID();
                /**
                 * 设置设备主参数
                 */
                mEquipment.setRid(rid);
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
                mLocation.setId(rid);
                mLocation.setfId(mEquipment.getId());
                mLocation.setWidth(str2Integer(width));
                mLocation.setHeight(str2Integer(height));
                mLocation.setxAxis(str2Integer(xAxis));
                mLocation.setyAxis(str2Integer(yAxis));
                utils = new EquipmentUtilsImpl(mContext);
               if (CREATE == type){
                   button = utils.createEquipments(mContext, mEquipment, mLocation);
                   callBack(button);
               }else if (UPDATE == type){
                   //Toast.makeText(mContext, ""+(null == mEquipment), Toast.LENGTH_SHORT).show();
                   boolean flag =  utils.updateEquipments(mContext, mEquipment);
                   if (flag){
                       Toast.makeText(mContext, "设备更新成功", Toast.LENGTH_SHORT).show();
                   }
               }

                /*System.out.println("ID:" + mEquipment.getId()
                        + "\nRID:" + "" + mEquipment.getRid()
                        + "\nName" + mEquipment.getName()
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
                        + "\nyAxis:" + mLocation.getyAxis());
                Toast.makeText(mContext, "ID:" + mEquipment.getId()
                        + "\nRID:" + "" + mEquipment.getRid()
                        + "\nName" + mEquipment.getName()
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
                        + "\nyAxis:" + mLocation.getyAxis()
                        , Toast.LENGTH_LONG).show();*/
            }
        });
        /**
         * 设置按键监听
         */
        setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        show();
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

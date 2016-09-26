package com.sunline.qi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunline.qi.activity.R;
import com.sunline.qi.db.InfoDao;
import com.sunline.qi.db.impl.InfoDaoImpl;
import com.sunline.qi.entity.EquipmentInfo;
import com.sunline.qi.entity.InfoList;
import com.sunline.qi.http.HttpUtils;
import com.sunline.qi.listener.InfoOnFocusChangeListenerImpl;
import com.sunline.qi.listener.InfoOnItemSelectedListenerImpl;
import com.sunline.qi.ui.BaseInfoUtils;
import com.sunline.qi.ui.impl.InfoUtilsImpl;
import com.sunline.qi.utils.IDUtils;


/**
 * Created by sunline on 2016/9/18.
 */
public class InfoPopuMenu extends AlertDialog.Builder {

    private EquipmentInfo[] infos;
    private Context mContext;
    private InfoDao dao;
    private View mView;
    private String equipmentID;
    private BaseInfoUtils utils;
    private int mRid;
    private Gson gson;
    private int type;
    public InfoPopuMenu(int rid, Context context) {
        super(context);
        mRid = rid;
        mContext = context;
        dao = new InfoDaoImpl(mContext);
        utils = new InfoUtilsImpl(mContext);
    }


    public InfoPopuMenu(String fk, Context context,int type) {
        super(context);
        equipmentID = fk;
        this.type = type;
        mContext = context;
        dao = new InfoDaoImpl(mContext);
        utils = new InfoUtilsImpl(mContext);
    }

    public void initDialog() {
        setInverseBackgroundForced(true);
        /**
         * 设置自定义图标
         */
        setIcon(R.drawable.honor);
        /**
         * 设置自定义标题
         */
        View title = LayoutInflater.from(mContext).inflate(R.layout.popu_title, null);
        setCustomTitle(title);

        /**
         * 设置对话框背景色
         */
        title.setBackgroundColor(R.color.colorPrimary);
        //TODO 加载菜单视图
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_info_menu, null);
        ListView menu = (ListView) mView.findViewById(R.id.list_info_menu);
        InfoAdapter adapter = null;
        if (BaseInfoUtils.INFO_UPDATE == type) {
            infos = utils.findInfos(equipmentID);
//            System.out.println("infos is exists");
//            System.out.println("--------------------------------------from local database--------------------------------------");
//            printInfos(infos);
//            System.out.println("--------------------------------------this is the end line-------------------------------------");
        }else if (BaseInfoUtils.INFO_CREATE == type){
            infos = new EquipmentInfo[0];
        }
        if (equipmentID.startsWith("AC")) {
            adapter = new InfoAdapter(5);
        } else if (equipmentID.startsWith("DC")) {
            adapter = new InfoAdapter(16);
        }
        menu.setAdapter(adapter);

        setView(mView);
        /**
         * 未点击确定或取消键时，不能取消对话框
         */
        setCancelable(false);

        /**
         * 设置确定对话框按键监听
         */
        setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //printInfos();
                //TODO 添加配置文件到本地Sqlite数据库
                String servlet = "AddEquipmentInfoServlet";
                HttpUtils httpUtils = new HttpUtils();
                if (BaseInfoUtils.INFO_CREATE == type) {
                    //添加到本地数据库
                    utils.addInfos(infos);
                    //打印到控制台
                    //printInfos(infos);
                    //todo add to server
                    //write code here
                    System.out.println("servlet = "+servlet);
                    httpUtils.doPost(servlet,new InfoList(infos));
                } else if (BaseInfoUtils.INFO_UPDATE == type) {
                    System.out.println("update infos ");
                    utils.updateInfos(infos);
                    //打印到控制台
                    //printInfos(infos);
                    //todo update to server
                    //write code here
                    httpUtils.doPost(servlet,new InfoList(infos));
                }
                dialog.dismiss();
                dialog.cancel();
            }
        });
        /**
         * 设置取消对话框按键监听
         */
        setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }




    private class InfoTag {
        public TextView route;
        public EditText name;
        public EditText totalPer;
        public EditText itPer;
        public Spinner totalSymbol;
        public Spinner itSymbol;
    }

    private class InfoAdapter extends BaseAdapter {
        private int count;

        public InfoAdapter(int count) {
            this.count = count;
            if (0 == infos.length) {
                infos = new EquipmentInfo[count];
                for (int i = 0; i < count; i++) {
                    infos[i] = new EquipmentInfo();
                }
            }
        }

        @Override
        public int getCount() {
            return infos.length;
        }

        @Override
        public Object getItem(int position) {
            return infos[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            InfoTag tag = null;
            if (null == view) {
                view = LayoutInflater.from(mContext).inflate(R.layout.info_menu_item, null);
                tag = new InfoTag();
                tag.route = (TextView) view.findViewById(R.id.route);
                tag.name = (EditText) view.findViewById(R.id.routeName);
                tag.totalSymbol = (Spinner) view.findViewById(R.id.totalSymbol);
                tag.itSymbol = (Spinner) view.findViewById(R.id.itSymbol);
                tag.totalPer = (EditText) view.findViewById(R.id.totalPer);
                tag.itPer = (EditText) view.findViewById(R.id.itPer);
                view.setTag(tag);
            }
            tag = (InfoTag) view.getTag();
            //TODO 设置打开菜单时的显示结果
            /*************************************************************************/

            tag.route.setText(Integer.toString(position + 1));

            tag.name.setText(infos[position].getRouteName());
            if (-1 == infos[position].getTotalSymbol()) {
                tag.totalSymbol.setSelection(1);
            }
            if (-1 == infos[position].getITSymbol()) {
                tag.itSymbol.setSelection(1);
            }
            if (0 != infos[position].getTotalPer()) {
                tag.totalPer.setText(Integer.toString(infos[position].getTotalPer()));
            }
            if (0 != infos[position].getITPer()) {
                tag.itPer.setText(Integer.toString(infos[position].getITPer()));
            }
            /*************************************************************************/
            /**
             * 获取文本框默认值
             */
            infos[position].setRouteName(tag.name.getText().toString().trim());
            infos[position].setTotalPer(Integer.parseInt(tag.totalPer.getText().toString().trim()));
            infos[position].setITPer(Integer.parseInt(tag.itPer.getText().toString().trim()));

            tag.name.setOnFocusChangeListener(new InfoOnFocusChangeListenerImpl(position) {
                @Override
                public void callBack(String str) {
                    infos[getPosition()].setRouteName(str);
                }
            });
            tag.totalPer.setOnFocusChangeListener(new InfoOnFocusChangeListenerImpl(position) {
                @Override
                public void callBack(String str) {
                    infos[getPosition()].setTotalPer(Integer.parseInt(str));
                }
            });
            tag.itPer.setOnFocusChangeListener(new InfoOnFocusChangeListenerImpl(position) {
                @Override
                public void callBack(String str) {
                    infos[getPosition()].setITPer(Integer.parseInt(str));
                }
            });

            int routeInt = Integer.parseInt(tag.route.getText().toString().trim());

            tag.totalSymbol.setOnItemSelectedListener(new InfoOnItemSelectedListenerImpl(position) {
                @Override
                public void callBack(int value) {
                    infos[getPosition()].setTotalSymbol(value);
                    //Toast.makeText(mContext, "inInfos[" + position + "] = " + infos[position].getTotalSymbol(), Toast.LENGTH_LONG).show();
                }
            });
            tag.itSymbol.setOnItemSelectedListener(new InfoOnItemSelectedListenerImpl(position) {
                @Override
                public void callBack(int value) {
                    infos[getPosition()].setITSymbol(value);
                }
            });

            if (BaseInfoUtils.INFO_CREATE == type){
                infos[position].setId(IDUtils.getId(IDUtils.INFO));
            }
            infos[position].setfId(equipmentID);
            infos[position].setRoute(routeInt);
            return view;
        }
    }

    private void printInfos(EquipmentInfo[] infos) {
        System.out.println("**************************结果集******************************");
        for (int i = 0; i < infos.length; i++) {
            printInfo(infos[i]);
            System.out.println();
        }
    }

    public void printInfo(EquipmentInfo info){

        System.out.println("id = "+info.getId());
        System.out.println("fk = "+info.getfId());
        System.out.println("route = "+info.getRoute());
        System.out.println("name = "+info.getRouteName());
        System.out.println("total_symbol = "+info.getTotalSymbol());
        System.out.println("total_per = "+info.getTotalPer());
        System.out.println("it_symbol = "+info.getITSymbol());
        System.out.println("it_per = "+info.getITPer());

    }
}

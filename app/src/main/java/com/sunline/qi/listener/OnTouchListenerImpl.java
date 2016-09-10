package com.sunline.qi.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunline.qi.utils.ScreenUtils;

import java.util.Calendar;

/**
 * Created by sunline on 2016/6/2.
 */
public class OnTouchListenerImpl implements View.OnTouchListener {
    private static final String TAG = "OnTouchListenerImpl";
    private long start = 0;
    private long stop = 0;
    private int tempX;
    private int tempY;
    private RelativeLayout mLayout;
    private int mId;
    private DeviceManager manager;
    private Context mContext;
    private boolean isDelete = false;
    private boolean isOpen = true;
    private LayoutInflater mInflater;
    private DeviceTag tag = null;
    private int btnId = 0;
    private int mScreenWidth,mScreenHeight,width,height;
    private ScreenUtils mScreenUtils ;
    private int rows = 10;
    private int cols = 15;
    private int leftMargin,topMargin;
    public OnTouchListenerImpl(LayoutInflater inflater, Context context,
                               RelativeLayout layout, int id) {
        mLayout = layout;
        mContext = context;
        mInflater = inflater;
        mId = id;
        this.manager = manager;
        mScreenUtils = new ScreenUtils(context);

        mScreenWidth = mScreenUtils.getScreenWidth();
        mScreenHeight = mScreenUtils.getScreenHeight();
        width = mScreenWidth/cols;
        height = mScreenHeight/rows;
    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                .getLayoutParams();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = Calendar.getInstance().getTimeInMillis();
                tempX = x - layoutParams.leftMargin;
                tempY = y - layoutParams.topMargin;

                break;
            case MotionEvent.ACTION_UP:
                stop = Calendar.getInstance().getTimeInMillis();

                if (1200 <= (stop - start)) {
                    btnId = v.getId();
                    //初始化设备参数列表

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("设备编辑器");

                    builder.setView(view);
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isDelete) {
                                manager.update("delete from Device where _id = ?", btnId);
                                manager.update("delete from Channels where _id = ? ", btnId);
                                v.setVisibility(View.GONE);
                            }
                            //TODO 更新通道别名
                            String[] vChannelNames = new String[]{tag.mItems.get(0).getText().toString().trim(),
                                    tag.mItems.get(1).getText().toString().trim(),
                                    tag.mItems.get(2).getText().toString().trim(),
                                    tag.mItems.get(3).getText().toString().trim(),
                                    tag.mItems.get(4).getText().toString().trim(),
                            };
                            Channels channels = new Channels();
                            channels.setId(v.getId());
                            channels.setShuntNames(vChannelNames);
                            manager.update("update Channels set device_shuntName1 = ? ,device_shuntName2 = ? ,device_shuntName3 = ? ,device_shuntName4 = ? ," +
                                    "device_shuntName5 = ? where _id = ?", channels);
                            //TODO 更新设备明细
                            MainConfig config = new MainConfig();
                            config.setId(v.getId());
                            config.setState(isOpen);
                            for (int i = 0; i < tag.mSpinners.size(); i++) {
                                states.add(tag.mSpinners.get(i).getSelectedItemPosition());
                            }
                            config.setShuntStates(states);
                            manager.update("update Device set device_state = ? ,device_channel1 = ?," +
                                    "device_channel2 = ?," +
                                    "device_channel3 = ?," +
                                    "device_channel4 = ?," +
                                    "device_channel5 = ?" +
                                    "where _id = ? ", config);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
                *
                 * 更新配置文件中设备的坐标数据


                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                tempX = x - layoutParams.leftMargin;
                tempY = y - layoutParams.topMargin;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                leftMargin = x - tempX;
                topMargin = y - tempY;
                if (leftMargin < 0) {
                    leftMargin = 0;
                }
                if (topMargin < 0) {
                    topMargin = 0;
                }

                int top = topMargin/height;
                int left = leftMargin/width;
                layoutParams.topMargin = top*height;
                layoutParams.leftMargin = left*width;
                v.setLayoutParams(layoutParams);
                break;
        }

        mLayout.invalidate();
        return false;
    }



}

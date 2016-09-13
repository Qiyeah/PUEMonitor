package com.sunline.qi.listener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.sunline.qi.utils.ScreenUtils;

import java.util.Calendar;

/**
 * Created by sunline on 2016/6/2.
 */
public class EquipmentOnTouchListenerImpl implements View.OnTouchListener {
    private static final String TAG = "";
    private long start = 0;
    private long stop = 0;
    private int tempX;
    private int tempY;
    private RelativeLayout mLayout;
    private int mId;
    private Context mContext;
    private boolean isDelete = false;
    private boolean isOpen = true;
    private LayoutInflater mInflater;
    private int btnId = 0;
    private int mScreenWidth,mScreenHeight, mWidth, mHeight;
    private ScreenUtils mScreenUtils ;
    private int rows = 10;
    private int cols = 15;
    private int leftMargin,topMargin;


    public EquipmentOnTouchListenerImpl(Context context,int width,int height) {
        mContext = context;
        mScreenUtils = new ScreenUtils(context);
        mScreenWidth = mScreenUtils.getScreenWidth();
        mScreenHeight = mScreenUtils.getScreenHeight();
        mWidth = width;
        mHeight = height;
    }

    public EquipmentOnTouchListenerImpl(LayoutInflater inflater, Context context,
                                        RelativeLayout layout, int id) {
        mLayout = layout;
        mContext = context;
        mInflater = inflater;
        mId = id;

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

                int top = topMargin/ mHeight;
                int left = leftMargin/ mWidth;
                topMargin = top* mHeight;
                leftMargin = left* mWidth;
                if (leftMargin+v.getWidth() > mScreenWidth){
                    leftMargin = mScreenWidth- mWidth;
                }
                if (topMargin + v.getHeight()>mScreenHeight){
                    topMargin = mScreenHeight - mHeight;
                }
                layoutParams.topMargin = topMargin;
                layoutParams.leftMargin = leftMargin;
                v.setLayoutParams(layoutParams);
                break;
        }

        //mLayout.invalidate();
        return false;
    }



}
/*if (1200 <= (stop - start)) {
                    btnId = v.getId();
                    //初始化设备参数列表

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("设备编辑器");
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    View view  = inflater.inflate(R.layout.support_simple_spinner_dropdown_item,null,false);
                    builder.setView(view);
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }*/
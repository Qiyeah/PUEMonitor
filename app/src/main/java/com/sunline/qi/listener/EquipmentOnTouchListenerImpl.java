package com.sunline.qi.listener;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunline.qi.db.impl.LocationDaoImpl;
import com.sunline.qi.entity.Location;
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
    private Context mContext;
    private int mScreenWidth,mScreenHeight, mWidth, mHeight;
    private ScreenUtils mScreenUtils ;
    private int leftMargin,topMargin;
    private Location location;

    public EquipmentOnTouchListenerImpl() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mContext = v.getContext();
        mScreenUtils = new ScreenUtils(mContext);
        mScreenWidth = mScreenUtils.getScreenWidth();
        mScreenHeight = mScreenUtils.getScreenHeight()-50;
        mWidth = v.getWidth();
        mHeight = v.getHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                .getLayoutParams();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        location = new Location();
        location.setId(v.getId());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = Calendar.getInstance().getTimeInMillis();
                tempX = x - layoutParams.leftMargin;
                tempY = y - layoutParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                stop = Calendar.getInstance().getTimeInMillis();
                location.setId(v.getId());
                location.setxAxis(leftMargin);
                location.setyAxis(topMargin);
                LocationDaoImpl dao = new LocationDaoImpl(mContext);
                Toast.makeText(mContext, "id = "+location.getId()+" xAxis = "+location.getxAxis()+" yAxis = "+location.getyAxis(), Toast.LENGTH_SHORT).show();
                boolean flag = dao.updateLocation(location);
                if (flag){
                    Toast.makeText(mContext, "设备位置更新成功", Toast.LENGTH_SHORT).show();
                }
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

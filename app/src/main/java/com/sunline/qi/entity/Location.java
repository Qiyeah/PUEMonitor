package com.sunline.qi.entity;

/**
 * Created by qi on 2016/9/8.
 */
public class Location {
    private int mId ;
    private String fId;
    private int mWidth = 0;
    private int mHeight = 0;
    private int xAxis = 0;
    private int yAxis = 0;

    public Location() {
    }

    public Location(String fId, int width, int height, int xAxis, int yAxis) {
        this.fId = fId;
        mWidth = width;
        mHeight = height;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public Location(int id, String fId, int width, int height, int xAxis, int yAxis) {
        mId = id;
        this.fId = fId;
        mWidth = width;
        mHeight = height;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getxAxis() {
        return xAxis;
    }

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }
}

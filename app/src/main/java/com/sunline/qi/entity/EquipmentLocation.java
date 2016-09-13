package com.sunline.qi.entity;

/**
 * Created by qi on 2016/9/8.
 */
public class EquipmentLocation {
    private int mId ;
    private String fId;
    private int mWidth = 0;
    private int mHeight = 0;
    private int leftMargin = 0;
    private int topMargin = 0;

    public EquipmentLocation() {
    }

    public EquipmentLocation(String fId, int width, int height, int leftMargin, int topMargin) {
        this.fId = fId;
        mWidth = width;
        mHeight = height;
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
    }

    public EquipmentLocation(int id, String fId, int width, int height, int leftMargin, int topMargin) {
        mId = id;
        this.fId = fId;
        mWidth = width;
        mHeight = height;
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
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

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }
}

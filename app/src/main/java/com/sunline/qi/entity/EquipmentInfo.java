package com.sunline.qi.entity;

/**
 * Created by sunline on 2016/8/22.
 */
public class EquipmentInfo {
    private String mId;
    private int mRoute;
    private String mRouteName;
    private int mRouteAttr;
    private String fId;
    private int mPer;
    private int mSymbol;

    public EquipmentInfo() {
    }

    public EquipmentInfo(String id, int route, String routeName, String fId) {
        mId = id;
        mRoute = route;
        mRouteName = routeName;
        this.fId = fId;
    }

    public EquipmentInfo(String id, int route, String routeName, int routeAttr, String fId, int per, int symbol) {
        mId = id;
        mRoute = route;
        mRouteName = routeName;
        mRouteAttr = routeAttr;
        this.fId = fId;
        mPer = per;
        mSymbol = symbol;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getRoute() {
        return mRoute;
    }

    public void setRoute(int route) {
        mRoute = route;
    }

    public String getRouteName() {
        return mRouteName;
    }

    public void setRouteName(String routeName) {
        mRouteName = routeName;
    }

    public int getRouteAttr() {
        return mRouteAttr;
    }

    public void setRouteAttr(int routeAttr) {
        mRouteAttr = routeAttr;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public int getPer() {
        return mPer;
    }

    public void setPer(int per) {
        mPer = per;
    }

    public int getSymbol() {
        return mSymbol;
    }

    public void setSymbol(int symbol) {
        mSymbol = symbol;
    }
}

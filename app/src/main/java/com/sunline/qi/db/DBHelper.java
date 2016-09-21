package com.sunline.qi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


/**
 * Created by sunline on 2016/6/2.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "sunline.db";
    private static final int VERSION = 1;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mDatabase = getWritableDatabase();
        mContext = context;
    }

    /**
     * 数据库第一次创建时被调用，仅调用一次。功能是用来建表
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Toast.makeText(mContext,"Sqlite onCreate is run !",Toast.LENGTH_LONG).show();
        System.out.println("Sqlite onCreate is run !");
        String sql = "create table Equipment( " +
                "_id CHAR(32)  NULL," +
                "rid integer  NULL," +
                "name CHAR(32)  NULL," +
                "port CHAR(10)  NULL," +
                "rate CHAR(6)  NULL," +
                "addr CHAR(3)  NULL," +
                "timeout CHAR(6)  NULL DEFAULT '200'," +
                "data CHAR(2)  NULL DEFAULT '8'," +
                "stop CHAR(2)  NULL DEFAULT '1'," +
                "parity CHAR(2)  NULL DEFAULT '0'," +
                "switch CHAR(1)  NULL DEFAULT '1'," +
                "delayed CHAR(2)  NULL DEFAULT '0'," +
                "dt DATETIME  NULL DEFAULT (datetime('now','localtime'))," +
                "PRIMARY KEY (_id) " +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE EquipmentInfo (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NOT NULL," +
                "route SMALLINT NOT NULL," +
                "name char(32) NOT NULL," +
                "total_symbol SMALLINT NOT NULL DEFAULT 1," +
                "total_per SMALLINT NOT NULL DEFAULT 100," +
                "it_symbol SMALLINT NOT NULL DEFAULT 1," +
                "it_per SMALLINT NOT NULL DEFAULT 100," +
                "dt DATETIME NOT NULL DEFAULT (datetime('now','localtime'))," +
                "PRIMARY KEY (_id) " +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE Location (" +
                "_id integer NOT NULL," +
                "fk CHAR(32) NOT NULL," +
                "width integer NOT  NULL," +
                "height integer NOT  NULL," +
                "leftMargin integer NOT  NULL," +
                "topMargin integer NOT  NULL," +
                "dt DATETIME NOT NULL DEFAULT (datetime('now','localtime'))," +
                "PRIMARY KEY (_id) " +
                ")";
        db.execSQL(sql);
    }

    /**
     * 版本更新时才调用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * 通道别名,设备别名（暂时未修改）
         */
        String sql = "";

        db.execSQL(sql);
    }

    /**
     * \
     * 执行增、删、改操作
     *
     * @param sql
     * @param params
     * @return
     */
    public boolean update(String sql, Object... params) {
        boolean flag = false;
        try {
            mDatabase.execSQL(sql, params);
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 执行查询操作
     *
     * @param sql
     * @param selectionArs
     * @return
     */
    public Cursor query(String sql, String... selectionArs) {
        return mDatabase.rawQuery(sql, selectionArs);
    }
}

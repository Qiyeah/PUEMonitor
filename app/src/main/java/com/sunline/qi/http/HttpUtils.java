package com.sunline.qi.http;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sunline.qi.entity.Equipment;
import com.sunline.qi.entity.InfoList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by qi on 2016/9/17.
 */
public class HttpUtils {
    private String separator = File.separator;
    private String address = "192.168.1.135";
    private String path;
    private String servlet;

    URL url = null;
    HttpURLConnection conn = null;
    OutputStream os = null;
    InputStream is = null;
    Context mContext;

    public HttpUtils() {

    }

    public HttpUtils(Context context) {
        mContext = context;
    }
    int count = 0 ;


    public void sendMessage(String servlet, String json) {
        new Thread(new HttpThread(servlet,json)).start();
    }

    public String parse2JSON(Object obj) {
        Gson gson = new Gson();
        if (obj instanceof Equipment) {
            System.out.println("obj instanceof Equipment");
            return gson.toJson(obj, Equipment.class);
        } else if (obj instanceof InfoList) {
            System.out.println("obj instanceof InfoList");
            return gson.toJson(obj, InfoList.class);
        }
        return "";
    }
    public String parse2JSON(Equipment equipment){
        Gson gson = new Gson();
        return gson.toJson(equipment, Equipment.class);
    }

    public String parse2JSON(InfoList list) {
        Gson gson = new Gson();
        String json = gson.toJson(list,InfoList.class);
        System.out.println("parse2JSON -->>"+json);
        return json;
    }

    public void doPost(String servlet, Equipment equipment) {
        //对象转JSON
        String json = parse2JSON(equipment);
        System.out.println("doPost json = "+json);
        //向服务器发送请求
        sendMessage(servlet, json);
        //关闭网络连接
        closeConn();
    }
    public void doPost(String servlet, InfoList list) {
        //对象转JSON
        String json = parse2JSON(list);
        System.out.println("json = " + json);
        //向服务器发送请求
        sendMessage(servlet, json);
        //关闭网络连接
        closeConn();
    }
    public void doPost(String servlet, String fk) {
        //向服务器发送请求
        sendMessage(servlet, fk);
        //关闭网络连接
        closeConn();
    }
    public void closeConn() {
        try {
            if (null != is) {
                is.close();
                is = null;
            }
            if (null != os) {
                os.close();
                os = null;
            }
            if (null != conn) {
                conn.disconnect();
                conn = null;
            }
            if (null != url) {
                url = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServlet() {
        return servlet;
    }

    public void setServlet(String servlet) {
        this.servlet = servlet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private class HttpThread implements Runnable {
        String json;
        String servlet;
        String path;
        public HttpThread(String servlet,String json) {
            this.json = json;
            this.servlet = servlet;
            System.out.println(servlet);
            path = "http://"+address+":8080/Server/"+servlet;
        }

        @Override
        public void run() {
            URL url = null;
            try {
                System.out.println(path);
                url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(1000 * 5);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-length", String.valueOf(json.getBytes().length));
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                System.out.println("json = " + json);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("网络操作成功！");
                }else {
                    System.out.println("response code = "+conn.getResponseCode());
                    count++;
                    if (5 > count){
                        run();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

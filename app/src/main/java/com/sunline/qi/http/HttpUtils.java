package com.sunline.qi.http;

import com.google.gson.Gson;
import com.sunline.qi.entity.Equipment;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by qi on 2016/9/17.
 */
public class HttpUtils {
    public void doPost(final String path, Equipment equipment) {
        Gson gson = new Gson();
        final String json = gson.toJson(equipment, Equipment.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(1000 * 5);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-length", String.valueOf(json.getBytes().length));
                    OutputStream os = conn.getOutputStream();
                    os.write(json.getBytes());
                    System.out.println("json = "+json);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("网络操作成功！");
                    }else {
                        System.out.println("response code = "+conn.getResponseCode());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

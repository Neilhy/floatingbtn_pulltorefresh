package com.example.neilhy.floatingbuttontest.NetWork;

import com.example.neilhy.floatingbuttontest.PDM.UserPDM;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by NeilHY on 2016/4/22.
 */
public class HttpUtil  {

    public static void sendHttpRequest(final String address,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8*1000);
                    connection.setReadTimeout(8*1000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in=connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    if (listener != null) {
                        //回调onFinish方法
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError方法
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void getJsonArrayByHttp(final String address, final UserPDM user, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                JSONObject object;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8*1000);
                    connection.setReadTimeout(8*1000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

                    InputStream in=connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    if (listener != null) {
                        //回调onFinish方法
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError方法
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static String toUTFString(String str) {
        //转换字符
        String text="";
        //判断要转码的字符串是否有效
        if (str != null & !"".equals(str)) {
            try {
                //将字符串进行编码处理
                text = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //返回后的字符串
        return text;
    }

}

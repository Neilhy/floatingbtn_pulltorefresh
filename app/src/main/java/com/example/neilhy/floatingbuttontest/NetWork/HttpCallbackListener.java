package com.example.neilhy.floatingbuttontest.NetWork;

/**
 * Created by NeilHY on 2016/4/22.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}

package com.example.neilhy.floatingbuttontest.PullToRefresh;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neilhy.floatingbuttontest.R;
import com.example.neilhy.pulltorefresh_lib.PtrFrameLayout;
import com.example.neilhy.pulltorefresh_lib.PtrHandler;
import com.example.neilhy.pulltorefresh_lib.header.StoreHouseHeader;

import java.util.Calendar;
import java.util.Date;

//import in.srain.cube.util.LocalDisplay;

/**
 * Created by NeilHY on 2016/4/18.
 */
public class Ptr_String_frag extends Fragment {

    String[] mStringList={"WeMark Sun.","WeMark Mon.","WeMark Tues.","WeMark Wed.","WeMark Thur.","WeMark Fri.","WeMark Sat."};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_marks_list_string, container, false);

        final PtrFrameLayout frame = (PtrFrameLayout) view.findViewById(R.id.FrameInStrFrag);

        StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setPadding(0,25,0,0);
//        header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);
        /**
         * using a string, support: A-Z 0-9 - .
         * you can add more letters by {@link com.example.neilhy.pulltorefresh_lib.header.StoreHousePath#addChar}
         */
        header.initWithString(getWeekOfDay());//获得当前星期的字符串

        frame.setDurationToCloseHeader(3000);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);

        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.autoRefresh(false);
            }
        },100);

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 2000);
            }
        });
        return view;
    }

    private String getWeekOfDay(){
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        int datOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (datOfWeek < 0) {
            datOfWeek=0;
        }
        return mStringList[datOfWeek];
    }
}

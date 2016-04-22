package com.example.neilhy.floatingbuttontest.PullToRefresh;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neilhy.floatingbuttontest.R;
import com.example.neilhy.floatingbuttontest.header.RentalsSunHeaderView;
import com.example.neilhy.pulltorefresh_lib.PtrFrameLayout;
import com.example.neilhy.pulltorefresh_lib.PtrHandler;

//import in.srain.cube.util.LocalDisplay;

/**
 * Created by NeilHY on 2016/4/18.
 */
public class Ptr_Sun_frag extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_marks_list, container, false);

        final PtrFrameLayout frame = (PtrFrameLayout) view.findViewById(R.id.FrameInSunFrag);

        //header
        RentalsSunHeaderView header = new RentalsSunHeaderView(getActivity());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0,20,0,50);
//        header.setPadding(0, LocalDisplay.dp2px(15),0,LocalDisplay.dp2px(10));
        header.setUp(frame);

        frame.setLoadingMinTime(1000);
        frame.setDurationToCloseHeader(1500);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.autoRefresh(true);
            }
        }, 100);

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
                },1500);
            }
        });
        return view;
    }
}

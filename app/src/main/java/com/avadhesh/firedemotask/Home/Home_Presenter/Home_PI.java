package com.avadhesh.firedemotask.Home.Home_Presenter;

import android.content.Context;

import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.Adapter.KeyRef;
import com.avadhesh.firedemotask.Home.HomeView;
import com.avadhesh.firedemotask.Home.Home_Model.Home_MV;


import java.util.ArrayList;
import java.util.List;

public class Home_PI implements Home_PV, Home_MV.OnFinishedListener {


    private HomeView chatView;
    private Home_MV cl_mv;
    private Context context;

    public Home_PI(HomeView chatView, Home_MV cl_mv, Context context) {
        this.chatView = chatView;
        this.cl_mv = cl_mv;
        this.context = context;
    }

    @Override
    public void call_Feed_List(ArrayList<EventData> feedArrayList, List<KeyRef> mKeys, String currentDate) {
        cl_mv.get_data(this, feedArrayList, mKeys, currentDate);
    }


    @Override
    public void onSuccessFeedList(ArrayList<EventData> feedArrayList, List<KeyRef> mKeys) {
        chatView.getFeedList(feedArrayList, mKeys);
    }


}

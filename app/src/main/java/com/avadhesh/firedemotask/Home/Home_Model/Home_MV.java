package com.avadhesh.firedemotask.Home.Home_Model;



import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.Adapter.KeyRef;


import java.util.ArrayList;
import java.util.List;

public interface Home_MV {


    interface OnFinishedListener {

      //  void onSuccessFeedList(FriendlyMessage options,boolean mIsLoading);

        void onSuccessFeedList(ArrayList<EventData> feedArrayList, List<KeyRef> mKeys);





    }

    void get_data(OnFinishedListener listener, ArrayList<EventData> feedArrayList, List<KeyRef> mKeys, String currentDate);

    void get_data_end_listener();

}

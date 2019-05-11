package com.avadhesh.firedemotask.Home.Home_Presenter;

import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.Adapter.KeyRef;

import java.util.ArrayList;
import java.util.List;

public interface Home_PV {

    void call_Feed_List(ArrayList<EventData> feedArrayList, List<KeyRef> mKeys, String currentDate);



}

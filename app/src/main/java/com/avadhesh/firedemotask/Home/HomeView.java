package com.avadhesh.firedemotask.Home;

import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.Adapter.KeyRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saksham on 26/6/17.
 */

public interface HomeView {
  //  void updateList(ArrayList<ChatPojo> list);
    void getFeedList(ArrayList<EventData> feedArrayList, List<KeyRef> mKeys);





}

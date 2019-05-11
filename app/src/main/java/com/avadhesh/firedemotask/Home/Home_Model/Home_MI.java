package com.avadhesh.firedemotask.Home.Home_Model;

import android.util.Log;


import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.Adapter.KeyRef;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.avadhesh.firedemotask.Home.MainActivity.GetAllFeed;


public class Home_MI implements Home_MV {

    ChildEventListener childEventListenerMain = null;
    Query messageQuery;


    @Override
    public void get_data(final OnFinishedListener listener, final ArrayList<EventData> feedArrayList,
                         final List<KeyRef> mKeys, final String currentDate) {
        feedArrayList.clear();
        mKeys.clear();

        messageQuery = FirebaseDatabase.getInstance().getReference().child(GetAllFeed)
                .orderByChild("eventDate").equalTo(currentDate);

        childEventListenerMain = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                EventData messageModel = dataSnapshot.getValue(EventData.class);
                /*KeyRef Key = new KeyRef(dataSnapshot.getKey(), messageModel.getDateTime());
                feedArrayList.add(messageModel);
                mKeys.add(Key);*/

                listener.onSuccessFeedList(feedArrayList, mKeys);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };

        messageQuery.addChildEventListener(childEventListenerMain);


    }

    @Override
    public void get_data_end_listener() {
        messageQuery.removeEventListener(childEventListenerMain);
    }


}

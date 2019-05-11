package com.avadhesh.firedemotask.Home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avadhesh.firedemotask.Adapter.AdapterEvent;
import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.Adapter.KeyRef;
import com.avadhesh.firedemotask.AppClass;
import com.avadhesh.firedemotask.Home.Home_Model.Home_MI;
import com.avadhesh.firedemotask.Home.Home_Presenter.Home_PI;
import com.avadhesh.firedemotask.NodeFirebase;
import com.avadhesh.firedemotask.PostEvent.EventPost;
import com.avadhesh.firedemotask.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements HomeView {

    private RecyclerView mRecyclerView;
    private AdapterEvent mAdapter;
    private Home_PI chatPi;
    private ArrayList<EventData> eventData = new ArrayList<>();
    private ArrayList<String> keyUniqList = new ArrayList<>();
    final Set<EventData> setListData = new HashSet();
    final Set<String> setKeyUniq = new HashSet();
    final Set<KeyRef> setListDataKey = new HashSet();
    public static String GetAllFeed = "";
    private LinearLayoutManager mLayoutManager;

    private TextView selectDate;
    private DatabaseReference mDatabase;
    private int mYear, mMonth, mDay;
    Query getAllListQuery,getDateListQuery;
    ChildEventListener getAllDataListener,getDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        selectDate = findViewById(R.id.selectDate);
        GetAllFeed = NodeFirebase.getInstance().getEventList(firebaseUser.getUid());
        chatPi = new Home_PI(this, new Home_MI(), MainActivity.this);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (AppClass.isInternetConnected()) {
                //  AppClass.getAppinstance().showLoadingDialog(MainActivity.this);
                mAdapter = new AdapterEvent(MainActivity.this, eventData);
                mRecyclerView.setAdapter(mAdapter);
                getData();
        } else {
            AppClass.getAppinstance().showToast(getString(R.string.internet));
        }
        TextView addEvent = findViewById(R.id.textView);
        addEvent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EventPost.class);
            startActivity(intent);
            finish();
        });

        selectDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            final Calendar mCalendar = Calendar.getInstance();
            DatePickerDialog mDatePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // TODO Auto-generated method stub
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        selectDate.setText(sdf2.format(mCalendar.getTime()));
                                 getAllListQuery.removeEventListener(getAllDataListener);
                        keyUniqList.clear();
                        setListData.clear();
                        setListDataKey.clear();
                        setKeyUniq.clear();
                        eventData.clear();
                        getDataDate();
                    }, mYear, mMonth, mDay);

            mDatePickerDialog.show();

        });


    }


    @Override
    public void getFeedList(ArrayList<EventData> tempMessageArrayList, List<KeyRef> mKeys1) {
        List<KeyRef>   temKey = new ArrayList<>();

        setListData.addAll(tempMessageArrayList);
        setListDataKey.addAll(mKeys1);
        tempMessageArrayList = new ArrayList<>(setListData);
        mKeys1 = new ArrayList<>(setListDataKey);

        Collections.sort(tempMessageArrayList, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        Collections.sort(mKeys1, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        setListData.addAll(tempMessageArrayList);
        setListDataKey.addAll(mKeys1);
        eventData = new ArrayList<>(setListData);
        temKey = new ArrayList<>(setListDataKey);
        setKeyUniq.clear();
        for (int i = 0; i < temKey.size(); i++) {
            setKeyUniq.add(temKey.get(i).getKey());

        }
        keyUniqList = new ArrayList<>(setKeyUniq);

        //   mAdapter = new AdapterEvent(MainActivity.this, eventData, keyUniqList);

        if (eventData.size()!=0) {
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        else
            mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public void getData() {
        List<KeyRef>  temKey = new ArrayList<>();
        ArrayList<EventData> mTempData = new ArrayList<>();

        if (mDatabase.child(GetAllFeed).getRoot() != null) {

             getAllListQuery = FirebaseDatabase.getInstance().getReference().child(GetAllFeed).orderByKey();

            getAllDataListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot!=null) {
                        if (keyUniqList.contains(dataSnapshot.getKey())) {

                        } else {
                            EventData eventData= dataSnapshot.getValue(EventData.class);
                            EventData image=new EventData(dataSnapshot.getKey(),
                                    eventData.getTitle(),eventData.getDescription(),eventData.getDate(),eventData.getDateTime(),
                                    eventData.getEventDate(),eventData.getParticipants());
                            KeyRef Key = new KeyRef(dataSnapshot.getKey(), image.getDateTime());
                            temKey.add(Key);
                            mTempData.add(image);

                            setListData.addAll(mTempData);

                            MainActivity.this.eventData = new ArrayList<>(setListData);

                            //Doing sorting according to event post date
                            Collections.sort(MainActivity.this.eventData, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
                            Collections.sort(temKey, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
                            setListData.clear();
                            setListData.addAll(MainActivity.this.eventData);


                            keyUniqList.clear();
                            for (int i = 0; i < temKey.size(); i++) {
                                setKeyUniq.add(temKey.get(i).getKey());

                            }
                            keyUniqList = new ArrayList<>(setKeyUniq);
                            mAdapter = new AdapterEvent(MainActivity.this, MainActivity.this.eventData);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String key = dataSnapshot.getKey();
                    EventData eventData= dataSnapshot.getValue(EventData.class);
                    EventData image=new EventData(dataSnapshot.getKey(),
                            eventData.getTitle(),eventData.getDescription(),eventData.getDate(),eventData.getDateTime(),
                            eventData.getEventDate(),eventData.getParticipants());
                    MainActivity.this.eventData.set(keyUniqList.indexOf(key), image);
                     if (MainActivity.this.eventData.size()!=0) {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    else
                        mRecyclerView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    EventData eventData= dataSnapshot.getValue(EventData.class);
                    EventData image=new EventData(dataSnapshot.getKey(),
                            eventData.getTitle(),eventData.getDescription(),eventData.getDate(),eventData.getDateTime(),
                            eventData.getEventDate(),eventData.getParticipants());
                    MainActivity.this.eventData.remove(image);
                    if (MainActivity.this.eventData.size()!=0) {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    else
                        mRecyclerView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            getAllListQuery.addChildEventListener(getAllDataListener);
        } else {

        }
    }


    public void getDataDate() {

        List<KeyRef>  temKey = new ArrayList<>();
        ArrayList<EventData> mTempData = new ArrayList<>();
        if (mDatabase.child(GetAllFeed).getRoot() != null) {

            getDateListQuery = FirebaseDatabase.getInstance().getReference().child(GetAllFeed)  .orderByChild("eventDate")
                    .equalTo(selectDate.getText().toString());

            getDateListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot!=null) {
                        if (keyUniqList.contains(dataSnapshot.getKey())) {

                        } else {
                            EventData eventData= dataSnapshot.getValue(EventData.class);
                            EventData image=new EventData(dataSnapshot.getKey(),
                                    eventData.getTitle(),eventData.getDescription(),eventData.getDate(),eventData.getDateTime(),
                                    eventData.getEventDate(),eventData.getParticipants());
                            KeyRef Key = new KeyRef(dataSnapshot.getKey(), image.getDateTime());
                            temKey.add(Key);
                            mTempData.add(image);

                            setListData.addAll(mTempData);

                            MainActivity.this.eventData = new ArrayList<>(setListData);

                            //Doing sorting according to event post date
                            Collections.sort(MainActivity.this.eventData, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
                            Collections.sort(temKey, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
                            setListData.clear();
                            setListData.addAll(MainActivity.this.eventData);


                            keyUniqList.clear();
                            for (int i = 0; i < temKey.size(); i++) {
                                setKeyUniq.add(temKey.get(i).getKey());

                            }
                            keyUniqList = new ArrayList<>(setKeyUniq);
                            mAdapter = new AdapterEvent(MainActivity.this, MainActivity.this.eventData);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String key = dataSnapshot.getKey();
                    EventData eventData= dataSnapshot.getValue(EventData.class);
                    EventData image=new EventData(dataSnapshot.getKey(),
                            eventData.getTitle(),eventData.getDescription(),eventData.getDate(),eventData.getDateTime(),
                            eventData.getEventDate(),eventData.getParticipants());
                   /* eventData.set(keyUniqList.indexOf(key), image);
                    if (eventData.size()!=0) {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    else
                        mRecyclerView.setVisibility(View.INVISIBLE);*/
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    EventData eventData= dataSnapshot.getValue(EventData.class);
                    EventData image=new EventData(dataSnapshot.getKey(),
                            eventData.getTitle(),eventData.getDescription(),eventData.getDate(),eventData.getDateTime(),
                            eventData.getEventDate(),eventData.getParticipants());
                    MainActivity.this.eventData.remove(image);
                    if (MainActivity.this.eventData.size()!=0) {
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    else
                        mRecyclerView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            getDateListQuery.addChildEventListener(getDateListener);
        } else {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}


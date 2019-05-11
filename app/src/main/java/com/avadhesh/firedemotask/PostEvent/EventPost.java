package com.avadhesh.firedemotask.PostEvent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.avadhesh.firedemotask.Adapter.EventData;
import com.avadhesh.firedemotask.AppClass;
import com.avadhesh.firedemotask.Home.MainActivity;
import com.avadhesh.firedemotask.LoginRegistration.User;
import com.avadhesh.firedemotask.NodeFirebase;
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
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EventPost extends AppCompatActivity implements View.OnClickListener {

    private TextView dateSelect;
    private EditText eventName, mDescription;
    private Button createEvent;
    private DatabaseReference mDatabase;
    private int mYear, mMonth, mDay;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_post);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        eventName = findViewById(R.id.editText4);
        mDescription = findViewById(R.id.editText5);
        dateSelect = findViewById(R.id.textView2);
        createEvent = findViewById(R.id.button);
        createEvent.setOnClickListener(this);
        dateSelect.setOnClickListener(this);
        spinner = findViewById(R.id.spinner);

        final ArrayList<User> userArrayList = new ArrayList<>();
        final ArrayList<String> strings = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference().child(NodeFirebase.NODE_USER).orderByKey();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                strings.add(user.email);
                userArrayList.add(user);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, strings);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
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
        query.addChildEventListener(childEventListener);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EventPost.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                if (TextUtils.isEmpty(eventName.getText().toString().trim())) {
                    AppClass.getAppinstance().showToast("Enter Event name.");

                } else if (TextUtils.isEmpty(mDescription.getText().toString().trim())) {
                    AppClass.getAppinstance().showToast("Enter Agenda.");

                } else if (TextUtils.isEmpty(dateSelect.getText().toString().trim())) {
                    AppClass.getAppinstance().showToast("Select Date");
                } else {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    long time = cal.getTimeInMillis();
                    Date date = new Date();
                    date.setTime(time);
                    EventData feed = new EventData(
                            eventName.getText().toString(), mDescription.getText().toString(),
                            time, date, dateSelect.getText().toString(), spinner.getSelectedItem().toString());
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    mDatabase.child(NodeFirebase.getInstance().setEventList(firebaseUser.getUid())).push();
                    mDatabase.child(NodeFirebase.getInstance().setEventList(firebaseUser.getUid())).push().setValue(feed);
                    Intent intent = new Intent(EventPost.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.textView2:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final Calendar mCalendar = Calendar.getInstance();
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(this,
                        (view, year, monthOfYear, dayOfMonth) -> {
                            // TODO Auto-generated method stub
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, monthOfYear);
                            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            dateSelect.setText(sdf2.format(mCalendar.getTime()));

                        }, mYear, mMonth, mDay);

                mDatePickerDialog.show();
                break;

        }

    }
}

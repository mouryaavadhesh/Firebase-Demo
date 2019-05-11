package com.avadhesh.firedemotask.LoginRegistration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avadhesh.firedemotask.NodeFirebase;
import com.avadhesh.firedemotask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText mPasswordView;
    public DatabaseReference mDatabase;
    private String fn, ln;
    FirebaseAuth mAuth;
    private EditText extEmail, firstName, lastName;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        extEmail = findViewById(R.id.editText);
        mPasswordView = findViewById(R.id.editText3);
        firstName = findViewById(R.id.editText1);
        lastName = findViewById(R.id.editText2);

        TextView btnSignUp = findViewById(R.id.textView16);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Registration.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.textView16:

                fn = firstName.getText().toString().trim();
                ln = lastName.getText().toString().trim();

                email = extEmail.getText().toString().trim();
                password = mPasswordView.getText().toString().trim();


                if (TextUtils.isEmpty(fn)) {
                    Toast.makeText(getApplicationContext(), "Enter first name address!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(ln)) {
                    Toast.makeText(getApplicationContext(), "Enter last name address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put(firebaseUser.getUid(), new User(fn, ln, email, password,firebaseUser.getUid()));
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        mDatabase.child(NodeFirebase.NODE_USER ).setValue(updates);
                                        Intent intent = new Intent(Registration.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Registration.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                break;
        }

    }

}

package com.planthealth.planthealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL_KEY = "email" ;
    private static final String PASSWORD_KEY = "password";
    private static final String TAG = "message";

    TextView userView;

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("userData/login");

    // Fetch data in real time when user enters the  main activity page
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
//                if (documentSnapshot.exists()) {
//                    String emailText = documentSnapshot.getString(EMAIL_KEY);
//                    String passwordText = documentSnapshot.getString(PASSWORD_KEY);
//
//                    // Gets all of the data
//                    //Map<String, Object> myData= documentSnapshot.getData();
//                    userView.setText("\n" + "Email: " + emailText + "\n" + "Password: " + passwordText);
//                } else if (e != null) {
//                    Log.w(TAG, "Got an exception!", e);
//                }
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userView = (TextView) findViewById(R.id.userLoginView);
    }

    public void fetchUserLogin(View view) {
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String emailText = documentSnapshot.getString(EMAIL_KEY);
                    String passwordText = documentSnapshot.getString(PASSWORD_KEY);

                    // Gets all of the data
                    //Map<String, Object> myData= documentSnapshot.getData();
                    userView.setText("\n" + "Username: " + emailText + "\n" + "Nickname: " + passwordText);
                }
            }
        });
    }

    public void saveUserLogin(View view) {
        EditText emailView = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText passwordView = (EditText) findViewById(R.id.editTextTextPassword);
        String emailText = emailView.getText().toString();
        String passwordText = passwordView.getText().toString();

        if (emailText.isEmpty()  || passwordText.isEmpty()) { return; }
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(EMAIL_KEY, emailText);
        dataToSave.put(PASSWORD_KEY, passwordText);
        // Create a Firebase document which will store the data
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Data has been saved to the database!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Data failed to saved in the database!", e);
            }
        });

    }
}
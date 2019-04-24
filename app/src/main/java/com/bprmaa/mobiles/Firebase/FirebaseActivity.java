package com.bprmaa.mobiles.Firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.bprmaa.mobiles.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseActivity extends AppCompatActivity {

    private Button mButtonSubscribe;
    private Button mButtonLogToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("Token", "ffi : " + token);
    }
}

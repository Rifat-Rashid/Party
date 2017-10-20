package com.rashid.bharadwaj.party;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RegisterActivity extends AppCompatActivity {

    private LinearLayout registerLayout;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        registerLayout = (LinearLayout) findViewById(R.id.register_layout);
        firstNameEditText = (EditText) findViewById(R.id.first_name);
        lastNameEditText = (EditText) findViewById(R.id.last_name);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        registerLayout.setBackgroundResource(R.drawable.party_background_1);
    }
}

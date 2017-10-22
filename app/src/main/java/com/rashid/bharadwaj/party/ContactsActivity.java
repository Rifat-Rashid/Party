package com.rashid.bharadwaj.party;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactsActivity extends AppCompatActivity {

    private String contacts = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contacts = getIntent().getStringExtra("contacts");
    }

    private void parseContacts() {
        String[] contactsArray = contacts.split(",");
        for (int i = 0; i < contactsArray.length; i++) {
            // Implement Logic
        }
    }
}

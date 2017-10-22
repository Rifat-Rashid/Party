package com.rashid.bharadwaj.party;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsActivity extends AppCompatActivity {

    private ListView listView;
    private String contacts = "";
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            contacts = intent.getStringExtra("contacts");
            System.out.println(contacts);
            parseContacts();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        LocalBroadcastManager.getInstance(ContactsActivity.this).registerReceiver(receiver,
                new IntentFilter("contactsIntent"));
        listView = (ListView) findViewById(R.id.contacts);
    }

    private void parseContacts() {
        String[] contactsArray = contacts.split(",");
        printArray(contactsArray);
        ArrayAdapter adapter = new ArrayAdapter<String>(ContactsActivity.this,
                R.layout.activity_contacts, contactsArray);
        listView.setAdapter(adapter);
    }

    // DEBUG METHOD
    private void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}

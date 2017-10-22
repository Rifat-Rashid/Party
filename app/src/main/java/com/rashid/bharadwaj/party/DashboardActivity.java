package com.rashid.bharadwaj.party;

import android.*;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private MapView mapView;
    LocationManager lm;
    public int numClicks = 1;
    public View extendedView;
    public View tintedView;
    private boolean readContactsPermission;
    private LinearLayout extendedLayout;
    private DatabaseReference databaseReference;
    private int instanceCounter = 0;

    private ArrayList<String> phoneNumbers;

    private Button contactsBtn;
    private Button creditCardBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        phoneNumbers = new ArrayList<String>();
        contactsBtn = (Button) findViewById(R.id.demo1);
        creditCardBtn = (Button) findViewById(R.id.demo2);

        Mapbox.getInstance(this, "pk.eyJ1IjoicmlmYXRyYXNoaWQiLCJhIjoiOExWXzZpVSJ9.gEuYvTL_aXc7fqZMegK9kw");
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setMyLocationEnabled(true);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(47.670370, -122.120182))
                        .zoom(13)
                        .bearing(180)
                        .tilt(30)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000);

                mapboxMap.getUiSettings().setTiltGesturesEnabled(false);
                // show markers
                /*
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(DashboardActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                */
            }
        });

        Button partyClick = (Button) findViewById(R.id.hostParty);
        partyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewParty("Party Instance:" + String.valueOf(instanceCounter), 47.670370, -122.120182);
                instanceCounter++;
            }
        });

        /*
         * Later implementation...
         */
        tintedView = (View) findViewById(R.id.tintedBG);
        tintedView.setVisibility(View.INVISIBLE);
        extendedLayout = (LinearLayout) findViewById(R.id.extendedLayout);
        YoYo.with(Techniques.SlideInDown).duration(0).playOn(extendedLayout);
        extendedLayout.setVisibility(View.INVISIBLE);
        Button b = (Button) findViewById(R.id.hostPartyButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numClicks++;
                if (numClicks % 2 == 0) {    // even clicks
                    extendedLayout.setVisibility(View.VISIBLE);
                    tintedView.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInUp).duration(500).playOn(extendedLayout);
                    YoYo.with(Techniques.FadeIn).duration(500).playOn(tintedView);

                } else {
                    YoYo.with(Techniques.SlideOutDown).duration(500).playOn(extendedLayout);
                    YoYo.with(Techniques.FadeOut).duration(500).playOn(tintedView);
                }
            }
        });
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ContactsActivity.class));
            }
        });
        creditCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, CreditCardActivity.class));
            }
        });
        // read contacts permission code
        readContactsPermission = false;
        int count = 0;
        while (!readContactsPermission) {
            getReadContactsPermission();
            if (count >= 1) {
                Toast.makeText(DashboardActivity.this, "Aww, we need your contacts!", Toast.LENGTH_LONG)
                        .show();
            }
            count++;
        }
        loadContacts();
        //printArrayList(phoneNumbers);
        filterPhoneNumbers(phoneNumbers);
        //System.out.println(getUserPhoneNumber());
///        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 150, 1, this);
        //queryPartiesToMap();

        /*
         * testing methods go below
         */
    }

    public void queryPartiesToMap() {
        /*
         * iterate through Firebase/parties
         */
        DatabaseReference partiesRef = databaseReference.child("parties");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = (String) ds.getKey();
                    DatabaseReference partyID = databaseReference.child("parties").child(id);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final Double latitude = (Double) dataSnapshot.child("lat").getValue();
                            final Double longitude = (Double) dataSnapshot.child("lng").getValue();
                            final String partyName = (String) dataSnapshot.child("partyName").getValue();
                            final String phoneNumberList = (String) dataSnapshot.child("phoneNumberList").getValue();

                            String userPhoneNumber = getUserPhoneNumber();
                            System.out.println("Party Name: " + partyName + " Lat: " + latitude + " Lng: " + longitude + " PN:" + userPhoneNumber);
                            if (phoneNumberList.contains(userPhoneNumber)) {
                                mapView.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(MapboxMap mapboxMap) {
                                        mapboxMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(latitude, longitude))
                                                .title(partyName)
                                        );
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    partyID.addListenerForSingleValueEvent(valueEventListener);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        partiesRef.addListenerForSingleValueEvent(eventListener);
    }

    /*
     * TODO: implementation
     */
    public void filterPhoneNumbers(ArrayList<String> phoneNumberList) {
        final int minNumberChar = (int) '0';
        final int maxNumberChar = (int) '9';
        for (int i = 0; i < phoneNumberList.size(); i++) {
            String number = phoneNumberList.get(i);
            String newNumber = "";
            for (int j = 0; j < number.length(); j++) {
                char c = number.charAt(j);  // get char and see if its a valid number
                // between 0 & 9
                if ((int) c >= minNumberChar && (int) c <= maxNumberChar) {
                    newNumber += c;
                }
            }
            System.out.println("Old Number: " + number);
            System.out.println("New Number: " + newNumber);
        }
    }


    // gets all user contacts and stores in StringBuilder object.
    // fields stored:
    // name
    // phone number
    private void loadContacts() {
        StringBuilder builder = new StringBuilder();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cursor2.moveToNext()) {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builder.append("Contact: ").append(name).append(" & Phone Number: ").append(phoneNumber).append(",\n");
                        phoneNumbers.add(phoneNumber);
                    }
                    cursor2.close();
                }
            }
        }
        cursor.close();
        Intent contactsIntent = new Intent("contactsIntent");
        contactsIntent.putExtra("contacts", builder.toString());
        LocalBroadcastManager.getInstance(DashboardActivity.this).sendBroadcast(contactsIntent);
        //System.out.println(builder.toString()); // prints out to console
    }

    private void getReadContactsPermission() {
        if ((ContextCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission
                .READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)) {
            readContactsPermission = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContactsPermission = true;
                }
            }
        }
    }

    /*
     * Name of party & location of party in geo coordinates
     */
    private void writeNewParty(String partyName, double lat, double lng) {
        Party party = new Party(partyName, lat, lng, "4253011512");
        DatabaseReference parties = databaseReference.child("parties");
        Map<String, Object> map = new HashMap<>();
        map.put(partyName, party);
        parties.updateChildren(map);
        //databaseReference.child("parties").child(partyName).setValue(party);
    }

    private String getUserPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) DashboardActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();
        return phoneNumber;
    }

    // DEBUG METHOD BELOW
    private void printArrayList(ArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(DashboardActivity.this, LaunchActivityBeta.class);
        startActivity(i);
        overridePendingTransition(R.anim.right, R.anim.left);
    }

}

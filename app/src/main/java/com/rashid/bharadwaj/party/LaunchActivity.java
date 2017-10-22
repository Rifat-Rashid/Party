package com.rashid.bharadwaj.party;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    // TESTING CODE BELOW
    private Button registerBtn;
    private Button loginBtn;
    private Button dashboardBtn;
    private Button creditCardBtn;
    /* ************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // TESTING CODE BELOW
        registerBtn = (Button) findViewById(R.id.register);
        loginBtn = (Button) findViewById(R.id.login);
        dashboardBtn = (Button) findViewById(R.id.dashboard);
        creditCardBtn = (Button) findViewById(R.id.credit_card);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, RegisterActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
            }
        });
        dashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, DashboardActivity.class));
            }
        });
        creditCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, CreditCardActivity.class));
            }
        });
        /* ***************************************************************************** */
    }
}

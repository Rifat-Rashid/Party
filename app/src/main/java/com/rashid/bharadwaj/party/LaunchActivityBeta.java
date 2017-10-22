package com.rashid.bharadwaj.party;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by DevWork on 10/22/17.
 */

public class LaunchActivityBeta extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_beta);
        Button registerButton = (Button) findViewById(R.id.button2);
        Button loginButton = (Button) findViewById(R.id.button3);
        Button dashboardButton = (Button) findViewById(R.id.button4);
        Button creditCardButton = (Button) findViewById(R.id.button5);

        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(registerButton);
        buttonList.add(loginButton);
        buttonList.add(dashboardButton);
        buttonList.add(creditCardButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivityBeta.this, RegisterActivity.class));
                overridePendingTransition(R.anim.left, R.anim.right);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivityBeta.this, LoginActivity.class));
                overridePendingTransition(R.anim.left, R.anim.right);
            }
        });

        /*
         * styling
         */
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat-Bold.ttf");
        for(Button b : buttonList){
            b.setTypeface(typeface);
        }

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaunchActivityBeta.this, DashboardActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left, R.anim.right);
            }
        });

        creditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivityBeta.this, CreditCardActivity.class));
                overridePendingTransition(R.anim.left, R.anim.right);
            }
        });
    }


}
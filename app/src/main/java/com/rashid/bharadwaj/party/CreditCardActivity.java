package com.rashid.bharadwaj.party;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

public class CreditCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        CardInputWidget cardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        Card cardToSave = cardInputWidget.getCard();
        if (cardToSave == null || !cardToSave.validateCard()) {
            // Implement Logic
        } else {
            Stripe stripe = new Stripe(CreditCardActivity.this, "sk_test_EiIOAUsqCeLRizKyfj9Zqb2j");
            stripe.createToken(cardToSave,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {

                        }

                        @Override
                        public void onSuccess(Token token) {

                        }
                    });
        }
    }
}

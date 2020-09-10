package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity
                            implements View.OnClickListener{

    Button btn_Shops, btn_yourCoupons, btn_howItWorks, btn_Deals;
    Button btn_yourAccount, btn_whoWeAre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_Deals = findViewById(R.id.btn_Deals);
        btn_howItWorks = findViewById(R.id.btn_howItWorks);
        btn_Shops = findViewById(R.id.btn_Shops);
        btn_whoWeAre = findViewById(R.id.btn_whoWeAre);
        btn_yourAccount = findViewById(R.id.btn_yourAccount);
        btn_yourCoupons = findViewById(R.id.btn_yourCoupons);


        btn_Deals.setOnClickListener(this);
        btn_yourAccount.setOnClickListener(this);
        btn_Shops.setOnClickListener(this);
        btn_whoWeAre.setOnClickListener(this);
        btn_yourCoupons.setOnClickListener(this);
        btn_howItWorks.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yourAccount:
                startActivity(new Intent(getApplicationContext(), YourAccountActivity.class));
                break;
            case R.id.btn_Shops:
                startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                break;
            case R.id.btn_whoWeAre:
                startActivity(new Intent(getApplicationContext(), WhoWeAreActivity.class));
                break;
            case R.id.btn_yourCoupons:
                startActivity(new Intent(getApplicationContext(), YourCouponActivity.class));
                break;
            case R.id.btn_howItWorks:
                startActivity(new Intent(getApplicationContext(), HowItWorksActivity.class));
                break;
            case R.id.btn_Deals:
                startActivity(new Intent(getApplicationContext(), DealsActivity.class));
                break;
        }
    }
}
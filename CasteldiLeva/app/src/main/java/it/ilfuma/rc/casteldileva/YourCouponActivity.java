package it.ilfuma.rc.casteldileva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class YourCouponActivity extends AppCompatActivity {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    ImageView iv_qr;
    TextView tv_numberOfCoupon, tv_noCoupons, tv_howTo, tvNoCoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_coupon);

        tv_numberOfCoupon = findViewById(R.id.tv_numberOfCoupon);
        tv_howTo = findViewById(R.id.tv_howTo);
        tv_noCoupons = findViewById(R.id.tv_noCoupons);
        iv_qr = findViewById(R.id.iv_qr);
        tvNoCoup = findViewById(R.id.tvNoCoup);

        //accessing document on the firestore database
        DocumentReference docRef = fStore.collection(("users")).document(fAuth.getUid());
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // lo snapshotlisteneer e' un modo del cazzo  per leggere il doc, perche crea u oggetto chh viene aggiornato  ad ogni modifica sul  backend, si sarebbe potuto usafre un semplice get
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                //we retrieve the  number of the coupon from the server
                String NOfCoupon = documentSnapshot.getString("#coupon");
                if  (NOfCoupon == null || NOfCoupon == "0") {
                    //setting the views if there are non coupons
                    tv_numberOfCoupon.setText("0");
                    tv_howTo.setVisibility(View.GONE);
                    tv_noCoupons.setVisibility(View.VISIBLE);
                    tvNoCoup.setVisibility(View.VISIBLE);
                    //iv_qr.setVisibility(View.GONE);
                }else {
                    //setting the views if there are more then 0 coupons
                    tv_numberOfCoupon.setText(NOfCoupon);
                    String coupon = documentSnapshot.getString("coupon" + NOfCoupon);

                    QRGEncoder qrgEncoder = new QRGEncoder(coupon, null, QRGContents.Type.TEXT, 500);
                    qrgEncoder.setColorBlack(Color.BLACK);
                    qrgEncoder.setColorWhite(Color.WHITE);
                    // Getting QR-Code as Bitmap
                    Bitmap bitmap = qrgEncoder.getBitmap();
                    // Setting Bitmap to ImageView
                    iv_qr.setImageBitmap(bitmap);
                    tvNoCoup.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
package it.ilfuma.rc.casteldileva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import it.ilfuma.rc.casteldileva.riccardoUtils.FirestoreHelper;

public class YourCouponActivity extends AppCompatActivity {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    ImageView iv_qr;
    TextView tv_numberOfCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_coupon);

        tv_numberOfCoupon = findViewById(R.id.tv_numberOfCoupon);
        iv_qr = findViewById(R.id.iv_qr);

        DocumentReference docRef = fStore.collection(("users")).document(fAuth.getUid()); // praticamente il figlio di puttana sta importando ill documento daL cloud e per farlo gli da un mezzo path sempificato (ADORO)
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // lo snapshotlisteneer e' un modo del cazzo  per leggere il doc, perche crea u oggetto chh viene aggiornato  ad ogni modifica sul  backend, si sarebbe potuto usafre un semplice get
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String NOfCoupon = documentSnapshot.getString("#coupon");
                Log.d("TAG", NOfCoupon);
                tv_numberOfCoupon.setText(NOfCoupon);
                Log.d("TAG", "coupon" + NOfCoupon);
                String coupon = documentSnapshot.getString("coupon" + NOfCoupon);

                QRGEncoder qrgEncoder = new QRGEncoder(coupon, null, QRGContents.Type.TEXT, 500);
                qrgEncoder.setColorBlack(Color.RED);
                qrgEncoder.setColorWhite(Color.BLUE);
                // Getting QR-Code as Bitmap
                Bitmap bitmap = qrgEncoder.getBitmap();
                // Setting Bitmap to ImageView
                iv_qr.setImageBitmap(bitmap);
            }
        });
    }
}
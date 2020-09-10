package it.ilfuma.rc.casteldileva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class YourCouponActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    String inputValue;
    String NOfCoupon;
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

        getNumberOfCoupon();
        getQRcode();
        showQRCode( inputValue);
    }


    private void getNumberOfCoupon() {
        DocumentReference docRef = fStore.collection(("users")).document(fAuth.getUid()); // praticamente il figlio di puttana sta importando ill documento daL cloud e per farlo gli da un mezzo path sempificato (ADORO)
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // lo snapshotlisteneer e' un modo del cazzo  per leggere il doc, perche crea u oggetto chh viene aggiornato  ad ogni modifica sul  backend, si sarebbe potuto usafre un semplice get
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                NOfCoupon = documentSnapshot.getString("#coupon");
                tv_numberOfCoupon.setText(NOfCoupon);
            }
        });
    }

    private void getQRcode( ) {
        DocumentReference docRef = fStore.collection(("users")).document(fAuth.getUid()); // praticamente il figlio di puttana sta importando ill documento daL cloud e per farlo gli da un mezzo path sempificato (ADORO)
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // lo snapshotlisteneer e' un modo del cazzo  per leggere il doc, perche crea u oggetto chh viene aggiornato  ad ogni modifica sul  backend, si sarebbe potuto usafre un semplice get
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                inputValue = documentSnapshot.getString("coupon" + NOfCoupon);
            }
        });
    }

    protected void showQRCode( String inputValue){
        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, 500);
        //qrgEncoder.setColorBlack(Color.RED);
        //qrgEncoder.setColorWhite(Color.BLUE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        iv_qr.setImageBitmap(bitmap);
        iv_qr.invalidate();
    }
}
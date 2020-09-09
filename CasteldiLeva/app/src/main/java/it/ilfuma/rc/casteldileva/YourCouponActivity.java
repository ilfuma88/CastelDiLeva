package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class YourCouponActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private static final String inputValue = "culo";
    ImageView iv_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_coupon);

        iv_qr = findViewById(R.id.iv_qr);

        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, 400);
        //qrgEncoder.setColorBlack(Color.RED);
        //qrgEncoder.setColorWhite(Color.BLUE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        iv_qr.setImageBitmap(bitmap);
        iv_qr.invalidate();
    }

    protected void showQRCode(){
        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, QRGContents.Type.TEXT);
        qrgEncoder.setColorBlack(Color.RED);
        qrgEncoder.setColorWhite(Color.BLUE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        iv_qr.setImageBitmap(bitmap);
    }
}
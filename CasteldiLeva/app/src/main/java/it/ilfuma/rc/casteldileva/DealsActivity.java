package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class DealsActivity extends AppCompatActivity {
    private Holder holder;
    private int cntPopupWindow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        holder = new Holder(this);
    }

    class Holder implements View.OnClickListener {
        private Context mContext;
        private CardView cvDiscount, cvComplimentaryServices, cvSpecialPackets;
        private PopupWindow mPopupWindow;
        private ImageButton ibtnClosePopup;
        private ConstraintLayout clDeals;

        public Holder(Context context) {
            mContext = context;

            clDeals = findViewById(R.id.clDeals);

            cvDiscount = findViewById(R.id.cvDiscount);
            cvComplimentaryServices = findViewById(R.id.cvComplimentaryServices);
            cvSpecialPackets = findViewById(R.id.cvSpecialPackets);

            cvDiscount.setOnClickListener(this);
            cvComplimentaryServices.setOnClickListener(this);
            cvSpecialPackets.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cvDiscount:
                    if (cntPopupWindow == 0) {
                        showPopupWindow(R.id.cvDiscount);
                        cntPopupWindow += 1;
                    }
                    break;
                case R.id.cvComplimentaryServices:
                    if (cntPopupWindow == 0) {
                        showPopupWindow(R.id.cvComplimentaryServices);
                        cntPopupWindow += 1;
                    }
                    break;
                case R.id.cvSpecialPackets:
                    if (cntPopupWindow == 0) {
                        showPopupWindow(R.id.cvSpecialPackets);
                        cntPopupWindow += 1;
                    }
                    break;

                case R.id.ibtnClosePopup:
                    mPopupWindow.dismiss(); // Chiudo la finestra di popup
                    cntPopupWindow = 0;
            }
        }

        public void showPopupWindow(int id) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
            View popup = null;
            if (id == R.id.cvDiscount) {
                popup = inflater.inflate(R.layout.popup_discount, null);    // Gonfio (inflate) il layout "popup_discount" all'interno della View popupDiscount
            }
            if (id == R.id.cvComplimentaryServices) {
                popup = inflater.inflate(R.layout.popup_complimentary_services, null);    // Gonfio (inflate) il layout "popup_discount" all'interno della View popupDiscount
            }
            if (id == R.id.cvSpecialPackets) {
                popup = inflater.inflate(R.layout.popup_special_packets, null);    // Gonfio (inflate) il layout "popup_discount" all'interno della View popupDiscount
            }

            mPopupWindow = new PopupWindow(popup, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);   // Inizializzo una nuova istanza di una finestra popup

            // Imposto un valore di elevation per la finestra di popup (per API >= 21)
            if (Build.VERSION.SDK_INT >= 21) {
                mPopupWindow.setElevation(5.0f);
            }

            mPopupWindow.showAtLocation(clDeals, Gravity.CENTER, 0, 0);  // Visualizza la finestra di popup al centro del ConstraintLayout del root

            ibtnClosePopup = popup.findViewById(R.id.ibtnClosePopup);
            ibtnClosePopup.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}

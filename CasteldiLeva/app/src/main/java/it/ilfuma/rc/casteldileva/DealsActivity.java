package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class DealsActivity extends AppCompatActivity {
    private Holder holder;

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
                    showPopupWindow(R.id.cvDiscount);
                    break;
                case R.id.cvComplimentaryServices:
                    break;
                case R.id.cvSpecialPackets:
                    break;
            }
        }

        public void showPopupWindow(int id) {
            switch (id) {
                case R.id.cvDiscount:
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
                    View popupDiscount = inflater.inflate(R.layout.popup_discount, null);    // Gonfio (inflate) il layout "popup_discount" all'interno della View popupDiscount

                    mPopupWindow = new PopupWindow(popupDiscount, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);   // Inizializzo una nuova istanza di una finestra popup

                    // Imposto un valore di elevation per la finestra di popup (per API >= 21)
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    mPopupWindow.showAtLocation(clDeals, Gravity.CENTER, 0, 0);  // Visualizza la finestra di popup al centro del ConstraintLayout del root

                    //ibtnClosePopup = popupDiscount.findViewById(R.id.ibtnClosePopup);
                    //ibtnClosePopup.setOnClickListener(this);
                    break;
            }
        }
    }
}

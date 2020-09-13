package it.ilfuma.rc.casteldileva;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * La DealsActivity gestisce l'attività relativa alle offerte dei negozi,
 * mostrando dei popup che permettono di visualizzare i negozi aderenti alle offerte.
 */
public class DealsActivity extends AppCompatActivity {
    private Holder holder;
    private int cntPopupWindow = 0; // conta quante finestre di Popup sono aperte. All'inizio nessuna.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        holder = new Holder(this);
    }

    class Holder implements View.OnClickListener {
        private Context mContext;   // e' il contesto in cui gira l'Activity
        private CardView cvDiscount, cvComplimentaryServices, cvSpecialPackets;
        private PopupWindow mPopupWindow;   // la finestra di Popup che mostrera' le offerte dopo il click delle CardView
        private ImageButton ibtnClosePopup; // il bottone che permette la chiusura del popup
        private ConstraintLayout clDeals;

        public Holder(Context context) {
            mContext = context;

            clDeals = findViewById(R.id.clDeals);

            cvDiscount = findViewById(R.id.cvDiscount);
            cvComplimentaryServices = findViewById(R.id.cvComplimentaryServices);
            cvSpecialPackets = findViewById(R.id.cvSpecialPackets);

            // imposto i Listener di click sulle CardView
            cvDiscount.setOnClickListener(this);
            cvComplimentaryServices.setOnClickListener(this);
            cvSpecialPackets.setOnClickListener(this);
        }

        /**
         * L'onClick, al click sulle CardView, gestisce l'apertura di un popup che mostra le offerte
         * relative ad una categoria di sconti.
         * @param v: la View che e' stata cliccata
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cvDiscount:
                    if (cntPopupWindow == 0) {  // verifica che non sia aperto alcun popup
                        showPopupWindow(R.id.cvDiscount);   // creazione e visualizzazione del popup
                        cntPopupWindow += 1;    // un popup e' stato aperto
                    }
                    break;
                case R.id.cvComplimentaryServices:
                    if (cntPopupWindow == 0) { // verifica che non sia aperto alcun popup
                        showPopupWindow(R.id.cvComplimentaryServices);  // creazione e visualizzazione del popup
                        cntPopupWindow += 1; // un popup e' stato aperto
                    }
                    break;
                case R.id.cvSpecialPackets:
                    if (cntPopupWindow == 0) { // verifica che non sia aperto alcun popup
                        showPopupWindow(R.id.cvSpecialPackets); // creazione e visualizzazione del popup
                        cntPopupWindow += 1; // un popup e' stato aperto
                    }
                    break;

                case R.id.ibtnClosePopup:
                    mPopupWindow.dismiss(); // Chiudo la finestra di popup
                    cntPopupWindow = 0; // nessun popup aperto
            }
        }

        /**
         * showPopupWindow() gestisce la creazione e la visualizzazione della finestra di Popup
         * @param id: e' l'id della CardView che e' stata cliccata e di cui bisogna aprire il popup
         */
        public void showPopupWindow(int id) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);  // Inizializzo una nuova istanza del LayoutInflater service
            View popup = null;
            if (id == R.id.cvDiscount) {
                popup = inflater.inflate(R.layout.popup_discount, null);    // Gonfio (inflate) il layout "popup_discount" all'interno della View popup
            }
            if (id == R.id.cvComplimentaryServices) {
                popup = inflater.inflate(R.layout.popup_complimentary_services, null);    // Gonfio (inflate) il layout "popup_complimentary_services" all'interno della View popup
            }
            if (id == R.id.cvSpecialPackets) {
                popup = inflater.inflate(R.layout.popup_special_packets, null);    // Gonfio (inflate) il layout "popup_special_packets" all'interno della View popup
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
        startActivity(new Intent(getApplicationContext(), HomeActivity.class)); // torna alla HomeActivity
        finish();   // termina la corrente Activity
    }
}

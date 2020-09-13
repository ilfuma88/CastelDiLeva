package it.ilfuma.rc.casteldileva;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import it.ilfuma.rc.casteldileva.Database.Shop;

/**
 * La ShopActivity gestisce la visualizzazione delle informazioni relative al
 * negozio cliccato nella ShopsActivity (la quale mostra la lista di negozi
 * appartenenti ad una categoria).
 */
public class ShopActivity extends AppCompatActivity {
    private Holder holder;
    private Shop mShop; // il negozio completo di tutti i suoi campi (alcuni potrebbero essere null o "")
    private RequestQueue mRequestQueue;
    private Bitmap mBmp;    // l'immagine Bitmap del negozio (una volta fatta la ImageRequest)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mRequestQueue = Volley.newRequestQueue(this);   // istanzio la coda di richieste gestite da Volley

        holder = new Holder(this);

        Intent shopIntent = getIntent();    // shopIntent riceve l'intent passato da ShopsActivity
        mShop = shopIntent.getParcelableExtra("shop");  // mShop prende l'oggetto con chiave "shop" dal bundle dell'Intent

        holder.setLayoutShopAttrs(mShop);   // faccio il set nelle View del layout dei campi dello Shop
    }

    class Holder implements View.OnClickListener {
        private Context mContext;   // e' il contesto in cui gira l'Activity

        private ScrollView svShopDetails;
        private CardView cvShopInfo, cvShopDeals1, cvShopDeals2, cvShopDeals3, cvContacts;
        private TextView tvShopName, tvShopDescription, tvDiscount1, tvCondition1, tvDiscount2, tvCondition2, tvDiscount3, tvCondition3;
        private TextView tvNumber1, tvNumber2;
        private ImageView ivShopLogo, ivWebsite, ivMaps, ivMail;

        public Holder(Context context) {
            mContext = context;

            svShopDetails = findViewById(R.id.svShopDetails);
            svShopDetails.setVisibility(View.VISIBLE);

            cvShopInfo = svShopDetails.findViewById(R.id.cvShopInfo);
            cvShopDeals1 = svShopDetails.findViewById(R.id.cvShopDeals1);
            cvShopDeals2 = svShopDetails.findViewById(R.id.cvShopDeals2);
            cvShopDeals3 = svShopDetails.findViewById(R.id.cvShopDeals3);
            cvContacts = svShopDetails.findViewById(R.id.cvContacts);

            tvShopName = cvShopInfo.findViewById(R.id.tvShopName);
            tvShopDescription = cvShopInfo.findViewById(R.id.tvShopDescription);
            ivShopLogo = cvShopInfo.findViewById(R.id.ivShopLogo);

            tvDiscount1 = cvShopDeals1.findViewById(R.id.tvDiscount1);
            tvCondition1 = cvShopDeals1.findViewById(R.id.tvCondition1);

            tvDiscount2 = cvShopDeals2.findViewById(R.id.tvDiscount2);
            tvCondition2 = cvShopDeals2.findViewById(R.id.tvCondition2);

            tvDiscount3 = cvShopDeals3.findViewById(R.id.tvDiscount3);
            tvCondition3 = cvShopDeals3.findViewById(R.id.tvCondition3);

            tvNumber1 = cvContacts.findViewById(R.id.tvNumber1);
            tvNumber2 = cvContacts.findViewById(R.id.tvNumber2);
            ivWebsite = cvContacts.findViewById(R.id.ivWebsite);
            ivMaps = cvContacts.findViewById(R.id.ivMaps);
            ivMail = cvContacts.findViewById(R.id.ivMail);

            // imposto i Listener di click sulle View
            tvNumber1.setOnClickListener(this);
            tvNumber2.setOnClickListener(this);
            ivWebsite.setOnClickListener(this);
            ivMaps.setOnClickListener(this);
            ivMail.setOnClickListener(this);
        }

        /**
         * setLayoutShopAttrs() fa il set nelle View della "activity_shop.xml" dei campi dello Shop.
         * @param shop : e' l'entita' Shop contenente i campi che devono essere settati nelle View.
         */
        public void setLayoutShopAttrs(Shop shop) {
            /* setto le TextView e le ImageView */
            tvShopName.setText(shop.shopName);
            tvShopDescription.setText(shop.shopDescription);
            setImage(shop.shopLogo, ivShopLogo);    // chiamo il metodo per impostare il logo del negozio nella ImageView "ivShopLogo"

            if (shop.discount1 != null && shop.discount1.compareTo("") != 0)    // se il campo è presente (non null e non "")
                tvDiscount1.setText(shop.discount1);
            if (shop.condition1 != null && shop.condition1.compareTo("") != 0)  // se il campo è presente (non null e non "")
                tvCondition1.setText(shop.condition1);

            if (shop.discount2 != null && shop.discount2.compareTo("") != 0)    // se il campo è presente (non null e non "")
                tvDiscount2.setText(shop.discount2);
            if (shop.condition2 != null && shop.condition2.compareTo("") != 0)  // se il campo è presente (non null e non "")
                tvCondition2.setText(shop.condition2);

            if (shop.discount3 != null && shop.discount3.compareTo("") != 0)    // se il campo è presente (non null e non "")
                tvDiscount3.setText(shop.discount3);
            if (shop.condition3 != null && shop.condition3.compareTo("") != 0)  // se il campo è presente (non null e non "")
                tvCondition3.setText(shop.condition3);

            if (shop.shopNumber1 != null && shop.shopNumber1.compareTo("") != 0)    // se il campo è presente (non null e non "")
                tvNumber1.setText(shop.shopNumber1);
            if (shop.shopNumber2 != null && shop.shopNumber2.compareTo("") != 0)    // se il campo è presente (non null e non "")
                tvNumber2.setText(shop.shopNumber2);
            if (shop.shopWebsite == null || shop.shopWebsite.compareTo("") == 0)    // se il campo è vuoto (null o "")
                ivWebsite.setVisibility(View.INVISIBLE);
            if (shop.shopMail == null || shop.shopMail.compareTo("") == 0)  // se il campo è vuoto (null o "")
                ivMail.setVisibility(View.INVISIBLE);
            if (shop.shopPosition == null || shop.shopPosition.compareTo("") == 0)  // se il campo è vuoto (null o "")
                ivMaps.setVisibility(View.INVISIBLE);
        }

        /**
         * setImage() imposta il logo del negozio nella ImageView passata come parametro
         * @param shopLogo : e' l'url dell'immagine del logo di cui fare la ImageRequest
         * @param imageView : e' la ImageView in cui si deve settare la Bitmap di risposta della ImageRequest
         */
        public void setImage(String shopLogo, final ImageView imageView) {
            ImageRequest imageRequest = null;
            if (shopLogo != null && shopLogo.compareTo("") != 0) {  // verifico che la stringa url non sia null o una stringa vuota...
                imageRequest = new ImageRequest(shopLogo, new Response.Listener<Bitmap>() {     // imposto il listener di risposta della ricerca dell'immagine

                    /**
                     * metodo di callback in risposta all'evento di Response della ImageRequest, in caso positivo di risposta.
                     * Qui si imposta l'immagine nella ImageView.
                     * @param response : e' l'immagine scaricata
                     */
                    @Override
                    public void onResponse(Bitmap response) {
                        mBmp = response;
                        if (mBmp != null)   // se l'immagine non è null
                            imageView.setImageBitmap(mBmp);
                    }
                }, 0, 0, null, Bitmap.Config.ARGB_8888, new Response.ErrorListener() { // imposto il listener di errore sulla risposta della ricerca dell'immagine

                    /**
                     * metodo di callback in risposta all'evento di Response della ImageRequest, in caso di risposta negativa.
                     * Qui si imposta una immagine di default.
                     * @param error : e' l'errore della richiesta di Volley
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.castel_di_leva_lunga);
                    }
                });
                mRequestQueue.add(imageRequest);    // aggiungo la imageRequest alla coda di richieste gestita da Volley
            } else {    // ... altrimenti imposto una immagine di default
                imageView.setImageResource(R.drawable.castel_di_leva_lunga);
            }
            mRequestQueue.add(imageRequest);    // aggiungo la imageRequest alla coda di richieste gestita da Volley
        }

        /**
         * L'onClick, al click sulle View, gestisce i relativi eventi (telefonata passando per l'app predefinita,
         * visualizzazione del sito web sul browser, apertura dell'app di posta elettronica,
         * ricerca della posizione del negozio su Google Maps).
         * @param v : la View che e' stata cliccata
         */
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvNumber1) {
                if (mShop.shopNumber1 != null && mShop.shopNumber1.compareTo("") != 0) {    // se il numero di telefono è presente...
                    Intent intent = new Intent(Intent.ACTION_DIAL); // intent di telefonata senza richiesta di permessi. Si passa tramite l'app di telefonia
                    intent.setData(Uri.parse(mShop.shopNumber1));   // viene settato nell'intent il numero di telefono dello shop
                    startActivity(intent);
                }
            }
            if (v.getId() == R.id.tvNumber2) {
                if (mShop.shopNumber2 != null && mShop.shopNumber2.compareTo("") != 0) {    // se il numero di telefono è presente...
                    Intent intent = new Intent(Intent.ACTION_DIAL); // intent di telefonata senza richiesta di permessi. Si passa tramite l'app di telefonia
                    intent.setData(Uri.parse(mShop.shopNumber2));   // viene settato nell'intent il numero di telefono dello shop
                    startActivity(intent);
                }
            }
            if (v.getId() == R.id.ivWebsite) {
                if (mShop.shopWebsite != null && mShop.shopWebsite.compareTo("") != 0) {    // se il sito web è presente...
                    Intent intentWebsite = new Intent(Intent.ACTION_VIEW);  // intent di visualizzazione del sito sul browser
                    intentWebsite.setData(Uri.parse(mShop.shopWebsite));    // viene settato nel bundle dell'intent la stringa del sito web dello shop
                    startActivity(intentWebsite);
                }
            }
            if (v.getId() == R.id.ivMail) { // se l'email è presente nel campo (se non è vuoto)...
                if (mShop.shopMail != null && mShop.shopMail.compareTo("") != 0) {  // se l'email è presente nel campo (se non è vuoto)...
                    Intent intentMail = new Intent(Intent.ACTION_SENDTO);   // intent di settaggio del destinatario dell'email (nell'app predefinita o nel browser)
                    intentMail.setData(Uri.parse("mailto:" + mShop.shopMail));  // viene settato nel bundle dell'intent l'indirizzo email del destinatario, preceduto dalla stringa "mailto:"
                    startActivity(intentMail);
                }
            }
            if (v.getId() == R.id.ivMaps) {
                if (mShop.shopPosition != null && mShop.shopPosition.compareTo("") != 0) {  // se l'indirizzo (posizione) del negozio è presente...
                    Intent intentMaps = new Intent(Intent.ACTION_VIEW); // intent di visualizzazione su app predefinita o browser
                    intentMaps.setData(Uri.parse(mShop.shopPosition));  // viene settato, nel bundle dell'intent, l'indirizzo dello shop
                    startActivity(intentMaps);
                }
            }
        }
    }
}

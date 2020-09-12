package it.ilfuma.rc.casteldileva;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class ShopActivity extends AppCompatActivity {
    private Holder holder;
    private Shop mShop;
    private RequestQueue mRequestQueue;
    private Bitmap mBmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mRequestQueue = Volley.newRequestQueue(this);

        holder = new Holder(this);

        Intent shopIntent = getIntent();
        mShop = shopIntent.getParcelableExtra("shop");

        holder.setLayoutShopAttrs(mShop);
    }

    class Holder implements View.OnClickListener {
        private Context mContext;

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

            tvNumber1.setOnClickListener(this);
            tvNumber2.setOnClickListener(this);
            ivWebsite.setOnClickListener(this);
            ivMaps.setOnClickListener(this);
            ivMail.setOnClickListener(this);
        }

        public void setLayoutShopAttrs(Shop shop) {
            tvShopName.setText(shop.shopName);
            tvShopDescription.setText(shop.shopDescription);
            setImage(shop.shopLogo, ivShopLogo);

            if (shop.discount1 != null && shop.discount1.compareTo("") != 0)
                tvDiscount1.setText(shop.discount1);
            if (shop.condition1 != null && shop.condition1.compareTo("") != 0)
                tvCondition1.setText(shop.condition1);

            if (shop.discount2 != null && shop.discount2.compareTo("") != 0)
                tvDiscount2.setText(shop.discount2);
            if (shop.condition2 != null && shop.condition2.compareTo("") != 0)
                tvCondition2.setText(shop.condition2);

            if (shop.discount3 != null && shop.discount3.compareTo("") != 0)
                tvDiscount3.setText(shop.discount3);
            if (shop.condition3 != null && shop.condition3.compareTo("") != 0)
                tvCondition3.setText(shop.condition3);
        }

        public void setImage(String shopLogo, final ImageView imageView) {
            ImageRequest imageRequest = null;
            if (shopLogo != null && shopLogo.compareTo("") != 0) {
                imageRequest = new ImageRequest(shopLogo, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mBmp = response;
                        if (mBmp != null)
                            imageView.setImageBitmap(mBmp);
                    }
                }, 0, 0, null, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.castel_di_leva_lunga);
                    }
                });
                mRequestQueue.add(imageRequest);
            } else {
                imageView.setImageResource(R.drawable.castel_di_leva_lunga);
            }
            mRequestQueue.add(imageRequest);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

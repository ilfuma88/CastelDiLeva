package it.ilfuma.rc.casteldileva;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_category);
            new Holder();
        }

        private class Holder implements AdapterView.OnItemClickListener {

            private GridView gvCategories;

            private String[] textCategorie;
            private Integer[] imgCategorie;
            Resources res = getResources();

            Holder() {
                textCategorie = new String[]{res.getString(R.string.tv_shop_clothes),res.getString(R.string.tv_shop_travel), res.getString(R.string.tv_shop_zootech),
                        res.getString(R.string.tv_shop_auto_parts), res.getString(R.string.tv_shop_bar_diner), res.getString(R.string.tv_shop_caf), res.getString(R.string.tv_shop_stationery),
                        res.getString(R.string.tv_shop_beauty_salon), res.getString(R.string.tv_shop_coffee_pod), res.getString(R.string.tv_shop_dentist),
                        res.getString(R.string.tv_shop_building_hardware_store), res.getString(R.string.tv_shop_physiotherapy),
                        res.getString(R.string.tv_shop_jewelry), res.getString(R.string.tv_shop_playroom), res.getString(R.string.tv_shop_rest_pizzeria),
                        res.getString(R.string.tv_shop_sport), res.getString(R.string.tv_shop_typography), res.getString(R.string.tv_shop_nursery_flow_shop)};

                imgCategorie = new Integer[]{R.drawable.negozi_abbigliamento, R.drawable.agenzia_di_viaggio, R.drawable.pet_shop,  R.drawable.autoricambi, R.drawable.bar_tavolacalda, R.drawable.caf,
                        R.drawable.cartoleria, R.drawable.centro_estetico, R.drawable.cialde_capsule, R.drawable.dentista,
                        R.drawable.edilizia_ferramenta, R.drawable.fisioterapia, R.drawable.gioielleria, R.drawable.ludoteca,
                        R.drawable.ristorante_pizzeria, R.drawable.sport, R.drawable.tipografia, R.drawable.vivai_fiorai};

                gvCategories = findViewById(R.id.gvCategories);

                CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, textCategorie, imgCategorie);
                gvCategories.setAdapter(categoryAdapter);

                gvCategories.setOnItemClickListener(this);
            }
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Intent intent = new Intent(CategoryActivity.this, ShopsActivity.class);
                intent.putExtra("categoryId", position +1);
                startActivity(intent);
            }
        }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
    }
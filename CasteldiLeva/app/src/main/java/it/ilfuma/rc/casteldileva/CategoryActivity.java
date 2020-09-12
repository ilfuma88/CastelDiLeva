package it.ilfuma.rc.casteldileva;

import android.content.Intent;
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

            Holder() {
                textCategorie = new String[]{"Abbigliamento", "Agenzia di viaggio", "Alimenti zootecnici","Autoricambi", "Bar/Tavola Calda", "CAF", "Cartolerie", "Centro Estetico", "Cialde/Capsule",
                        "Dentista", "Edilizia/Ferramenta", "Fisioterapia", "Gioielleria", "Ludoteca", "Ristorante/Pizzeria", "Sport", "Tipografia", "Vivai/Fiorai"};

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
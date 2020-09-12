package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WhoWeAreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_we_are);
        new Holder();
    }

    class Holder implements View.OnClickListener{
        Button btnOfficialSite;

        Holder(){
            btnOfficialSite = findViewById(R.id.btnOfficialSite);
            btnOfficialSite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intentOfficialSite = new Intent(Intent.ACTION_VIEW);
            intentOfficialSite.setData(Uri.parse("https://www.iorestoacasteldileva.com/"));
            startActivity(intentOfficialSite);
        }
    }

}
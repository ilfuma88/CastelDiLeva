package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WhoWeAreActivity extends AppCompatActivity
                                implements View.OnClickListener{
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_we_are);

        btn_back = findViewById(R.id.btn_goBack);
        btn_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_goBack){
            this.onBackPressed();
            //finish(); could be used instead of the upper line and would have worked the same (in activity and not in fragments)
        }
    }
}
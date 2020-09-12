package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth= FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            Log.d("TAG", fAuth.getCurrentUser().getDisplayName());
            Log.d("TAG", fAuth.getUid());
            setContentView(R.layout.activity_main);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_main);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
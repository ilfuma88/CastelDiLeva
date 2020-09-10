package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth= FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_main);
            //Toast.makeText( MainActivity.this, "You went through MainActivity.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            setContentView(R.layout.activity_main);
            //Toast.makeText( MainActivity.this, "You went through MainActivity.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
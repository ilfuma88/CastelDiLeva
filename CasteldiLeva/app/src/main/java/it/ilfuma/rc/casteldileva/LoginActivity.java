package it.ilfuma.rc.casteldileva;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
                            implements View.OnClickListener{
    EditText et_email, et_password;
    TextView tv_whoWeAre;
    Button btn_login, btn_gotoRegistration;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        tv_whoWeAre = findViewById(R.id.tv_whoWeAre);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.btn_login);
        btn_gotoRegistration = findViewById(R.id.btn_register);


        tv_whoWeAre.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_gotoRegistration.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                /*
                 handling of login with email and password
                 */
                progressBar.setVisibility(View.VISIBLE);

                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                //checking that EditText are not empty
                if (TextUtils.isEmpty(email)) {
                    progressBar.setVisibility(View.GONE);
                    et_email.setError("L'email è necessaria");
                    break;
                }

                if (TextUtils.isEmpty(password)) {
                    progressBar.setVisibility(View.GONE);
                    et_email.setError("La password è necessaria");
                    break;
                }

                //Login the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "User Logged in.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case R.id.btn_register:
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                finish();
                break;
            case  R.id.tv_whoWeAre:
                startActivity(new Intent(getApplicationContext(), WhoWeAreActivity.class));
                break;

        }
    }
}

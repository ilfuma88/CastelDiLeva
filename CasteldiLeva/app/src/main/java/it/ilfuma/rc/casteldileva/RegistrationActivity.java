package it.ilfuma.rc.casteldileva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity
                                    implements View.OnClickListener{
    EditText et_fullName, et_email, et_password, et_repeatPassword;
    TextView tv_whoWeAre;
    Button btn_register, btn_login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_fullName = findViewById(R.id.et_fullName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_repeatPassword = findViewById(R.id.et_repeatPassword);
        tv_whoWeAre = findViewById(R.id.tv_whoWeAre);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        tv_whoWeAre.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        //FirebaseUser user = fAuth.getCurrentUser(); //test line can be removed
/*
        if(user != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));  //non e' questal'activity che dovra' lanciare in realta'
            finish();
        }else{
            Log.d("TAG", "uccidetemi");
        }
 */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:

                progressBar.setVisibility(View.VISIBLE);

                final String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String repeatPassword = et_repeatPassword.getText().toString().trim();
                final String fullName = et_fullName.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    et_email.setError("L'email è necessaria");
                    break; //l'indianino qui mette un return
                }

                if(TextUtils.isEmpty(password)){
                    et_password.setError("La password è necessaria");
                    break; //l'indianino qui mette un return
                }
                if(!(password.equals(repeatPassword))){
                    et_repeatPassword.setError("La password non coincide con la ripetizione");
                    break; //l'indianino qui mette un return
                }

                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        /**
                         * da qui si scrive sul database
                         */
                        if(task.isSuccessful()){
                            Toast.makeText( RegistrationActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference docRef = fStore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap< >();
                            user.put("fullName", fullName);
                            user.put("email", email);
                            docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   Log.d("TAG", "onSuccess: user Prfile is created for "+ userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: "+ e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));  //non e' questal'activity che dovra' lanciare in realta'
                            finish();
                        }else{
                            Toast.makeText(RegistrationActivity.this, "error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            case R.id.btn_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case  R.id.tv_whoWeAre:
                startActivity(new Intent(getApplicationContext(), WhoWeAreActivity.class));
                break;
        }

    }
}
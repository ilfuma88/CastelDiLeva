package it.ilfuma.rc.casteldileva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class YourAccountActivity extends AppCompatActivity {

    TextView tv_yourName, tv_yourEmail, tv_yourPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        tv_yourPhone = findViewById(R.id.tv_yourPhone);
        tv_yourName =  findViewById(R.id.tv_yuorName);
        tv_yourEmail =  findViewById(R.id.tv_yourEmail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getUid();    //l'indiano usa: fAuth.getCurrentUser().getUid();

        //now we will retrive the data from the damn firestore (cool stuff!!)
        DocumentReference docRef = fStore.collection(("users")).document(userId ); // praticamente il figlio di puttana sta importando ill documento daL cloud e per farlo gli da un mezzo path sempificato (ADORO)
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // lo snapshotlisteneer e' un modo del cazzo  per leggere il doc, perche crea u oggetto chh viene aggiornato  ad ogni modifica sul  backend, si sarebbe potuto usafre un semplice get
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                tv_yourName.setText(documentSnapshot.getString("fullName"));
                tv_yourEmail.setText(documentSnapshot.getString("email"));
            }
        });

    }


    public void logout(View v){
        fAuth.signOut(); //logout
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
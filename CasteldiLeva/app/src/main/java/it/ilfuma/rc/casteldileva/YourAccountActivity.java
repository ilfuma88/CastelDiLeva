package it.ilfuma.rc.casteldileva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso; //Library used to transform images to better fit into layouts and to reduce memory size

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class YourAccountActivity extends AppCompatActivity
                                    implements View.OnClickListener{

    TextView tv_yourName, tv_yourEmail, tvChoosePhoto;
    Button btn_logout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView ivPersonalPhoto, ivQrUser;
    StorageReference mStorageRef;
    ProgressBar progBarProfPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);

        tv_yourName =  findViewById(R.id.tv_yuorName);
        tv_yourEmail =  findViewById(R.id.tv_yourEmail);
        tvChoosePhoto = findViewById(R.id.tvChangePhoto);
        btn_logout = findViewById(R.id.btn_logout);
        ivQrUser = findViewById(R.id.iv_qrcode_user);
        ivPersonalPhoto = findViewById(R.id.ivPersonalPhoto);
        progBarProfPhoto = findViewById(R.id.progBarProfPhoto);
        progBarProfPhoto.setVisibility(View.GONE);

        btn_logout.setOnClickListener(this);
        ivPersonalPhoto.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //create a reference to a Firebase instance
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //now we download the profilePicture that the user has already chosen, that has been already uploaded to Firebase and set it as profile picture
        //create a child reference: profileRef now points to the path below
        final StorageReference profileRef = mStorageRef.child("users/"+fAuth.getUid()+"/profile.jpg");
            progBarProfPhoto.setVisibility(View.VISIBLE);
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) { //Uri representing the downloaded URL of the image
                    tvChoosePhoto.setVisibility(View.INVISIBLE);
                    Picasso.get().load(uri).into(ivPersonalPhoto);
                    progBarProfPhoto.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("getDownloadUrl:", "No profile photo selected");
                    e.printStackTrace();
                }
            });

        userId = fAuth.getUid();    //l'indiano usa: fAuth.getCurrentUser().getUid();

        //now we will retrive the data from the damn firestore (cool stuff!!)
        DocumentReference docRef = fStore.collection(("users")).document(userId ); // praticamente il figlio di puttana sta importando ill documento daL cloud e per farlo gli da un mezzo path sempificato (ADORO)
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() { // lo snapshotlisteneer e' un modo del cazzo  per leggere il doc, perche crea u oggetto chh viene aggiornato  ad ogni modifica sul  backend, si sarebbe potuto usafre un semplice get
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot !=  null){
                    tv_yourName.setText(documentSnapshot.getString("fullName"));
                    tv_yourEmail.setText(documentSnapshot.getString("email"));
                }

                //we assign a unique QRcode to the user
                QRGEncoder qrgEncoder = new QRGEncoder(userId, null, QRGContents.Type.TEXT, 500);
                qrgEncoder.setColorBlack(Color.BLACK);
                qrgEncoder.setColorWhite(Color.WHITE);
                // Getting QR-Code as Bitmap
                Bitmap bitmap = qrgEncoder.getBitmap();
                // Setting Bitmap to ImageView
                ivQrUser.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                fAuth.signOut(); //logout

                if(fAuth.getCurrentUser() == null){
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }else{
                    Log.d("TAG", "user didnt logout");
                }
                break;
            case R.id.ivPersonalPhoto:
                //Intent that opens images
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 && resultCode==RESULT_OK && data!=null &&data.getData()!=null){
            progBarProfPhoto.setVisibility(View.VISIBLE);
            tvChoosePhoto.setVisibility(View.INVISIBLE);
            //the Uri of the image that has been choosen
            Uri imgUri = data.getData();
            uploadImgToFirebase(imgUri);

        }
    }

    //upload image to FirebaseStorage
    private void uploadImgToFirebase(Uri imageUri){
        //create a reference in Firebase Storage
        final StorageReference imgRef = mStorageRef.child("users/"+fAuth.getUid()+"/profile.jpg");
        //put in the Firebase refernce that points to the path above the choosen image
        imgRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(YourAccountActivity.this, R.string.toast_img_uploaded, Toast.LENGTH_LONG).show();
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {   //if the imageUri has been uploaded correctly
                        //use Picasso library that sets the profile picture
                        Picasso.get().load(uri).into(ivPersonalPhoto);
                        progBarProfPhoto.setVisibility(View.GONE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

}
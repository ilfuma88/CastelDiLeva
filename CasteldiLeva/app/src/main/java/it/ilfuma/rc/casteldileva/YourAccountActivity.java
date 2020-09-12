package it.ilfuma.rc.casteldileva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class YourAccountActivity extends AppCompatActivity
                                    implements View.OnClickListener{

    TextView tv_yourName, tv_yourEmail, tvChoosePhoto;
    Button btn_logout, btn_account_go_back;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView ivPersonalPhoto, ivQrUser;
    StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);

        tv_yourName =  findViewById(R.id.tv_yuorName);
        tv_yourEmail =  findViewById(R.id.tv_yourEmail);
        tvChoosePhoto = findViewById(R.id.tvChangePhoto);
        btn_logout = findViewById(R.id.btn_logout);
        btn_account_go_back = findViewById(R.id.btn_account_go_back);
        ivQrUser = findViewById(R.id.iv_qrcode_user);
        ivPersonalPhoto = findViewById(R.id.ivPersonalPhoto);

        btn_logout.setOnClickListener(this);
        btn_account_go_back.setOnClickListener(this);
        ivPersonalPhoto.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //
        final StorageReference profileRef = mStorageRef.child("users/"+fAuth.getUid()+"/profile.jpg");

            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    tvChoosePhoto.setVisibility(View.INVISIBLE);
                    Picasso.get().load(uri).into(ivPersonalPhoto);
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
            case R.id.btn_account_go_back:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
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
            //the imageUri of the image that has been choosen
            tvChoosePhoto.setVisibility(View.INVISIBLE);
            Uri imgUri = data.getData();
            uploadImgToFirebase(imgUri);

        }
    }

    //upload image to FirebaseStorage
    private void uploadImgToFirebase(Uri imageUri){
        //create a reference in Firebase Storage
        final StorageReference imgRef = mStorageRef.child("users/"+fAuth.getUid()+"/profile.jpg");
        //put in that reference the imageUri of the choosen image
        imgRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(YourAccountActivity.this, R.string.toast_img_uploaded, Toast.LENGTH_LONG).show();
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {   //if the imageUri has been uploaded correctly
                        //use Picasso library that sets the image uri into the ImageView of the layout
                        Picasso.get().load(uri).into(ivPersonalPhoto);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imgUri = data.getData();
            ivPersonalPhoto.setImageURI(imgUri);
        }
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void FileSetter(){
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imgUri));
        Ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(YourAccountActivity.this, R.string.toast_img_uploaded, Toast.LENGTH_LONG).show();
                        Log.w("onSuccess()", "uploaded");
                        btnSetPhoto.setVisibility(View.INVISIBLE);
                        btnBgSetPhoto.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(YourAccountActivity.this, "Not uploaded", Toast.LENGTH_LONG).show();
                        Log.w("onFailure()", "notUploaded");
                    }
                });
    }

    private void FileChoser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

}
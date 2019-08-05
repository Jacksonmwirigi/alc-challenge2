package com.techweezy.travelmantics;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.techweezy.travelmantics.model.TravelDeals;
import com.techweezy.travelmantics.utils.FirebaseUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class AdminPanel extends AppCompatActivity {

    EditText price_et, desc, deal_name;
    Button selectBtn;
    ImageView imageView;
    public static final int IMAGE_REQUEST_ID =100;  //Camera/image Request code.
    String imageString;
    String imageName;

    FirebaseDatabase mFireDb;
    DatabaseReference dbRef;
    FirebaseStorage mFiretorage;
    StorageReference storageReference;
    TravelDeals travelDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        mFireDb = FirebaseDatabase.getInstance();
        dbRef = mFireDb.getReference().child("traveldeals");

        deal_name=(EditText)findViewById(R.id.place_nameET);
        price_et=(EditText)findViewById(R.id.place_priceET);
        desc =(EditText)findViewById(R.id.place_descET);
        selectBtn=(Button)findViewById(R.id.select_image);
        imageView =(ImageView)findViewById(R.id.imageView1);

        imageString="null";


        mFiretorage = FirebaseStorage.getInstance();
        storageReference  = mFiretorage.getReference();


        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent (Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/* ");
                startActivityForResult(galleryintent.createChooser
                        (galleryintent,"select image"), IMAGE_REQUEST_ID);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_menu_option:
                saveDeals();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveDeals(){
        String name_str= deal_name.getText().toString().trim();
        String price_str= price_et.getText().toString().trim();
        String descritpion_str=desc.getText().toString().trim();

        if(imageString.equals("upload")){
            Toast.makeText(this, "Uploading Your Image...",
                    Toast.LENGTH_SHORT).show();
        }else if (imageString.equals("null")) {
            Toast.makeText(this, "Please Select an Image...",
                    Toast.LENGTH_SHORT).show();
        }else{
            travelDeals = new TravelDeals();
            travelDeals.setPlace_name(name_str);
            travelDeals.setPrice(price_str);
            travelDeals.setDescription(descritpion_str);
            travelDeals.setImageUrl(imageString);
            travelDeals.setImageName(imageName);
            dbRef.push().setValue(travelDeals);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK) {
            displaySelectedImage(imageString);
            imageString = "upload";
            selectBtn.setText("Loading Image...");
            selectBtn.setEnabled(false);
            Uri imageUri = data.getData();

            if (imageUri !=null){
                final StorageReference ref = storageReference
                        .child("images/"+ UUID.randomUUID().toString());
                ref.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageName = taskSnapshot.getStorage().getPath();
                                ref.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageString = uri.toString();
                                        displaySelectedImage(imageString);
                                        selectBtn.setText("Select Image...");
                                        selectBtn.setEnabled(true);
                                        Toast.makeText(AdminPanel.this,
                                                "Upload Successful",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.
                                getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        int yx = (int) progress;
                        String prog = "Uploading " + yx + "%";
                        selectBtn.setText(prog);
                    }
                });

            }
            else {
                Toast.makeText(this, "No URI data", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void displaySelectedImage(String url){
        if (url != null && url.isEmpty() == false){
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.beach)
                    .into(imageView);
        }
    }
}

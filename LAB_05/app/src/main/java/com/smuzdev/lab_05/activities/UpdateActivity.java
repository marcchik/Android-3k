package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.models.Dish;

public class UpdateActivity extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText txt_name, txt_description, txt_cooking_time;
    String imageUrl;
    String key, oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String recipeName, recipeDescription, recipeCookingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        recipeImage = findViewById(R.id.iv_dishImage);
        txt_name = findViewById(R.id.txt_recipe_name);
        txt_description = findViewById(R.id.text_description);
        txt_cooking_time = findViewById(R.id.text_cooking_time);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {

            Glide.with(UpdateActivity.this)
                    .load(bundle.getString("oldImageUrl"))
                    .into(recipeImage);
            txt_name.setText(bundle.getString("dishNameKey"));
            txt_description.setText(bundle.getString("dishDescriptionKey"));
            txt_cooking_time.setText(bundle.getString("dishCookingTimeKey"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("oldImageUrl");

        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe").child(key);

    }

    public void btnSelectImage(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            uri = data.getData();
            recipeImage.setImageURI(uri);

        }
        else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_LONG).show();
    }

    public void btnUpdateRecipe(View view) {

        recipeName = txt_name.getText().toString().trim();
        recipeDescription = txt_description.getText().toString().trim();
        recipeCookingTime = txt_cooking_time.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Recipe uploading...");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance()
                .getReference().child("RecipeImage").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadRecipe();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    public void uploadRecipe() {

        Dish dish = new Dish(
                recipeName,
                recipeDescription,
                recipeCookingTime,
                imageUrl
        );

        databaseReference.setValue(dish).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
               storageReferenceNew.delete();
                Toast.makeText(UpdateActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
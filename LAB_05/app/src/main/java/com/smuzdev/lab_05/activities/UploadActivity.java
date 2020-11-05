package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.models.Dish;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText txt_name, txt_description, txt_cooking_time;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        recipeImage = findViewById(R.id.iv_dishImage);
        txt_name = findViewById(R.id.txt_recipe_name);
        txt_description = findViewById(R.id.text_description);
        txt_cooking_time = findViewById(R.id.text_cooking_time);
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

    public  void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("RecipeImage").child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadRecipe();
                Toast.makeText(UploadActivity.this, "Image uploaded", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void btnUploadRecipe(View view) {
        uploadImage();
    }

    public void uploadRecipe() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Recipe uploading...");
        progressDialog.show();

        Dish dish = new Dish(
                txt_name.getText().toString(),
                txt_description.getText().toString(),
                txt_cooking_time.getText().toString(),
                imageUrl
        );

        FirebaseDatabase.getInstance().getReference("Recipe")
                .child(dish.getDishName()).setValue(dish).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {

                    Toast.makeText(UploadActivity.this, "Recipe uploaded", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }

}
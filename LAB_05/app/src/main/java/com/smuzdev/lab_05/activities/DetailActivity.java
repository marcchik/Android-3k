package com.smuzdev.lab_05.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smuzdev.lab_05.R;

public class DetailActivity extends AppCompatActivity {

    TextView dishDescription;
    ImageView dishImage;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dishDescription = findViewById(R.id.txtDescription);
        dishImage = findViewById(R.id.ivImage2);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle != null) {

            dishDescription.setText(mBundle.getString("Description"));
            key = mBundle.getString("keyValue");
            imageUrl = mBundle.getString("Image");
            //dishImage.setImageResource(mBundle.getInt("Image"));

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(dishImage);

        }

    }

    public void btnDeleteRecipe(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipe");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });

    }
}
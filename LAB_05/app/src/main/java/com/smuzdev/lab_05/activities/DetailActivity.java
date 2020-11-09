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

    TextView thingName, thingDescription, thingDiscoveryDate, thingDiscoveryPlace,
            thingPickupPoint, userName, userEmail, userPhone;
    ImageView thingImage;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        thingName = findViewById(R.id.txtThingName);
        thingDescription = findViewById(R.id.txtThingDescription);
        thingDiscoveryDate = findViewById(R.id.txtThingDiscoveryDate);
        thingDiscoveryPlace = findViewById(R.id.txtThingDiscoveryPlace);
        thingPickupPoint = findViewById(R.id.txtThingPickupPoint);
        userName = findViewById(R.id.txtUserName);
        userEmail = findViewById(R.id.txtUserEmail);
        userPhone = findViewById(R.id.txtUserPhone);
        thingImage = findViewById(R.id.ivImage2);

        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {

            thingName.setText(mBundle.getString("ThingName"));
            thingDescription.setText(mBundle.getString("ThingDescription"));
            thingDiscoveryDate.setText(mBundle.getString("ThingDiscoveryDate"));
            thingDiscoveryPlace.setText(mBundle.getString("ThingDiscoveryPlace"));
            thingPickupPoint.setText(mBundle.getString("ThingPickupPoint"));
            userName.setText(mBundle.getString("UserName"));
            userPhone.setText(mBundle.getString("UserEmail"));
            userEmail.setText(mBundle.getString("UserPhone"));

            key = mBundle.getString("KeyValue");
            imageUrl = mBundle.getString("ThingImage");

            Glide.with(this)
                    .load(mBundle.getString("ThingImage"))
                    .into(thingImage);

        }
    }

    public void btnDeleteRecipe(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Things");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Thing deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });

    }

    public void btnUpdateRecipe(View view) {

        startActivity(new Intent(getApplicationContext(), UpdateActivity.class)
                .putExtra("thingNameKey", thingName.getText().toString())
                .putExtra("thingDescriptionKey", thingDescription.getText().toString())
                .putExtra("thingDiscoveryDateKey", thingDiscoveryDate.getText().toString())
                .putExtra("thingDiscoveryPlaceKey", thingDiscoveryPlace.getText().toString())
                .putExtra("thingPickupPointKey", thingPickupPoint.getText().toString())
                .putExtra("userNameKey", userName.getText().toString())
                .putExtra("userPhoneKey", userPhone.getText().toString())
                .putExtra("userEmailKey", userEmail.getText().toString())
                .putExtra("oldImageUrl", imageUrl)
                .putExtra("key", key)
        );
    }
}

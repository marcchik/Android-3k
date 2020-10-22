package com.smuzdev.lab_04.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.smuzdev.lab_04.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Item extends AppCompatActivity {

    String TAG = "LAB_04";
    ImageView itemUserAvatar;
    TextView itemNameSurname, itemPhone, itemEmail, itemTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        itemUserAvatar = findViewById(R.id.itemUserAvatar);
        itemNameSurname = findViewById(R.id.itemNameSurname);
        itemPhone = findViewById(R.id.itemPhone);
        itemEmail = findViewById(R.id.itemEmail);
        itemTwitter = findViewById(R.id.itemTwitter);

        Intent intent = getIntent();

        File imgFile = new File(intent.getStringExtra("pathToAvatar"));
        itemUserAvatar.setImageURI(Uri.fromFile(imgFile));
        itemNameSurname.setText("User: " + intent.getStringExtra("name") + " " + intent.getStringExtra("surname"));
        itemPhone.setText("Phone: " + intent.getStringExtra("phone"));
        itemEmail.setText("Email: " + intent.getStringExtra("email"));
        itemTwitter.setText("Twitter: " + "twitter.com/" + intent.getStringExtra("twitter"));

        Log.d(TAG, "onStart: " + intent.getStringExtra("name"));
    }
}
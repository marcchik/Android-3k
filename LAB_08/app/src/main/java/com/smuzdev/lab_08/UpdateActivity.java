package com.smuzdev.lab_08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, description_input, discoveredPlace_input;
    Button update_button, delete_button, select_image_button;
    DatabaseHelper databaseHelper;
    String id, title, description, discoveredPlace;
    byte[] byteImage, newByteImage;
    ImageView image;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        description_input = findViewById(R.id.description_input2);
        discoveredPlace_input = findViewById(R.id.discovered_place_input2);
        image = findViewById(R.id.image_view2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        select_image_button = findViewById(R.id.select_image_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                description = description_input.getText().toString().trim();
                discoveredPlace = discoveredPlace_input.getText().toString().trim();
                byteImage = newByteImage;
                databaseHelper.updateData(id, title, description, discoveredPlace, byteImage);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        select_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            image.setImageURI(uri);

            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            newByteImage = DbBitmapUtility.getBytes(bitmap);

        } else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_LONG).show();
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("description") && getIntent().hasExtra("discoveredPlace")) {

            //Getting Intent Data
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            discoveredPlace = getIntent().getStringExtra("discoveredPlace");

            databaseHelper = new DatabaseHelper(UpdateActivity.this);
            ArrayList<byte[]> thing_image = databaseHelper.selectImageById(id);

            byte[] byteImage = thing_image.get(0);
            Bitmap bitmapImage = DbBitmapUtility.getImage(byteImage);

            //Setting Intent Data
            title_input.setText(title);
            description_input.setText(description);
            discoveredPlace_input.setText(discoveredPlace);
            image.setImageBitmap(bitmapImage);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure you want to delete " + title + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                databaseHelper.deleteOneRaw(id);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
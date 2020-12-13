package com.smuzdev.lab_08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText title_input, description_input, discoveredPlace_input;
    Button add_button, select_image_button;
    ImageView image;
    Uri uri;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        image = findViewById(R.id.image_view);
        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.description_input);
        discoveredPlace_input = findViewById(R.id.discovered_place_input);
        add_button = findViewById(R.id.add_button);
        select_image_button = findViewById(R.id.select_image_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddActivity.this);
                databaseHelper.addBook(title_input.getText().toString().trim(),
                        description_input.getText().toString().trim(),
                        discoveredPlace_input.getText().toString().trim());
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
        } else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_LONG).show();
    }
}
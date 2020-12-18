package com.smuzdev.lab_08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.smuzdev.lab_08.helpers.DbAsyncInsertTask;
import com.smuzdev.lab_08.helpers.DbBitmapUtility;

public class AddActivity extends AppCompatActivity {

    EditText title_input, description_input, discoveredPlace_input;
    Button add_button, select_image_button;
    byte[] byteImage;
    ImageView image;
    Uri uri;

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

                if (title_input.getText().toString().isEmpty()) {
                    title_input.setError("Enter thing title");
                    return;
                }

                if (description_input.getText().toString().isEmpty()) {
                    description_input.setError("Enter Thing Description");
                    return;
                }

                if (discoveredPlace_input.getText().toString().isEmpty()) {
                    discoveredPlace_input.setError("Enter Thing Discovered Place");
                    return;
                }

                if (image == null) {
                    Toast.makeText(AddActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                    return;
                }

//                //СИНХРОННЫЙ INSERT
//                databaseHelper.addThing(title_input.getText().toString().trim(),
//                        description_input.getText().toString().trim(),
//                        discoveredPlace_input.getText().toString().trim(),
//                        byteImage);

                //ASYNC INSERT
                ThingModel thingModel = new ThingModel(
                        title_input.getText().toString().trim(),
                        description_input.getText().toString().trim(),
                        discoveredPlace_input.getText().toString().trim(),
                        byteImage);

                DbAsyncInsertTask asyncInsertTask = new DbAsyncInsertTask(AddActivity.this);
                asyncInsertTask.execute(thingModel);
                startActivity(new Intent(AddActivity.this, MainActivity.class));

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
            byteImage = DbBitmapUtility.getBytes(bitmap);

        } else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_LONG).show();
    }

}
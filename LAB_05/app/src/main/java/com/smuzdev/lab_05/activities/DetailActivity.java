package com.smuzdev.lab_05.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.smuzdev.lab_05.R;

public class DetailActivity extends AppCompatActivity {

    TextView dishDescription;
    ImageView dishImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dishDescription = findViewById(R.id.txtDescription);
        dishImage = findViewById(R.id.ivImage2);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle != null) {

            dishDescription.setText(mBundle.getString("Description"));
            dishImage.setImageResource(mBundle.getInt("Image"));

        }

    }
}
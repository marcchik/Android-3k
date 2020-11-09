package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.helper.CustomTextWatcher;
import com.smuzdev.lab_05.helper.DatePickerFragment;
import com.smuzdev.lab_05.models.Thing;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageView thingImage;
    Uri uri;
    EditText txt_thingName, txt_thingDescription, txt_thingDiscoveryPlace,
            txt_thingPickupPoint;
    TextView txt_thingDiscoveryDate;
    Button selectDateButton;
    String imageUrl;
    String key, oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String thingName, thingDescription, thingDiscoveryDate, thingDiscoveryPlace,
            thingPickupPoint, userName, userPhone, userEmail;
    MaterialButton updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        thingImage = findViewById(R.id.iv_thingImage);
        txt_thingName = findViewById(R.id.txtThingName);
        txt_thingDescription = findViewById(R.id.txtThingDescription);
        txt_thingDiscoveryDate = findViewById(R.id.txtThingDiscoveryDate);
        txt_thingDiscoveryPlace = findViewById(R.id.txtThingDiscoveryPlace);
        txt_thingPickupPoint = findViewById(R.id.txtThingPickupPoint);
        selectDateButton = findViewById(R.id.selectDateButton);
        updateButton = findViewById(R.id.updateButton);
        updateButton.setEnabled(false);
        updateButton.getBackground().setAlpha(128);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        EditText[] editTexts = {txt_thingName, txt_thingDescription, txt_thingDiscoveryPlace, txt_thingPickupPoint};
        CustomTextWatcher textWatcher = new CustomTextWatcher(editTexts, updateButton);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            Glide.with(UpdateActivity.this)
                    .load(bundle.getString("oldImageUrl"))
                    .into(thingImage);
            txt_thingName.setText(bundle.getString("thingNameKey"));
            txt_thingDescription.setText(bundle.getString("thingDescriptionKey"));
            txt_thingDiscoveryDate.setText(bundle.getString("thingDiscoveryDateKey"));
            txt_thingDiscoveryPlace.setText(bundle.getString("thingDiscoveryPlaceKey"));
            txt_thingPickupPoint.setText(bundle.getString("thingPickupPointKey"));
            userName = bundle.getString("userNameKey");
            userPhone = bundle.getString("userPhoneKey");
            userEmail = bundle.getString("userEmailKey");
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("oldImageUrl");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Things").child(key);

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
            thingImage.setImageURI(uri);

        }
        else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_LONG).show();
    }

    public void btnUpdateThing(View view) {
        thingName = txt_thingName.getText().toString().trim();
        thingDescription = txt_thingDescription.getText().toString().trim();
        thingDiscoveryDate = txt_thingDiscoveryDate.getText().toString().trim();
        thingDiscoveryPlace = txt_thingDiscoveryPlace.getText().toString().trim();
        thingDiscoveryPlace = txt_thingPickupPoint.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Thing uploading...");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance()
                .getReference().child("ThingImage").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadThing();
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

    public void uploadThing() {

        Thing thing = new Thing(
                thingName,
                thingDescription,
                thingDiscoveryDate,
                thingDiscoveryPlace,
                thingPickupPoint,
                imageUrl,
                userName,
                userPhone,
                userEmail
        );

        databaseReference.setValue(thing).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                storageReferenceNew.delete();
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = sdf.format(calendar.getTime());
        txt_thingDiscoveryDate.setText(currentDateString);

    }
}
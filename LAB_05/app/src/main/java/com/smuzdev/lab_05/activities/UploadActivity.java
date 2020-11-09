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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.helper.CustomTextWatcher;
import com.smuzdev.lab_05.helper.DatePickerFragment;
import com.smuzdev.lab_05.helper.User;
import com.smuzdev.lab_05.models.Thing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class UploadActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Uri uri;
    User user;
    FirebaseUser FB_user;
    Button selectDateButton;
    TextView txt_thingDiscoveryDate;
    EditText txt_thingName, txt_thingDescription, txt_thingDiscoveryPlace, txt_thingPickupPoint;
    ImageView thingImage;
    String imageUrl, userName, userEmail, userPhone;
    MaterialButton uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        thingImage = findViewById(R.id.iv_thingImage);
        txt_thingName = findViewById(R.id.txtThingName);
        txt_thingDescription = findViewById(R.id.txtThingDescription);
        txt_thingDiscoveryDate = findViewById(R.id.txtThingDiscoveryDate);
        txt_thingDiscoveryPlace = findViewById(R.id.txtThingDiscoveryPlace);
        txt_thingPickupPoint = findViewById(R.id.txtThingPickupPoint);
        selectDateButton = findViewById(R.id.selectDateButton);
        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setEnabled(false);
        uploadButton.getBackground().setAlpha(128);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        EditText[] editTexts = {txt_thingName, txt_thingDescription, txt_thingDiscoveryPlace, txt_thingPickupPoint};
        CustomTextWatcher textWatcher = new CustomTextWatcher(editTexts, uploadButton);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }

        FB_user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(itemSnapshot.getKey());
                    if (itemSnapshot.getKey().equals(FB_user.getUid())) {
                        user = itemSnapshot.getValue(User.class);
                        userName = user.getName();
                        userEmail = user.getEmail();
                        userPhone = user.getPhone();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
        } else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_LONG).show();
    }

    public void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("ThingImage").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Thing Uploading...");
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadThing();
                progressDialog.dismiss();
                Toast.makeText(UploadActivity.this, "Image uploaded", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void btnUploadThing(View view) {
        uploadImage();
    }

    public void uploadThing() {

        Thing thing = new Thing(
                txt_thingName.getText().toString(),
                txt_thingDescription.getText().toString(),
                txt_thingDiscoveryDate.getText().toString(),
                txt_thingDiscoveryPlace.getText().toString(),
                txt_thingPickupPoint.getText().toString(),
                imageUrl,
                userName,
                userPhone,
                userEmail
        );

        FirebaseDatabase.getInstance().getReference("Things")
                .child(thing.getThingName()).setValue(thing).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(UploadActivity.this, "Thing uploaded", Toast.LENGTH_LONG).show();
                    finish();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
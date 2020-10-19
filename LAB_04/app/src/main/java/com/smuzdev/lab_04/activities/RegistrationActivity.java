package com.smuzdev.lab_04.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.smuzdev.lab_04.R;
import com.smuzdev.lab_04.auxiliary.Json;
import com.smuzdev.lab_04.auxiliary.Person;
import com.smuzdev.lab_04.auxiliary.Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class RegistrationActivity extends AppCompatActivity  {

    Button addButton, selectAvatarButton;
    TextInputLayout tilInputName, tilInputSurname;
    EditText inputName, inputSurname, inputEmail, inputTwitter, inputPhone;
    ImageView selectAvatarIcon;
    private static final int GALLERY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle("Registration");

        addButton = findViewById(R.id.addButton);
        selectAvatarIcon = findViewById(R.id.selectAvatarIcon);
        selectAvatarButton = findViewById(R.id.selectAvatarButton);
        inputName = findViewById(R.id.inputName);
        inputSurname = findViewById(R.id.inputSurname);
        inputEmail = findViewById(R.id.inputEmail);
        inputTwitter = findViewById(R.id.inputTwitter);
        inputPhone = findViewById(R.id.inputPhone);

//        inputName.addTextChangedListener(new ValidationTextWatcher(inputName));
//        inputSurname.addTextChangedListener(new ValidationTextWatcher(inputSurname));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addButton.setEnabled(validateName() && validateSurname());
                if (selectAvatarButton.isEnabled()) {
                    Users users = Json.Deserialize();
                    users.addPerson(new Person(
                            inputName.getText().toString(),
                            inputSurname.getText().toString(),
                            inputEmail.getText().toString(),
                            inputTwitter.getText().toString(),
                            inputPhone.getText().toString(),
                            Environment.getExternalStorageDirectory() + "/LAB_04/" +  inputName.getText().toString().hashCode() + ".png"
                    ));
                    users.printPersonList();
                    Json.Serialize(users);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        selectAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = Json.Deserialize();
                users.printPersonList();
                pickImage();
            }
        });

        selectAvatarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = Json.Deserialize();
                users.printPersonList();
                pickImage();
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE);
    }

    public void saveImageToExternalSt(Bitmap image_bitmap) {
//        String userName = inputName.getText().toString();
//        String destPath = "";
//        try {
//            OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/LAB_04/" + userName.hashCode() + ".png");
//            InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
//            FileUtils.copy(inputStream, outputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String userName = inputName.getText().toString();
        String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root, "/LAB_04");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String fname = "Image-" + userName.hashCode() + ".png";
            File file = new File(myDir, fname);
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile(); // if file already exists will do nothing
                FileOutputStream out = new FileOutputStream(file);
                image_bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, new String[]{file.getName()}, null);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Bitmap image_bitmap = null;
            try {
                image_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectAvatarIcon.setImageURI(imageUri);
            saveImageToExternalSt(image_bitmap);
        }
    }

//    //VALIDATION
//    private class ValidationTextWatcher implements TextWatcher {
//        private View view;
//
//        private ValidationTextWatcher(View view) {
//            this.view = view;
//        }
//
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//        public void afterTextChanged(Editable editable) {
//            switch (view.getId()) {
//                case R.id.inputName:
//                    validateName();
//                    break;
//                case R.id.inputSurname:
//                    validateSurname();
//                    break;
//            }
//
//            addButton.setEnabled(validateName() && validateSurname());
//        }
//    }
//
//    private boolean validateName() {
//        if (inputName.getText().toString().trim().isEmpty()) {
//            inputName.setError("Name is required");
//            return false;
//
//        } else if(inputName.getText().toString().length() > 20){
//            inputName.setError("Name can't be more than 20 symbols");
//            requestFocus(tilInputName);
//            return false;
//        }
//        else {
//            tilInputName.setErrorEnabled(false);
//        }
//        return true;
//    }
//
//    private boolean validateSurname() {
//        if (inputSurname.getText().toString().trim().isEmpty()) {
//            inputSurname.setError("Surname is required");
//            return false;
//        } else if(inputSurname.getText().toString().length() > 30){
//            inputSurname.setError("Surname can't be more than 30 symbols");
//            requestFocus(tilInputSurname);
//            return false;
//        }
//        else {
//            tilInputSurname.setErrorEnabled(false);
//        }
//        return true;
//    }
//
//    private void requestFocus(View view) {
//        if (view.requestFocus()) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
//    }
}
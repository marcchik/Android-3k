package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.helper.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etPassword;
    EditText etEmail;
    EditText etPhone;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        etName = findViewById(R.id.input_name);
        etPhone = findViewById(R.id.input_mobile);
    }

    public void btnSignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnSignUp(View view) {
        final String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final String name = etName.getText().toString().trim();
        final String phone = etPhone.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Required");
            etName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter the valid email");
            etEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Required");
            etPhone.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            etPhone.setError("Please enter the valid email");
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Min password length is 6 characters");
            etPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, phone, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Successful register", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Smth wrong", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
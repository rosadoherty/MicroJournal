package com.example.microjournal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    // firebase auth object
    private FirebaseAuth firebaseAuth;
    EditText username;
    EditText emailD;
    EditText password;
    EditText cpassword;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Get Firebase Auth Instance
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void RegisterUser(View view) {
        username = findViewById(R.id.editText_Username);
        emailD = findViewById(R.id.editText_Email);
        password = findViewById(R.id.editText_Password);
        cpassword = findViewById(R.id.editText_ConfirmPassword);

        // getting email and and password from edit text
        String email = emailD.getText().toString();
        String pwd = password.getText().toString();
        String user = username.getText().toString();
        String cpwd = cpassword.getText().toString();

        //checking if input fields are empty
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(cpwd)) {
            Toast.makeText(this, "Please enter the confirmation password", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, please enter a minimum of 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        // creating a new user

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task < AuthResult > task) {
                        // checking if register is successful
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Successfully registered.",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        } else {
                            // authentication failed message
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(SignUpActivity.this, "Failed Registration."
                                    + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

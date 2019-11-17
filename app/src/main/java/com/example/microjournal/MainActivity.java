package com.example.microjournal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity<LoginActivity> extends AppCompatActivity {
    // firebase auth object
    private FirebaseAuth firebaseAuth;
    EditText emailD, password;
    Button btnLogin;
    TextView txtviewSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Firebase Auth Instance
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void LoginUser(View view) {
        //
        emailD = findViewById(R.id.editText_Email);
        password = findViewById(R.id.editText_Password);
        btnLogin = findViewById(R.id.button_Login);
        txtviewSignup = findViewById(R.id.textView_SignUp);

        // getting email and and password from edit text
        String email = emailD.getText().toString();
        String pwd = password.getText().toString();

        //checking if password and email are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //Authenticate user
        firebaseAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

        public void goRegisterUser (View view){
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }
    }


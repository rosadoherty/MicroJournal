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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignUpActivity extends AppCompatActivity {
    // firebase auth object
    private FirebaseAuth firebaseAuth;

    Button btnSignUp= findViewById(R.id.button_SignUp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Get Firebase Auth Instance
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void RegisterUser(View view) {
        String email = ((EditText) findViewById(R.id.editText_Email)).getText().toString();
        String username = ((EditText) findViewById(R.id.editText_Username)).getText().toString();
        String password = ((EditText)findViewById(R.id.editText_Password)).getText().toString();
        String confirmpassword = ((EditText) findViewById(R.id.editText_ConfirmPassword)).getText().toString();

        //checking if input fields are empty
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(this, "Please enter the confirmation password", Toast.LENGTH_LONG).show();
            return;
        }
        // password validation
        if(!password.equals(confirmpassword))
        {
            Toast.makeText(this, "Passwords must be identical", Toast.LENGTH_SHORT).show();
            return;
        }

        // password condition length validation
        if (password.length() < 6) {
            Toast.makeText(this, "Password too short, please enter a minimum of 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
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
}

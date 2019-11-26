package com.example.microjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class WrittenEntryActivity extends AppCompatActivity {
    // initializing variables
    private Button button_Save = (Button)findViewById(R.id.button_Save);
    private EditText editText_PostDescription = ((EditText)findViewById(R.id.EditText_PostDescription));
    private EditText editText_PostTitle = ((EditText)findViewById(R.id.EditText_PostTitle));

    // initializing Firebase fields required for posting written entry to database
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private FirebaseAuth FirebaseAuth;
    private DatabaseReference DatabaseUsers;
    private FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_written_entry);

        // initializing firebase objects
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseRef = database.getInstance().getReference().child("Written Journal Entries");
        FirebaseAuth = FirebaseAuth.getInstance();
        CurrentUser = FirebaseAuth.getCurrentUser();
        DatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUser.getUid());
    }
    // Posting Written Entry to Firebase




}}

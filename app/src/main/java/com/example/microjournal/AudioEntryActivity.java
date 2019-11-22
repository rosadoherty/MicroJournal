package com.example.microjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class AudioEntryActivity extends AppCompatActivity {
    // initializing variables
    Button button_Choose = ((Button)findViewById(R.id.button_Choose));
    Button button_Upload= ((Button)findViewById(R.id.button_Upload));
    ImageView imgview_ =((ImageView)findViewById(R.id.imgView_CameraEntry));
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_entry);
    }
}

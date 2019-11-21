package com.example.microjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting view from mxl file
        setContentView(R.layout.activity_home);

        //

        ImageButton AddButtonContainer = ((ImageButton) findViewById(R.id.imageButton_AddButtonContainer));
        final ImageButton CameraEntry = ((ImageButton) findViewById(R.id.imageButton_CameraEntry));
        final ImageButton AudioEntry = ((ImageButton) findViewById(R.id.imageButton_AudioEntry));
        final ImageButton WrittenEntry = ((ImageButton) findViewById(R.id.imageButton_WrittenEntry));

        // set listener of AddButtonContainer
        AddButtonContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make Camera,Audio,Written buttons visible when add button is pressed
                CameraEntry.setVisibility(View.VISIBLE);
                AudioEntry.setVisibility(View.VISIBLE);
                WrittenEntry.setVisibility(View.VISIBLE);
            }
        });


    }
    public void gotoCameraEntry(View view){
        startActivity(new Intent(HomeActivity.this, CameraEntryActivity.class));
    }

    public void gotoAudioEntry (View view){
        startActivity(new Intent(HomeActivity.this, AudioEntryActivity.class));
    }

    public void gotoWrittenEntry (View view)
    {
        startActivity(new Intent(HomeActivity.this, WrittenEntryActivity.class));
    }

}

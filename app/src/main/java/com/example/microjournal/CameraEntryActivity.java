package com.example.microjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class CameraEntryActivity extends AppCompatActivity {
    // initializing variables
    Button button_Choose = ((Button)findViewById(R.id.button_Choose));
    Button button_Upload= ((Button)findViewById(R.id.button_Upload));
    ImageView imgview_CameraEntry =((ImageView)findViewById(R.id.imgView_CameraEntry));
    private Uri filePath;

    // request code defined as an instance variable
    private final int PICK_IMAGE_REQUEST = 71;

    // firebase fields
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_entry);

        // defining listeners for choose and upload buttons
        button_Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseEntry();
            }
        });

        button_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             uploadEntry();
        }
        });
        // creating a firebase storage instance
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void chooseEntry (){
        Intent intent = new Intent();

        Intent photoLibraryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // gets content
        photoLibraryIntent.setType("image/* video/*");
        // Intent.setType("image/* video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image or Video"), PICK_IMAGE_REQUEST);
    }
    @Override
    // request data
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking if requests are equal to display entry
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgview_CameraEntry.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        private void uploadEntry(){
            if(filePath != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading data ...");
                progressDialog.show();

                StorageReference ref = storageReference.child("imagesandvideos/"+ UUID.randomUUID().toString());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(CameraEntryActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(CameraEntryActivity.this, "Failed to upload"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
        }

    }

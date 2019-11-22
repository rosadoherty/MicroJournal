package com.example.microjournal;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // firebase authentication object
    private FirebaseAuth firebaseAuth;

    // declaring sensor variables
    private SensorManager sensorManager;
    private long lastUpdateTime;
    private View view;
    private static float SHAKE_THRESHOLD_GRAVITY =2;
    private boolean Login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // initializing sensor manager instance
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener( this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
        lastUpdateTime= System.currentTimeMillis();

        // Get firebase Authentication Instance
        firebaseAuth = FirebaseAuth.getInstance();

        Button btnLogin = findViewById(R.id.button_Login);
        TextView txtviewSignup = findViewById(R.id.textView_SignUp);
    }

        public void LoginUser(View view) {
        String email = ((EditText) findViewById(R.id.editText_Email)).getText().toString();
        String password = ((EditText)findViewById(R.id.editText_Password)).getText().toString();

        //checking if password and email are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        //Authenticate user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                           // Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, "Failed Registration."
                                    + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
        public void goRegisterUser (View view){
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
        getAccelerometer (sensorEvent); }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //ignore
    }

    private void getAccelerometer(SensorEvent sensorEvent){
        float [] values = sensorEvent.values;

        //Movement
        float x = values [0];
        float y = values [1];
        float z = values [2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ=  z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement.
        float gForce = (float)Math.sqrt(gX + gX + gY * gY + gZ * gZ);

        long currentTime = System.currentTimeMillis();
        if (gForce >= SHAKE_THRESHOLD_GRAVITY) {
            if (currentTime - lastUpdateTime < 200) {
                return;
            }
            // Authenticate User so login only occurs when details are correct
        }
            //lastUpdateTime = currentTime;
            //Toast.makeText(this, "Device was shaken", Toast.LENGTH_SHORT).show();
        if (Login ==true){


        }
        }
        @Override
        protected void onResume(){
        super.onResume();
        // register this class as a listener for the orientation and accelerometer
            sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onPause()
        {//unregister listener
            super.onPause();
            sensorManager.unregisterListener(this); }
        }
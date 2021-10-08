package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

                                                                                                    //Simple flashlight app



public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;                                                              //Refers to toggleButton in activity_main

    boolean hasCameraFlash;                                                                        //Variable used to check if our mobile device on which the application is installed supports camera flash or not


    boolean flashOn;                                                                               //This variable will decide the current state of flashlight and accordingly fire the flashlight on or off and toggle between them

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);                         //Using the dark theme on device has no effect on app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                                                         // Sets the XML file activity_main as your main layout when the app starts

        toggleButton = findViewById(R.id.toggleButton);                                                 //Finds a button that was identified in activity_main.xml
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);     //Checks if the device is supporting flash

        toggleButton.setOnClickListener(new View.OnClickListener() {                                    //Registers a callback when button is clicked
            @Override
            public void onClick(View v) {
                if (hasCameraFlash) {                                                                   //if device supports flash we can proceed to toggle on and off  the flashlight of the application
                    if (flashOn) {                                                                      // Checks if flashlight is already on
                        flashOn = false;                                                                //flashlight is off
                        toggleButton.setImageResource(R.drawable.power_off);                            //Sets the flashlight image to off button
                        flashLightOff();                                                                //Function which does the work of switching off the flashlight

                    } else {
                        flashOn = true;                                                                 //Flashlight is on
                        toggleButton.setImageResource(R.drawable.power_on);                             //Sets the flashlight image to on button
                        flashLightOn();                                                                 //Function which does the work of switching on the flashlight
                    }
                } else {                                                                                                                   //If device does not support a flash

                    Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();     // Displays a toast message in bottom of screen which says
                                                                                                                                        // "No flash available on your device"
                }
            }
        });
    }

        private void flashLightOn() {                                                                //To switch on the flashlight we require a camera manager object and set its torch attribute to true
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);      //Defines the cameraManager object
            try{
                assert cameraManager != null;
                String cameraId = cameraManager.getCameraIdList()[0];                               //Returns the list of currently connected camera devices by id
                cameraManager.setTorchMode(cameraId, true);                                 //Set the flash unit's torch mode of the camera of the given ID without opening the camera device.
                Toast.makeText(MainActivity.this, "FlashLight is ON", Toast.LENGTH_SHORT).show();   // Displays a toast message in bottom of screen which says "FLashLight is on"
            }
            catch(CameraAccessException e){                                                                       //CameraAccessException is thrown if a camera device could not be queried or opened by the
                                                                                                                  //CameraManager or if the connection to and opened  cameraDevice is no longer valid.

                Log.e("Camera Problem", "Cannot turn on camera flashlight");                           //Sends an error log message
            }
        }

    private void flashLightOff(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            Toast.makeText(MainActivity.this, "FlashLight is OFF", Toast.LENGTH_SHORT).show();
        }
        catch(CameraAccessException e){
            Log.e("Camera Problem", "Cannot turn off camera flashlight");
        }
    }


}
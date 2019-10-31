package com.muhammet.takephotonotify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_SMS_AND_CAMERA_REQUEST = 1001;
    public static final int CAMERA_CAPTURE_EVENT = 1002;

    Button takePhotoButton;
    EditText phoneNumberText;
    ImageView capturedImageView;
    boolean isCameraOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhotoButton   = findViewById(R.id.takePhotoButton);
        phoneNumberText   = findViewById(R.id.phoneNumberText);
        capturedImageView = findViewById(R.id.capturedImageView);
    }

    public void onClickTakePhotoButton(View view) {
        String phoneNumber = phoneNumberText.getText().toString();

        if ( phoneNumber.length() < 10 ) {
            Toast.makeText(getApplicationContext(), getString(R.string.fill_phone_number), Toast.LENGTH_LONG).show();
            return;
        }

        if ( isPermissionGranted() )
            takePhotoAndNotify();
        else
            getPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( requestCode == PERMISSION_SMS_AND_CAMERA_REQUEST ) {
            for ( int permissionResult = 0; permissionResult < permissions.length; permissionResult++ ) {
                if ( grantResults[permissionResult] == PackageManager.PERMISSION_DENIED ) {
                    Toast.makeText(getApplicationContext(), getString(R.string.need_all_permission), Toast.LENGTH_LONG).show();
                    return;
                }
            }

            takePhotoAndNotify();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == CAMERA_CAPTURE_EVENT ) {
            if ( resultCode == Activity.RESULT_OK ) {
                Toast.makeText(getApplicationContext(), getString(R.string.image_captured), Toast.LENGTH_LONG).show();
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                capturedImageView.setImageBitmap(capturedImage);
                sendSms();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.image_not_captured), Toast.LENGTH_LONG).show();
            }

            isCameraOpened = false;
        }
    }

    private void sendSms() {
        String phoneNumber = phoneNumberText.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, getString(R.string.image_capture_notify), null, null);
        Toast.makeText(getApplicationContext(), getString(R.string.image_capture_notify_send), Toast.LENGTH_LONG).show();
    }

    private boolean isPermissionGranted() {
        return  isCameraPermissionGranted() && isSMSPermissionGranted();
    }

    private void getPermissions() {
        ArrayList<String> requestingPermissions = new ArrayList<String>();

        if ( !isSMSPermissionGranted() )
            requestingPermissions.add(Manifest.permission.SEND_SMS);

        if ( !isCameraPermissionGranted() )
            requestingPermissions.add(Manifest.permission.CAMERA);

        for ( String permission : requestingPermissions ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission) ) {
                String message = permission.equals(Manifest.permission.SEND_SMS) ?
                        getString(R.string.need_sms_permission) :
                        getString(R.string.need_camera_permission);

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        String permissions[] = new String[requestingPermissions.size()];

        for ( int counter = 0; counter < requestingPermissions.size(); counter++ )
            permissions[counter] = requestingPermissions.get(counter);

        ActivityCompat.requestPermissions(this, permissions, PERMISSION_SMS_AND_CAMERA_REQUEST);
    }

    private void takePhotoAndNotify() {
        if ( isCameraOpened )
            return;

        isCameraOpened = true;

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CAPTURE_EVENT);
    }

    private boolean isSMSPermissionGranted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isCameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}

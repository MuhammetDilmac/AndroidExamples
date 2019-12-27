package com.muhammet.basiccontactlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initialVariables();
    }

    private void initialVariables() {
        firstNameInput = findViewById(R.id.firstName);
        lastNameInput  = findViewById(R.id.lastName);
        phoneInput     = findViewById(R.id.phone);
    }

    public void onSaveClicked(View view) {
        String firstName = firstNameInput.getText().toString();
        String lastName  = lastNameInput.getText().toString();
        String phone     = phoneInput.getText().toString();

        if ( firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() )
            Toast.makeText(getApplicationContext(), "All fields must be fill", Toast.LENGTH_LONG).show();
        else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("firstName", firstName);
            resultIntent.putExtra("lastName", lastName);
            resultIntent.putExtra("phone", phone);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

    }

    public void onCancelClicked(View view) {
        setResult(Activity.RESULT_CANCELED);

        finish();
    }
}

package com.muhammet.basiccontactlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammet.basiccontactlist.Modal.Contact;

import org.w3c.dom.Text;

public class ShowContact extends AppCompatActivity {
    private TextView firstNameArea;
    private TextView lastNameArea;
    private TextView phoneArea;

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        initialVariables();
        if ( findContact() )
            setViewData();
    }

    private void initialVariables() {
        firstNameArea = findViewById(R.id.firstNameArea);
        lastNameArea  = findViewById(R.id.lastNameArea);
        phoneArea     = findViewById(R.id.phoneArea);
    }

    private boolean findContact() {
        int contactId  = getIntent().getIntExtra("contactId", 0);
        contact        = new Contact(getApplicationContext());
        boolean result = contact.find(contactId);

        if ( !result )
            Toast.makeText(getApplicationContext(), "Contact can not found", Toast.LENGTH_LONG).show();

        return result;
    }

    private void setViewData() {
        firstNameArea.setText(contact.firstName);
        lastNameArea.setText(contact.lastName);
        phoneArea.setText(contact.phone);
    }

    public void onClickButton(View view) {
        switch( view.getId() ) {
            case R.id.call:
                callContact();
                break;

            case R.id.edit:
                editContact();
                break;

            case R.id.delete:
                deleteContact();
                break;

            case R.id.goBack:
                finish();
                break;
        }
    }

    private void deleteContact() {
        boolean result = contact.destroy();

        if ( result ) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("action", "deleted");

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else
            Toast.makeText(getApplicationContext(), "Contact couldn't delete", Toast.LENGTH_LONG).show();
    }

    private void editContact() {
        Intent editContactIntent = new Intent(this, EditContact.class);

        editContactIntent.putExtra("id", contact.id);
        editContactIntent.putExtra("firstName", contact.firstName);
        editContactIntent.putExtra("lastName", contact.lastName);
        editContactIntent.putExtra("phone", contact.phone);

        startActivityForResult(editContactIntent, MainActivity.EDIT_CONTACT_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( requestCode == MainActivity.EDIT_CONTACT_EVENT && resultCode == Activity.RESULT_OK )
            updateContact(data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateContact(@Nullable Intent data) {
        contact.firstName = data.getStringExtra("firstName");
        contact.lastName  = data.getStringExtra("lastName");
        contact.phone     = data.getStringExtra("phone");

        if ( contact.update() ) {
            Toast.makeText(getApplicationContext(), "Contact successfully updated.", Toast.LENGTH_LONG).show();

            if ( findContact() )
                setViewData();
        } else
            Toast.makeText(getApplicationContext(), "Contact update failed.", Toast.LENGTH_LONG).show();
    }

    private void callContact() {
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED ) {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.phone));
            startActivity(callIntent);
        } else {
            if ( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) )
                Toast.makeText(getApplicationContext(), "We need call permission to calling", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CALL_PHONE }, MainActivity.CALL_PERMISSON_EVENT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( requestCode == MainActivity.CALL_PERMISSON_EVENT ) {
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                callContact();
            else
                Toast.makeText(getApplicationContext(), "We need call permission to calling", Toast.LENGTH_LONG).show();
        }
    }
}

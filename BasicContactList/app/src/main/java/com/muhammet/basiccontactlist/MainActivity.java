package com.muhammet.basiccontactlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.muhammet.basiccontactlist.Modal.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CONTACT_ACTIVITY_EVENT = 1001;
    public static final int SHOW_CONTACT_EVENT         = 1002;
    public static final int EDIT_CONTACT_EVENT         = 1003;
    public static final int CALL_PERMISSON_EVENT       = 1004;

    private ListView contactList;
    private Contact contact;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialVariables();
        fetchCustomers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.actionAddContact:
                Intent addContactActivity = new Intent(this, AddContact.class);
                startActivityForResult(addContactActivity, ADD_CONTACT_ACTIVITY_EVENT);
                break;

            case R.id.actionExit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == ADD_CONTACT_ACTIVITY_EVENT && resultCode == Activity.RESULT_OK )
            onCreateResult(data);

        if ( requestCode == SHOW_CONTACT_EVENT && resultCode == Activity.RESULT_OK )
            onShowResult(data);
    }

    private void initialVariables() {
        TextView emptyContactList = findViewById(R.id.emptyContactList);

        contactList = findViewById(R.id.contactList);
        contact     = new Contact(this);

        contactList.setEmptyView(emptyContactList);
    }

    private void fetchCustomers() {
        contacts = contact.all();

        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, contacts);

        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showContactIntent = new Intent(getApplicationContext(), ShowContact.class);
                showContactIntent.putExtra("contactId", contacts.get(position).id);
                startActivityForResult(showContactIntent, SHOW_CONTACT_EVENT);
            }
        });
    }

    private void onCreateResult(@Nullable Intent data) {
        contact.firstName = data.getStringExtra("firstName");
        contact.lastName  = data.getStringExtra("lastName");
        contact.phone     = data.getStringExtra("phone");

        if ( contact.create() )
            Toast.makeText(getApplicationContext(), "Contact successfully created.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Contact creation failed.", Toast.LENGTH_LONG).show();

        fetchCustomers();
    }

    private void onShowResult(@Nullable Intent data) {
        fetchCustomers();

        Toast.makeText(getApplicationContext(), "Contact successfully " + data.getStringExtra("action"), Toast.LENGTH_LONG).show();
    }
}

package com.muhammet.basiccontactlist.Modal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Contact extends SQLiteOpenHelper {
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "basic_contact_list_db";

    private static final String TABLE_NAME       = "contacts";

    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME  = "last_name";
    private static final String FIELD_PHONE      = "phone";
    private static final String FIELD_ID         = "id";

    private Context myContext;

    public int     id        = 0;
    public String  firstName = null;
    public String  lastName  = null;
    public String  phone     = null;

    public Contact(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.myContext = context;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                FIELD_FIRST_NAME + " TEXT NOT NULL, " +
                FIELD_LAST_NAME + " TEXT NOT NULL, " +
                FIELD_PHONE + " TEXT NOT NULL )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public Boolean create() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_FIRST_NAME, firstName);
        values.put(FIELD_LAST_NAME, lastName);
        values.put(FIELD_PHONE, phone);

        long result = db.insert(
            TABLE_NAME,
            null,
            values
        );
        db.close();

        return result > 0;
    }

    public Boolean update() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_FIRST_NAME, firstName);
        values.put(FIELD_LAST_NAME, lastName);
        values.put(FIELD_PHONE, phone);

        long result = db.update(
            TABLE_NAME,
            values,
            FIELD_ID + " = ?", new String[] { String.valueOf(id) }
        );

        db.close();

        return result > 0;
    }

    public Boolean destroy() {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(
            TABLE_NAME,
            FIELD_ID + " = ?",
            new String[] { String.valueOf(id) }
        );

        db.close();

        return result > 0;
    }

    public boolean find(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql        = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = ?";
        Cursor cursor     = db.rawQuery(sql, new String[] { String.valueOf(id) });

        cursor.moveToFirst();
        db.close();

        if ( cursor.getCount() == 0 )
            return false;

        this.id        = cursor.getInt(0);
        this.firstName = cursor.getString(1);
        this.lastName  = cursor.getString(2);
        this.phone     = cursor.getString(3);

        return true;
    }

    public ArrayList<Contact> all() {
        SQLiteDatabase db           = this.getReadableDatabase();
        String sql                  = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor               = db.rawQuery(sql, null);
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        int count                   = cursor.getCount();

        cursor.moveToFirst();

        if ( count == 0 )
            return contacts;

        for ( int counter = 0; counter < count; counter++ ) {
            Contact new_contact   = new Contact(this.myContext);
            new_contact.id        = cursor.getInt(0);
            new_contact.firstName = cursor.getString(1);
            new_contact.lastName  = cursor.getString(2);
            new_contact.phone     = cursor.getString(3);

            contacts.add(new_contact);
            cursor.moveToNext();
        }

        return contacts;
    }
}

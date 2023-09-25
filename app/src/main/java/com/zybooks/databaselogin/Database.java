package com.zybooks.databaselogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public final String tableName = "registration.db";

    public Database(Context context) {
        super(context, "registration.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create Table users(MicroID TEXT primary key, Name TEXT, Gender TEXT, EmailAddress TEXT, AccessCode NUMBER, ConfirmCode NUMBER, Breed TEXT, Neutered TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String MicroID, String Name, String Gender, String Email, String AccessCode, String ConfirmCode, String Breed, String Neutered){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MicroID", MicroID);
        contentValues.put("Name", Name);
        contentValues.put("Gender", Gender);
        contentValues.put("EmailAddress", Email);
        contentValues.put("AccessCode", AccessCode);
        contentValues.put("ConfirmCode", ConfirmCode);
        contentValues.put("Breed", Breed);
        contentValues.put("Neutered", Neutered);
        long result = sqLiteDatabase.insert("users",null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkMicroID(String MicroID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where MicroID = ?", new String[] {MicroID});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}

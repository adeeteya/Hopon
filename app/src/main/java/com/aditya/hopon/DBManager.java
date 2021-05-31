package com.aditya.hopon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

@SuppressWarnings("SyntaxError")
public class DBManager {

    private DatabaseHelper dbHelper;

    private final Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String sequence, int mode, String pid) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.SEQUENCE, sequence);
        contentValue.put(DatabaseHelper.MODE, mode);
        contentValue.put(DatabaseHelper.PID, pid);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SEQUENCE, DatabaseHelper.MODE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor search(String name) {
        return database.rawQuery("SELECT * from Patterns where name like '" + name + "%';", null);
    }

    public boolean checkIfPidExists(String pid) {
        Cursor temp = database.rawQuery("SELECT * from Patterns where pid=='" + pid + "';", null);
        if (temp.getCount() > 0) {
            temp.close();
            return true;
        }
        temp.close();
        return false;
    }

// --Commented out by Inspection START (16/04/2021 7:50 AM):
//    public int update(long _id, String name, String sequence, int mode) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.NAME, name);
//        contentValues.put(DatabaseHelper.SEQUENCE, sequence);
//        contentValues.put(DatabaseHelper.MODE, mode);
//        return database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
//    }
// --Commented out by Inspection STOP (16/04/2021 7:50 AM)

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
        database.execSQL("create table temp ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, sequence TEXT, mode INTEGER, pid TEXT);");
        database.execSQL("insert into temp (name, sequence, mode, pid) select name, sequence, mode, pid from Patterns;");
        database.execSQL("drop table Patterns;");
        database.execSQL("alter table temp rename to Patterns;");
    }

}


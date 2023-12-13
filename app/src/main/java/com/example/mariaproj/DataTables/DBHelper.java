package com.example.mariaproj.DataTables;

import static com.example.mariaproj.DataTables.QueryString.*;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.*;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
//controll the database
public  class DBHelper {
    //define the database name
    private static final String DATABASE_NAME = "Mariadb.db";
    private static final int DATABASE_VERSION = 1;


    private Context mContext;
    private DataBaseHelper dbhelper;
    private SQLiteDatabase db;//method with database

    public SQLiteDatabase getDb() {
        return db;
    }

    private class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(SQL_DELETE_PRODUCT);
            onCreate(sqLiteDatabase);
        }

    }
    //resets the database
    public void Reset(){

        dbhelper.onUpgrade(db,1,2);
    }
    public DBHelper(Context context){
        mContext = context;
        dbhelper = new DataBaseHelper(mContext);
    }
    //open the database to write in
    public DBHelper OpenWriteAble() throws SQLException{
        db = dbhelper.getWritableDatabase();
        return this;
    }
    //open the database to read from
    public DBHelper OpenReadAble() throws SQLException{
        db = dbhelper.getReadableDatabase();
        return this;
    }
    //close the database

    public void Close(){
        dbhelper.close();
    }

}

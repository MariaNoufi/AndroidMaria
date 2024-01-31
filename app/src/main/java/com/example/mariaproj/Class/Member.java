package com.example.mariaproj.Class;

import static com.example.mariaproj.DataTables.TablesString.MemberTable.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class Member implements SqlInterface{
    private int MID;
    private int VID;
    private String UID;
    //endregion

    //region Constructors
    public Member( int VID, String UID){
        this.VID = VID;
        this.UID = UID;
    }
    //endregion

    //region Add,Delete,Update,Select Sql
    @Override
    public long Add(SQLiteDatabase db) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, UID);
        values.put(COLUMN_VOLUNTEER_ID, VID);

// Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_MEMBER, null, values);
    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        String selection = BaseColumns._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {id+""};
// Issue SQL statement.
        return db.delete(TABLE_MEMBER, selection, selectionArgs);
    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, UID);
        values.put(COLUMN_VOLUNTEER_ID, VID);

// Which row to update, based on the title
        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };

        return  db.update(
                TABLE_MEMBER,
                values,
                selection,
                selectionArgs);
    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_USER_ID,
                COLUMN_VOLUNTEER_ID
        };
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";
        Cursor c = db.query(TABLE_MEMBER,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        return c;
    }
    public boolean IsExist(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_USER_ID,
                COLUMN_VOLUNTEER_ID
        };
        String selection = COLUMN_USER_ID + " = ? AND " +COLUMN_VOLUNTEER_ID+" = ?";
        String[] selectionArgs = {UID,VID+""};

// How you want the results sorted in the resulting Cursor
        Cursor c = db.query(TABLE_MEMBER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Log.d("length",c.getCount()+"");
        return c.getCount() != 0;
    }
    //endregion

    //region Getter and Setter
    public int getVid() {
        return VID;
    }

    public void setVid(int VID) {
        this.VID =VID;
    }

    public String getUid() {
        return UID;
    }

    public void setUid(String UID) {
        this.UID = UID;
    }
    public int getMID() {
        return MID;
    }

    public void setMID(int MID) {
        this.MID = MID;
    }

    //endregion
}

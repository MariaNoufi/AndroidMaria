package com.example.mariaproj.Class;

import static com.example.mariaproj.DataTables.TablesString.MemberTable.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Member implements SqlInterface{

    //region Attribute
    private String Members;
    private String VID;
    private String UID;
    //endregion

    //region Constructors
    public Member(String Members, String VID, String UID){
        this.Members=Members;
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
    public Cursor SelectByUserId(SQLiteDatabase db, int userid) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_USER_ID,
                COLUMN_VOLUNTEER_ID
        };
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { userid+"" };

// How you want the results sorted in the resulting Cursor
        Cursor c = db.query(TABLE_MEMBER,
                projection,
                null,
                null,
                null,
                null,
                null);
        return c;
    }
    //endregion

    //region Getter and Setter
    public String getMembers() {
        return Members;
    }

    public void setMembers(String Members) {
        this.Members = Members;
    }

    public String getVid() {
        return VID;
    }

    public void setVid(String VID) {
        this.VID =VID;
    }

    public String getUid() {
        return UID;
    }

    public void setUid(String UID) {
        this.UID = UID;
    }

    //endregion
}
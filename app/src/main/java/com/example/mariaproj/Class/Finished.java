package com.example.mariaproj.Class;

import static com.example.mariaproj.DataTables.TablesString.FinishedTable.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Finished implements SqlInterface {
    //region Attributes

    private String Finished;
    private String VID;
    //endregion

    //region Constructors
    public Finished(String Finished, String VID) {
        this.Finished = Finished;
        this.VID = VID;
    }
    //endregion

    //region Add,Delete,Update,Select Sql
    @Override
    public long Add(SQLiteDatabase db) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TABLE_FINISHED, Finished);
        values.put(COLUMN_VOLUNTEER_ID, VID);

// Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_FINISHED, null, values);
    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        String selection = BaseColumns._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {id + ""};
// Issue SQL statement.
        return db.delete(TABLE_FINISHED, selection, selectionArgs);
    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(TABLE_FINISHED, Finished);
        values.put(COLUMN_VOLUNTEER_ID, VID);

// Which row to update, based on the title
        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = {id + ""};

        return db.update(
                TABLE_FINISHED,
                values,
                selection,
                selectionArgs);
    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                TABLE_FINISHED,
                COLUMN_VOLUNTEER_ID
        };
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";
        Cursor c = db.query(TABLE_FINISHED,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        return c;
    }

    public String getFinished() {
        return Finished;
    }

    public void setFinished(String finished) {
        Finished = finished;
    }

    public String getVID() {
        return VID;
    }

    public void setVID(String VID) {
        this.VID = VID;
    }
}
//endregion


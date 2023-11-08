package com.example.mariaproj.Class;

import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.*;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Volunteer implements SqlInterface{

    //region Attribute
    protected int pid;
    protected String place;



    protected String Pdescribtion;
    protected String requiredSup;

    protected double requiredNumOfVolunteers;
    protected double numOfRegisteredVolunteers;
    protected byte[] imageByte;
    //endregion

    //region Constructors
    public Volunteer(String place, String Pdescribtion , String requiredSup, double requiredNumOfVolunteers, double numOfRegisteredVolunteers, byte[] image){
        this.requiredNumOfVolunteers=requiredNumOfVolunteers;
        this.numOfRegisteredVolunteers=numOfRegisteredVolunteers;
        this.place=place;
        this.Pdescribtion=Pdescribtion;
        this.requiredSup=requiredSup;
        this.imageByte = image;
    }

    public Volunteer() {

    }

    public Volunteer(Volunteer volunteer) {
        pid = volunteer.getPid();
        place = volunteer.getPlace();
        Pdescribtion = volunteer.getPdescribtion();
        numOfRegisteredVolunteers = volunteer.getNumOfRegisteredVolunteers();
        requiredSup = volunteer.getRequiredSup();
        requiredNumOfVolunteers = volunteer.getRequiredNumOfVolunteers();
        imageByte = volunteer.getImageByte();
    }
    //endregion

    //region Add,Delete,Update,Select Sql
    @Override
    public long Add(SQLiteDatabase db) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_VOLUNTEER_PLACE, place);
        values.put(COLUMN_PLACE_DESCRIPTION , Pdescribtion);
        values.put(COLUMN_REQUIRED_SUPPLIES, requiredSup);
        values.put(COLUMN_REGISTERED_VOLUNTEERS, numOfRegisteredVolunteers);
        values.put(COLUMN_NUM_OF_VOLUNTEERS, requiredNumOfVolunteers);
        values.put(COLUMN_PRODUCT_IMAGE, imageByte);


// Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_VOLUNTEER, null, values);

    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        String selection = BaseColumns._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {id+""};
// Issue SQL statement.
        return db.delete(TABLE_VOLUNTEER, selection, selectionArgs);

    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_VOLUNTEER_PLACE, place);
        values.put(COLUMN_PLACE_DESCRIPTION , Pdescribtion);
        values.put(COLUMN_REQUIRED_SUPPLIES, requiredSup);
        values.put(COLUMN_REGISTERED_VOLUNTEERS,numOfRegisteredVolunteers);
        values.put(COLUMN_NUM_OF_VOLUNTEERS, requiredNumOfVolunteers);
        values.put(COLUMN_PRODUCT_IMAGE, imageByte);

// Which row to update, based on the title
        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };

        return  db.update(
                TABLE_VOLUNTEER,
                values,
                selection,
                selectionArgs);

    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_VOLUNTEER_PLACE,
                COLUMN_PLACE_DESCRIPTION ,
                COLUMN_REQUIRED_SUPPLIES,
                COLUMN_PRODUCT_IMAGE,
                COLUMN_REGISTERED_VOLUNTEERS,
                COLUMN_NUM_OF_VOLUNTEERS,


        };
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";
        Cursor c = db.query(TABLE_VOLUNTEER,
                projection,
                null,
                null,
                null,
                null,
                null);
        return c;
    }
    public Cursor SelectById(SQLiteDatabase db,String id) {
        String[] projection = {
                BaseColumns._ID,
                COLUMN_VOLUNTEER_PLACE,
                COLUMN_PLACE_DESCRIPTION,
                COLUMN_REQUIRED_SUPPLIES,
                COLUMN_NUM_OF_VOLUNTEERS,
                COLUMN_PRODUCT_IMAGE,
                COLUMN_REGISTERED_VOLUNTEERS,
        };
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {id};

        Cursor c = db.query(
                TABLE_VOLUNTEER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null  );
        return c;
    }

    //endregion

    //region Setter and Getter
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getActName() {
        return place;
    }

    public void setActName(String place) {
        this.place = place;
    }

    public String getPdescribtion() {
        return Pdescribtion;
    }

    public void setPdescribtion(String Pdescribtion) {
        this.Pdescribtion = Pdescribtion;
    }
    public String getRequiredSup(){return requiredSup;}
    public void setRequiredSup(String requiredSup){this.requiredSup = requiredSup;}

    public double getRequiredNumOfVolunteers() {
        return requiredNumOfVolunteers;
    }

    public void setRequiredNumOfVolunteers(double requiredNumOfVolunteers) {
        this.requiredNumOfVolunteers = requiredNumOfVolunteers;
    }

    public double getNumOfRegisteredVolunteers() {
        return numOfRegisteredVolunteers;
    }

    public void setNumOfRegisteredVolunteers(double numOfRegisteredVolunteers) {
        this.numOfRegisteredVolunteers =numOfRegisteredVolunteers;
    }
    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }


    public byte[] getImageByte() {
        return imageByte;
    }
    @Override
    public String toString(){return place; }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    //endregion

}


package com.example.mariaproj.DataTables;
import com.example.mariaproj.DataTables.TablesString.*;
public class QueryString {


    //region Create Tables
    public static final String SQL_CREATE_PRODUCT =
            "CREATE TABLE " + VolunteerTable.TABLE_VOLUNTEER + " (" +
                    VolunteerTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    VolunteerTable.COLUMN_VOLUNTEER_PLACE + " TEXT," +
                    VolunteerTable.COLUMN_PLACE_DESCRIPTION  + " TEXT," +
                    VolunteerTable.COLUMN_REGISTERED_VOLUNTEERS+ " DOUBLE,"+
                    VolunteerTable.COLUMN_NUM_OF_VOLUNTEERS+ " DOUBLE,"+
                    VolunteerTable.COLUMN_PRODUCT_IMAGE + " BLOB,"+
                    VolunteerTable.COLUMN_REQUIRED_SUPPLIES + " Text);";



    public static final String SQL_CREATE_CART =
            "CREATE TABLE " + MemberTable.TABLE_MEMBER + " (" +
                    MemberTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MemberTable.COLUMN_VOLUNTEER_ID  + " INTEGER," +
                    MemberTable.COLUMN_USER_ID + " TEXT);";

    public static final String SQL_CREATE_SALE =
            "CREATE TABLE " + FinishedTable . TABLE_FINISHED  + " (" +
                    FinishedTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FinishedTable.COLUMN_VOLUNTEER_ID + " INTEGER," ;


    //endregions

    //region Delete Tables

    public static final String SQL_DELETE_PRODUCT =
            "DROP TABLE IF EXISTS " + VolunteerTable.TABLE_VOLUNTEER;

    public static final String SQL_DELETE_CART =
            "DROP TABLE IF EXISTS " + MemberTable.TABLE_MEMBER;

    public static final String SQL_DELETE_SALE =
            "DROP TABLE IF EXISTS " + FinishedTable.TABLE_FINISHED;

    //endregion
}

package com.example.mariaproj.DataTables;

import android.provider.BaseColumns;

public class TablesString {

    public TablesString() {
    }
    //region Product Table
    public static class VolunteerTable implements BaseColumns {
        public static final String TABLE_VOLUNTEER = "Volunteer";
        public static final String COLUMN_VOLUNTEER_PLACE = "Place";
        public static final String COLUMN_PLACE_DESCRIPTION = "Description";
        public static final String COLUMN_PRODUCT_IMAGE = "ProductImage";
        public static final String COLUMN_REGISTERED_VOLUNTEERS= "numOfRegisteredVolunteers";
        public static final String COLUMN_NUM_OF_VOLUNTEERS="requiredNumOfVolunteers";
        public static final String COLUMN_REQUIRED_SUPPLIES="requiredSup";

    }
    //endregion

    //region Cart Table
    public static class MemberTable implements BaseColumns {
        public static final String TABLE_MEMBER = "Members";
        public static final String COLUMN_VOLUNTEER_ID = "VID";
        public static final String COLUMN_USER_ID = "UID";

    }
    //endregion

    //region Sale Table
    public static class FinishedTable implements BaseColumns {
        public static final String TABLE_FINISHED = "Finished";
        public static final String COLUMN_VOLUNTEER_ID = "VID";

    }
    //endregion
}

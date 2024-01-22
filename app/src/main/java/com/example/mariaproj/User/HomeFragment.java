package com.example.mariaproj.User;

import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.*;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mariaproj.Class.Volunteer;
import com.example.mariaproj.Class.VolunteerPlacesAdapter;
import com.example.mariaproj.DataTables.DBHelper;
import com.example.mariaproj.R;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<Volunteer> VolunteerList;
    RecyclerView recyclerView;
    VolunteerPlacesAdapter mAdapter;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        VolunteerList = new ArrayList<>();
        dbHelper = new DBHelper(inflater.getContext());
        dbHelper = dbHelper.OpenReadAble();
        Volunteer p = new Volunteer(),volunteer=new Volunteer();
        Cursor c = p.Select(dbHelper.getDb());
        c.moveToFirst();
        while(!c.isAfterLast()){
            volunteer.setPid(c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID)));
            volunteer.setPlace(c.getString(c.getColumnIndexOrThrow(COLUMN_VOLUNTEER_PLACE)));
            volunteer.setPdescribtion(c.getString(c.getColumnIndexOrThrow(COLUMN_PLACE_DESCRIPTION)));
            volunteer.setRequiredSup(c.getString(c.getColumnIndexOrThrow(COLUMN_REQUIRED_SUPPLIES)));
            volunteer.setRequiredNumOfVolunteers(c.getDouble(c.getColumnIndexOrThrow(COLUMN_NUM_OF_VOLUNTEERS)));
            volunteer.setNumOfRegisteredVolunteers(c.getInt(c.getColumnIndexOrThrow(COLUMN_REGISTERED_VOLUNTEERS)));
            volunteer.setImageByte(c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)));
            VolunteerList.add(volunteer);
            c.moveToNext();
            volunteer = new Volunteer();
        }
        // adapter
        mAdapter = new VolunteerPlacesAdapter(inflater.getContext(), VolunteerList);
        recyclerView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return  view;
    }
}
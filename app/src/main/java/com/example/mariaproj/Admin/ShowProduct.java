package com.example.mariaproj.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.*;

import com.example.mariaproj.Class.ListAdapter;
import com.example.mariaproj.Class.*;
import com.example.mariaproj.DataTables.DBHelper;

import com.example.mariaproj.R;
import com.google.firebase.auth.FirebaseAuth;

public class ShowProduct extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView volunteerListview;
    Button addnew;
    String [] product_string ;
    DBHelper db;
    Volunteer volunteer;
    Volunteer[] place_info;
    Volunteer selected_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        volunteerListview = findViewById(R.id.lvproduct);
        volunteerListview.setOnItemClickListener(this);
        addnew = findViewById(R.id.btAddNewProd);
        addnew.setOnClickListener(this);
        db = new DBHelper(getApplicationContext());
        volunteer= new Volunteer();
        getProductToArray();
       // ListAdapter adapter = new ListAdapter(this,place_info);
        //volunteerListview.setAdapter(adapter);

    }
    public void getProductToArray(){
        db.OpenReadAble();
        Cursor c = volunteer.Select(db.getDb());
        if(c.getCount()>0){
            product_string = new String[c.getCount()];
            place_info =  new Volunteer[c.getCount()];
            int i =0;
            c.moveToFirst();
            while(!c.isAfterLast()){
                volunteer.setPid(c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID)));
                volunteer.setActName(c.getString(c.getColumnIndexOrThrow(COLUMN_VOLUNTEER_PLACE)));
                volunteer.setPdescribtion(c.getString(c.getColumnIndexOrThrow(COLUMN_PLACE_DESCRIPTION)));
                volunteer.setRequiredSup(c.getString(c.getColumnIndexOrThrow(COLUMN_REQUIRED_SUPPLIES)));
                volunteer.setRequiredNumOfVolunteers(c.getDouble(c.getColumnIndexOrThrow(COLUMN_NUM_OF_VOLUNTEERS)));
                volunteer.setNumOfRegisteredVolunteers(c.getInt(c.getColumnIndexOrThrow(COLUMN_REGISTERED_VOLUNTEERS)));
                volunteer.setImageByte(c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)));
                //String cat = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY));
               /* if(cat.equals("PC"))
                    product_info[i]=new PC(p);
                else if(cat.equals("LabTop"))
                    product_info[i]=new LabTop(p);
                else if(cat.equals("Printer"))
                    product_info[i]=new Printer(p);
                else
                    product_info[i]=new Other(p);*/
                // place_info[i]=new Volunteer(volunteer);
              //  product_string[i++] = volunteer.toString();
                //c.moveToNext();
            }
        }
        db.Close();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selected_product = place_info[i];
        Intent in = new Intent(this, AddVolunteerPlace.class);
        in.putExtra("Selected_Id", selected_product.getPid()+"");
        startActivity(in);
    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(this, AddVolunteerPlace.class);
        startActivity(in);
    }
    @Override
    public void onStop() {
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        fauth.signOut();
        super.onStop();
    }
}
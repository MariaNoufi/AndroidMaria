package com.example.mariaproj.User;

import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariaproj.Class.Member;
import com.example.mariaproj.Class.Volunteer;
import com.example.mariaproj.DataTables.DBHelper;
import com.example.mariaproj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProductInfo extends AppCompatActivity  {
    ImageView imageView;
    TextView  volunteerPlace,VPdescription;
    Button addmember;
    TextView  numOfRegisteredVolunteers, requiredNumOfVolunteers;
    DBHelper dbHelper;
    String selectedid;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        imageView = findViewById(R.id.imageViewInfo);
        volunteerPlace = findViewById(R.id.ProductNameInfo);
        addmember = findViewById(R.id.addtocart);
        VPdescription = findViewById(R.id.descriptioninfo);
        numOfRegisteredVolunteers = findViewById(R.id.Registered);
        requiredNumOfVolunteers = findViewById(R.id.Requiered);
        dbHelper = new DBHelper(this);
        selectedid = getIntent().getExtras().getString("id");
        Log.d("Product","123");
        setProduct();

        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCart();
            }
        });
    }
    private void setProduct() {
        Log.d("Product","123");
        dbHelper.OpenReadAble();
        Volunteer p=new Volunteer();
        c = p.SelectById(dbHelper.getDb(),selectedid);
        if(c!=null){
            c.moveToFirst();
            volunteerPlace.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_VOLUNTEER_PLACE)));
            VPdescription.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PLACE_DESCRIPTION)));
            numOfRegisteredVolunteers.setText(c.getInt(c.getColumnIndexOrThrow(COLUMN_REGISTERED_VOLUNTEERS))+"");
            requiredNumOfVolunteers.setText("Requiered : "+c.getInt(c.getColumnIndexOrThrow(COLUMN_NUM_OF_VOLUNTEERS)));
            byte[] image = c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));
            Bitmap bm = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            imageView.setImageBitmap(bm);
        }
        dbHelper.Close();

    }
    private void SaveCart() {
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser curruser = fauth.getCurrentUser();
        // getting the values from our views
        dbHelper.OpenWriteAble();
        Member member = new Member(Integer.parseInt(selectedid),curruser.getUid());
        if(!member.IsExist(dbHelper.getDb())){
            Volunteer v = setSelectedVolunteerNumOfRegister();
            member.Add(dbHelper.getDb());
            v.Update(dbHelper.getDb(),v.getPid());
            numOfRegisteredVolunteers.setText((int)(v.getNumOfRegisteredVolunteers())+"");
            dbHelper.Close();
            Toast.makeText(getBaseContext(), "Thank you "+curruser.getDisplayName()+" for joining our volunteer family", Toast.LENGTH_SHORT).show();
        }
       else
            Toast.makeText(getBaseContext(), "oops "+curruser.getDisplayName()+" looks like you have already registerd  ", Toast.LENGTH_SHORT).show();

    }
    private Volunteer setSelectedVolunteerNumOfRegister(){
        Volunteer v= new Volunteer();
        v.setPlace(c.getString(c.getColumnIndexOrThrow(COLUMN_VOLUNTEER_PLACE)));
        v.setImageByte(c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)));
        v.setPdescribtion(c.getString(c.getColumnIndexOrThrow(COLUMN_PLACE_DESCRIPTION)));
        v.setPid(Integer.parseInt(selectedid));
        v.setRequiredSup(c.getString(c.getColumnIndexOrThrow(COLUMN_REQUIRED_SUPPLIES)));
        v.setRequiredNumOfVolunteers(c.getInt(c.getColumnIndexOrThrow(COLUMN_NUM_OF_VOLUNTEERS)));
        v.setNumOfRegisteredVolunteers(c.getInt(c.getColumnIndexOrThrow(COLUMN_REGISTERED_VOLUNTEERS))+1);
        return v;
    }
}
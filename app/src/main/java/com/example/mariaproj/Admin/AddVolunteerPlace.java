package com.example.mariaproj.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mariaproj.Class.Volunteer;
import com.example.mariaproj.DataTables.DBHelper;
import com.example.mariaproj.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.COLUMN_NUM_OF_VOLUNTEERS;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.COLUMN_PLACE_DESCRIPTION;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.COLUMN_PRODUCT_IMAGE;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.COLUMN_REGISTERED_VOLUNTEERS;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.COLUMN_REQUIRED_SUPPLIES;
import static com.example.mariaproj.DataTables.TablesString.VolunteerTable.COLUMN_VOLUNTEER_PLACE;

public class AddVolunteerPlace extends AppCompatActivity implements View.OnClickListener {
    private static int RESULT_LOAD_IMAGE = 1;
    EditText etPlace,etPdescribtion,etrequiredSup,etrequiredNumOfVolunteers,etnumOfRegisteredVolunteers;
    ImageButton imageButton;
    Button btadd,btupdate,btdelete;
    Volunteer volunteer;
    Uri selectedImageUri;
    DBHelper dbHelper;
    String selectedId= " ";
    byte[] image;
    boolean SelectedNewImage = false;
    ProgressBar addItemProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_volunteer_place);
        etPlace = findViewById(R.id.etPlace);
        etPdescribtion = findViewById(R.id.etPdescribtion);
        etrequiredSup = findViewById(R.id.etrequiredSup);
        etrequiredNumOfVolunteers = findViewById(R.id.etrequiredNumOfVolunteers);
        etnumOfRegisteredVolunteers = findViewById(R.id.etnumOfRegisteredVolunteers);
        imageButton = findViewById(R.id.imageButton);
        btadd = findViewById(R.id.addButton);
        btadd.setOnClickListener(this);
        //change
        btupdate = findViewById(R.id.btUpdate);
        btupdate.setOnClickListener(this);
        btdelete = findViewById(R.id.btDelete);
        btdelete.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        imageButton.setOnClickListener(this);
        Intent i = getIntent();
        if(i.getStringExtra("Selected_Id")==null){
            btdelete.setVisibility(View.GONE);
            btupdate.setVisibility(View.GONE);
        }
        else {
            btadd.setVisibility(View.GONE);
            selectedId = i.getStringExtra("Selected_Id");
            setProduct();
        }
    }
    private void setProduct() {

        dbHelper.OpenReadAble();
        volunteer=new Volunteer();
        Cursor c = volunteer.SelectById(dbHelper.getDb(),selectedId);
        if(c!=null){
            c.moveToFirst();
            etPlace.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_VOLUNTEER_PLACE)));
            etPdescribtion.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PLACE_DESCRIPTION )));
            etrequiredSup.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_REQUIRED_SUPPLIES)));
            etrequiredNumOfVolunteers.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_NUM_OF_VOLUNTEERS)));
            etnumOfRegisteredVolunteers.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_REGISTERED_VOLUNTEERS)));
            image = c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));
            Bitmap bm = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            imageButton.setImageBitmap(bm);
        }
        dbHelper.Close();

    }
    public byte[] imageViewToByte() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() ,selectedImageUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addButton){
            dbHelper.OpenWriteAble();
            dbHelper = new DBHelper(this);

            byte[] data  = imageViewToByte();
            volunteer=new Volunteer(etPlace.getText().toString(),
                    etPdescribtion.getText().toString(),
                    etrequiredSup.getText().toString(),
                    Double.parseDouble(etrequiredNumOfVolunteers.getText().toString()),
                    Double.parseDouble(etnumOfRegisteredVolunteers.getText().toString()),data);
            dbHelper.OpenWriteAble();
            if(volunteer.Add(dbHelper.getDb())>-1){
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                dbHelper.Close();

                Intent i = new Intent(this,ShowProduct.class);
                startActivity(i);
            }

            if(view.getId()==R.id.btUpdate){
                volunteer.setPid(Integer.parseInt(selectedId));
                volunteer.setActName(etPlace.getText().toString());
                volunteer.setPdescribtion(etPdescribtion.getText().toString());
                volunteer.setRequiredSup(etrequiredSup.getText().toString());
                volunteer.setRequiredNumOfVolunteers(Double.parseDouble(etrequiredNumOfVolunteers.getText().toString()));
                volunteer.setNumOfRegisteredVolunteers(Double.parseDouble(etnumOfRegisteredVolunteers.getText().toString()));
                if(SelectedNewImage)
                    volunteer.setImageByte(imageViewToByte());
                else
                    volunteer.setImageByte(image);
                dbHelper.OpenWriteAble();
                volunteer.Update(dbHelper.getDb(),Integer.parseInt(selectedId));
                dbHelper.Close();
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,ShowProduct.class);
                startActivity(i);
            }
            if(view.getId()==R.id.btDelete){
                dbHelper.OpenWriteAble();
                volunteer.Delete(dbHelper.getDb(),Integer.parseInt(selectedId));
                dbHelper.Close();
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                 Intent i = new Intent(this,ShowProduct.class);
                 startActivity(i);
            }

        }
        if(view.getId()==R.id.imageButton){
            Intent gallery = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, RESULT_LOAD_IMAGE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            selectedImageUri = data.getData();
            imageButton.setImageURI(selectedImageUri);
            SelectedNewImage = true;
        }
    }
}
package com.example.mariaproj.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    EditText etPlace,etPdescribtion,etrequiredSup,etrequiredNumOfVolunteers,etnumOfRegisteredVolunteers;
    ImageButton imageButton;
    Button btadd;
    Volunteer p;
    Uri selectedImageUri;
    DBHelper dbHelper;
    ProgressBar addItemProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etPlace = findViewById(R.id.etPlace);
        etPdescribtion = findViewById(R.id.etPdescribtion);
        etrequiredSup = findViewById(R.id.etrequiredSup);
        etrequiredNumOfVolunteers = findViewById(R.id.etrequiredNumOfVolunteers);
        etnumOfRegisteredVolunteers = findViewById(R.id.etnumOfRegisteredVolunteers);
        imageButton = findViewById(R.id.imageButton);
        btadd = findViewById(R.id.addButton);
        btadd.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        addItemProgressBar=findViewById(R.id.addItemProgressBar);
        dbHelper = new DBHelper(this);
        dbHelper.OpenWriteAble();

    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addButton){
            addItemProgressBar.setVisibility(View.VISIBLE);
            dbHelper = new DBHelper(this);

            byte[] data  = imageViewToByte();
            p=new Volunteer(etPlace.getText().toString(),etPdescribtion.getText().toString(),
                    String.join(etrequiredSup.getText().toString()),
                    Double.parseDouble(etrequiredNumOfVolunteers.getText().toString()),
                    Double.parseDouble(etnumOfRegisteredVolunteers.getText().toString()),data);
            dbHelper.OpenWriteAble();
            if(p.Add(dbHelper.getDb())>-1){
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                dbHelper.Close();
            }

        }
        if(view.getId()==R.id.imageButton){
            Intent gallery = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, RESULT_LOAD_IMAGE);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            selectedImageUri = data.getData();
            imageButton.setImageURI(selectedImageUri);
        }
    }
}
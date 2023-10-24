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
        private void setProduct() {

            dbHelper.OpenReadAble();
            p=new Product();
            Cursor c = p.SelectById(dbHelper.getDb(),selectedId);
            if(c!=null){
                c.moveToFirst();
                etname.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
                etdisc.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)));
                etbuyprice.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE)));
                etsaleprice.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)));
                etstock.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_STOCK)));
                image = c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));
                Bitmap bm = BitmapFactory.decodeByteArray(image, 0 ,image.length);
                imageButton.setImageBitmap(bm);
            }
            dbHelper.Close();

        }
        }

    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addButton){
            dbHelper.OpenWriteAble();
            addItemProgressBar.setVisibility(View.VISIBLE);
            dbHelper = new DBHelper(this);

            byte[] data  = imageViewToByte();
            p=new Volunteer(etPlace.getText().toString(),etPdescribtion.getText().toString(),
                    Integer.parseInt(etrequiredSup.getText().toString()),
                    Double.parseDouble(etrequiredNumOfVolunteers.getText().toString()),
                    Double.parseDouble(etnumOfRegisteredVolunteers.getText().toString()),data);
            dbHelper.OpenWriteAble();
            if(p.Add(dbHelper.getDb())>-1){
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                dbHelper.Close();

                Intent i = new Intent(this,ShowProduct.class);
                startActivity(i);
            }

            if(view.getId()==R.id.btUpdate){
                p.setPid(Integer.parseInt(selectedId));
                p.setProdname(etname.getText().toString());
                p.setProddisc(etdisc.getText().toString());
                p.setBuyprice(Double.parseDouble(etbuyprice.getText().toString()));
                p.setSaleprice(Double.parseDouble(etsaleprice.getText().toString()));
                p.setStock(Integer.parseInt(etstock.getText().toString()));
                if(SelectedNewImage)
                    p.setImageByte(imageViewToByte());
                else
                    p.setImageByte(image);
                dbHelper.OpenWriteAble();
                p.Update(dbHelper.getDb(),selectedId);
                dbHelper.Close();
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,ShowProduct.class);
                startActivity(i);
            }
            if(view.getId()==R.id.btDelete){
                dbHelper.OpenWriteAble();
                p.Delete(dbHelper.getDb(),selectedId);
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
            SelectedNewImage = true;
        }
    }
}
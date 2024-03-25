package com.example.mariaproj.User;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariaproj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DonateFragment extends Fragment {
    ImageView donateimage;
    ConstraintLayout donations;
    CheckBox oneh,twoh,threeh;
    EditText other;
    Button subbutton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donate, container, false);
        donateimage = v.findViewById(R.id.ivDonate);
        oneh = v.findViewById(R.id.oneH);
        twoh = v.findViewById(R.id.twoH);
        threeh = v.findViewById(R.id.threeH);
        other = v.findViewById(R.id.youChoose);
        subbutton = v.findViewById(R.id.submit);
        subbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = 0;
                FirebaseAuth fauth = FirebaseAuth.getInstance();
                FirebaseUser curruser = fauth.getCurrentUser();
                if(oneh.isChecked())sum+=100;
                if(twoh.isChecked())sum+=200;
                if(threeh.isChecked())sum+=300;
                if(!other.getText().toString().matches(""))sum += Integer.parseInt(other.getText().toString());
                Toast.makeText(getContext(), "Thank you "+curruser.getDisplayName()+" for your donate "+sum, Toast.LENGTH_SHORT).show();
            }
        });
        donations = v.findViewById(R.id.donate);
        donateimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                donations.setVisibility(View.VISIBLE);
            }
        });


        return v;
    }

}
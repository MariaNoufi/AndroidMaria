package com.example.mariaproj.User;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mariaproj.R;

public class DonateFragment extends Fragment {
    ImageView donateimage;
    ConstraintLayout donations;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donate, container, false);
        donateimage = v.findViewById(R.id.ivDonate);
        donations = v.findViewById(R.id.donate);
        donateimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donations.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }
}
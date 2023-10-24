package com.example.mariaproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    EditText NameT;
    EditText emailT;
    EditText PasswordT;
    EditText PasswordCT;
    Button RegisterBT;
    TextView errorT;
    EditText admincode;
    Switch isadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        NameT=findViewById(R.id.etName);
        emailT=findViewById(R.id.etEmailUser);
        PasswordT=findViewById(R.id.etPass);
        PasswordCT=findViewById(R.id.etRePass);
        RegisterBT=findViewById(R.id.btRegister);
        errorT=findViewById(R.id.tvErrorMsg);
        errorT.setVisibility(View.GONE);
        RegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }
    private void createNewAccount(){
        errorT.setVisibility(View.GONE);
        if(NameT.getText().toString().equals("")){
            errorT.setVisibility(View.VISIBLE);
            errorT.setText("please enter your full name");
            return;
        }
        if(emailT.getText().toString().equals("")){
            errorT.setVisibility(View.VISIBLE);
            errorT.setText("please enter your email");
            return;
        }
        if(PasswordT.getText().toString().equals("")){
            errorT.setVisibility(View.VISIBLE);
            errorT.setText("please enter your password");
            return;
        }
        if(PasswordCT.getText().toString().equals("")){
            errorT.setVisibility(View.VISIBLE);
            errorT.setText("please confirm your password");
            return;
        }
        if(!PasswordCT.getText().toString().equals(PasswordT.getText().toString())){
            errorT.setVisibility(View.VISIBLE);
            errorT.setText("password dosn't match");
            return;
        }
        if(isadmin.isChecked() && !admincode.getText().toString().equals("13579")) {
            errorT.setVisibility(View.VISIBLE);
            errorT.setText("admin code is incorrect");
            return;
        }
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailT.getText().toString(),PasswordT.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            String name=fullNameEditText.getText().toString();
                            if(isadmin.isChecked()){
                                name = "admin: "+name;
                            }
                            UserProfileChangeRequest  profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                            }
                                        }
                                    });
                        } else {

                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            errorT.setVisibility(View.VISIBLE);
                            errorT.setText(task.getException().getMessage());

                        }
                    }
                });

    }
}
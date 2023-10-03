package com.example.mariaproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    EditText ETemail;
    EditText ETpassword;
    Button BTsubmit;
    TextView CreateANewAccount,error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ETemail=findViewById(R.id.ETemail);
        ETpassword=findViewById(R.id.ETpassword);
        BTsubmit=findViewById(R.id.BTsubmit);
        error =findViewById(R.id.TVerror);
        error.setVisibility(View.GONE);
        CreateANewAccount=findViewById(R.id.CreateANewAccount);
        CreateANewAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            }
        });
        BTsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseAuth mAuth=FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(ETemail.getText().toString(), ETpassword.getText().toString())
                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    startActivity(new Intent(LogInActivity.this,MainActivity.class));

                                } else {
                                    error.setVisibility(View.VISIBLE);

                                    Log.d("error","erro");

                                }
                            }
                        });
            }
        });
    }
}
package com.example.mariaproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LogInActivity extends AppCompatActivity {
    EditText ETemail;
    EditText ETpassword;
    Button BTsubmit;
    TextView CreateANewAccout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ETemail=findViewById(R.id.ETemail);
        ETpassword=findViewById(R.id.ETpassword);
        BTsubmit=findViewById(R.id.BTsubmit);
        CreateANewAccout=findViewById(R.id.CreateANewAccout);
        newAccountButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CreateNewAcountActivity.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseAuth mAuth=FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                } else {

                                    Log.d("error","erro");

                                }
                            }
                        });
            }
        });
    }
}
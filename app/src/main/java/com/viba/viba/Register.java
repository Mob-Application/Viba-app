package com.viba.viba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
    private String name, email, password, mobilenumber;
    EditText mfullname, memail, mpassword, mmobilenumber;
    Button mRegisterbtn;
    ImageView img;
    String emailpattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String passwordvalidate = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mfullname = (EditText) findViewById(R.id.fullname);
        mmobilenumber = (EditText) findViewById(R.id.mobilenumber);
        memail = (EditText) findViewById(R.id.emai);
        mpassword = (EditText) findViewById(R.id.password);
        mRegisterbtn = (Button) findViewById(R.id.button2);
        img = (ImageView) findViewById(R.id.imageView14);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = memail.getText().toString().trim();
                password = mpassword.getText().toString().trim();
                name = mfullname.getText().toString().trim();
                mobilenumber = mmobilenumber.getText().toString().trim();

                if (memail.getText().toString().trim().matches(emailpattern) && mpassword.getText().toString().trim().matches(passwordvalidate)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        sendemail();

                                    } else {
                                        Toast.makeText(Register.this, "Authentication Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "invalid email address (or) Weak Password", Toast.LENGTH_SHORT).show();
                }
            }


        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    public void login(){
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }


    private void sendemail() {
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            //Toast.makeText(getApplicationContext(), "test1", Toast.LENGTH_LONG).show();
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //Toast.makeText(getApplicationContext(), "test1", Toast.LENGTH_LONG).show();
                        myRef = myRef.child(mAuth.getUid());
                        items item;
                        item = new items(name, email, password, mobilenumber);
                        myRef.setValue(item);
                        Toast.makeText(getApplicationContext(), "Successfully registered, VERIFY YOUR MAIL AND LOGIN", Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Verification mail failed to send try after sometime", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}

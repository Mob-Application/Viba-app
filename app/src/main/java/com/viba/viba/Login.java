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

public class Login extends AppCompatActivity {
    ImageView image;
    EditText memail, mpassword;
    Button mloginbtn;
    String emailpattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String passwordvalidate = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        image = (ImageView) findViewById(R.id.imageView5);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        memail = (EditText) findViewById(R.id.emai);
        mpassword = (EditText) findViewById(R.id.password);
        mloginbtn = (Button) findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "user created" + task.getException(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            } else {
                                Toast.makeText(Login.this, "Authentication Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                } else {
                    Toast.makeText(Login.this, "enter all the keys", Toast.LENGTH_SHORT).show();
                }

            }


        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }
    public  void register(){
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }
}




package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_up extends AppCompatActivity {
    TextView alreadyHaveaccount;
    EditText nom,prenom,password,mail;
    Button send;
    String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;
    FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveaccount=findViewById(R.id.singupText);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        password = findViewById(R.id.password);
        mail = findViewById(R.id.mail);
        send = findViewById(R.id.send);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");




        alreadyHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sing_up.this, Login.class));
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String email = mail.getText().toString();
                String pass = password.getText().toString();
                String name = nom.getText().toString();
                String lastname = prenom.getText().toString();

                HelperClass helperClass = new HelperClass(name,lastname,email,pass,FirebaseAuth.getInstance().getUid());
                reference.child(FirebaseAuth.getInstance().getUid()).setValue(helperClass);
                PerforAuth();
            }

        });

    }



    private void PerforAuth() {
        String email = mail.getText().toString();
        String pass = password.getText().toString();

        if(!email.matches(emailPattern)){
            mail.setError(" Entrez l'mail");
        }else if(pass.isEmpty()||pass.length()<8){
            password.setError("Entrez un pass correcte");
        }else{
            progressDialog.setMessage("Wait Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Sing_up.this, "Inscription reussi", Toast.LENGTH_LONG);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Sing_up.this, ""+task.getException(),Toast.LENGTH_SHORT);
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(Sing_up.this, Login.class);
       intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
       startActivity(intent);
    }
}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    TextView create;
    EditText password, mail;
    Button sendlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        create = findViewById(R.id.create);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        password = findViewById(R.id.password);
        mail = findViewById(R.id.mail);
        progressDialog = new ProgressDialog(this);
        sendlogin = findViewById(R.id.send);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Sing_up.class));
            }
        });

        sendlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforlogin();
            }
        });
    }

    private void perforlogin() {
        String email = mail.getText().toString();
        String pass = password.getText().toString();


        if (!email.matches(emailPattern)) {
            mail.setError(" Entrez l'mail");
        } else if (pass.isEmpty() || pass.length() < 8) {
            password.setError("Entrez un pass correcte");
        } else {
            progressDialog.setMessage("Please wait while login...");
            progressDialog.setTitle("login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Login.this, "Login reussi", Toast.LENGTH_LONG);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "" + task.getException(), Toast.LENGTH_SHORT);
                    }
                }
            });

        }

    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}


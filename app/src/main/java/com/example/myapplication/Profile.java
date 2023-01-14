package com.example.myapplication;

import static com.example.myapplication.R.layout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Profile extends AppCompatActivity {
    ImageView ivqr;
    TextView mail;
    TextView nom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_profile);
        ivqr=findViewById(R.id.code);

        Query reference = FirebaseDatabase.getInstance().getReference("users").orderByChild("id").equalTo(FirebaseAuth.getInstance().getUid());

         mail = findViewById(R.id.mail);
        nom = findViewById(R.id.name);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String nom1 = snapshot.child(FirebaseAuth.getInstance().getUid()).child("nom").getValue(String.class);
                    String mail1 = snapshot.child(FirebaseAuth.getInstance().getUid()).child("mail").getValue(String.class);
                    nom.setText(nom1);
                    mail.setText(mail1);
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix matrix = writer.encode(nom1+" "+mail1, BarcodeFormat.QR_CODE,600,600);

                        BarcodeEncoder encoder = new BarcodeEncoder();
                        Bitmap bitmap = encoder.createBitmap(matrix);
                        ivqr.setImageBitmap(bitmap);


                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else
                {

                    Toast.makeText(Profile.this,"user does'n exist",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}
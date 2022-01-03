package com.example.examentp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText Nom , Cinema, Date , Image;
    Button btnSave , btnBack ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Nom= (EditText) findViewById(R.id.addtxtName);
        Cinema= (EditText) findViewById(R.id.addcinemaName);
        Date= (EditText) findViewById(R.id.addtxtdate);
        Image= (EditText) findViewById(R.id.addimageurl);


        btnSave= (Button) findViewById(R.id.btnsave);
        btnBack = (Button) findViewById(R.id.btnback);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }
    private void insertData(){
        Map<String,Object>map= new HashMap<>();
        map.put("Nom",Nom.getText().toString());
        map.put("Cinema",Cinema.getText().toString());
        map.put("Date",Date.getText().toString());
        map.put("Image",Image.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("films").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Succefully ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
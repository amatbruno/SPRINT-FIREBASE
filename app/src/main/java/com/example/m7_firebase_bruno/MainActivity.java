package com.example.m7_firebase_bruno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRefCode = database.getReference("CODE");
    EditText codeTxt, usernameTxt;
    Button btnValidate, btnLogin;

    //String getCode = codeTxt.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("\tSABER Y GANAR II");

        //DATA BINDING
        usernameTxt = findViewById(R.id.txtUsername);
        codeTxt = findViewById(R.id.txtCode);
        btnValidate = findViewById(R.id.buttonValidate);
        btnLogin = findViewById(R.id.buttonLogin);

        //PRUEBA_myRef.child("/USERS/User1/1").setValue("Hola");

        //ONCLICK BUTTON INSERT USER
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointNewUser();
            }
        });

        //ONCLICK BUTTON VALIDATE CODE
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
    }

    //FUNCTION TO APPOINT A NEW USER
    public void appointNewUser() {
        String getUsername = usernameTxt.getText().toString();
        myRef.child("/USERS/User1/").setValue(getUsername);
    }

    //FUNCTION TO GET THE CODE FROM
    public void getCode() {
        myRef.child("CODES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String code = codeTxt.getText().toString();
                String code2 = dataSnapshot.getValue(String.class);
                assert code2 != null;
                if (code2.equals(code)) {
                    Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
                    btnLogin.setVisibility(View.VISIBLE);
                    usernameTxt.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "POZO", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
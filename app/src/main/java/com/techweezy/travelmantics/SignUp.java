package com.techweezy.travelmantics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class SignUp extends AppCompatActivity {
    EditText name, email,password;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=(EditText)findViewById(R.id.nameET);
        email=(EditText)findViewById(R.id.emailET);
        password=(EditText)findViewById(R.id.passET);

    }

    public void registerMethod(View view) {
        String fullname_str=name.getText().toString().trim();
        String email_str= email.getText().toString().trim();
        String pass_str=password.getText().toString().trim();

        if (fullname_str.isEmpty() ||email_str.isEmpty() ||pass_str.isEmpty()){
            Toast.makeText(this, "fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}

package com.mg3.guttiapp18;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewRegister;

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonSignIn;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonSignIn = findViewById(R.id.btnSignIn);

        mAuth=FirebaseAuth.getInstance();

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });


        mTextViewRegister = findViewById(R.id.TextViewRegister);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


    }

    private void login() {

        String email=mTextInputEmail.getText().toString();
        String password=mTextInputPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);

                    
                } else{
                    Toast.makeText(MainActivity.this, "El email y la contrase??a no son correctos", Toast.LENGTH_SHORT).show();
                        
                }

            }
        });

        Log.d("Campo","email"+email);
        Log.d("Campo","password"+password);


    }
}
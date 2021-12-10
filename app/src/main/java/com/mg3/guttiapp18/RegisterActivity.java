package com.mg3.guttiapp18;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewback;

    TextInputEditText mTextInputEditTextUserName;
    TextInputEditText mTextInputEditTextEmailR;
    TextInputEditText mTextInputEditTextPasswordR;
    TextInputEditText mTextInputEditTextCPassword;
    Button mButtonRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextInputEditTextUserName=findViewById(R.id.textInputUserName);
        mTextInputEditTextEmailR=findViewById(R.id.textInputEmailR);
        mTextInputEditTextPasswordR=findViewById(R.id.textInputPasswordR);
        mTextInputEditTextCPassword=findViewById(R.id.textInputCPassword);
        mButtonRegister=findViewById(R.id.btnRegister);

        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });



        mCircleImageViewback = findViewById(R.id.CircleImageViewback);
        mCircleImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }

    private void register() {

        String username =mTextInputEditTextUserName.getText().toString();
        String email =mTextInputEditTextEmailR.getText().toString();
        String password =mTextInputEditTextPasswordR.getText().toString();
        String cpassword = mTextInputEditTextCPassword.getText().toString();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !cpassword.isEmpty()){

            if (isEmailValid(email)) {

                if (password.equals(cpassword)) {
                    if (password.length() >= 6) {
                        createUser(username, email, password);
                    } else {
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Has insertado todos los campos pero el correo no es valido", Toast.LENGTH_SHORT).show();

            }


        }
        else {
                Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    private void createUser(final String username, final String email, String password ) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("username", username);
                    map.put("password", password);
                    mFirestore.collection("Users").document(id).set(map);

                    Toast.makeText(RegisterActivity.this, "el usuario se almaceno correctamente en la base de datos", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(RegisterActivity.this, "no se pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }



    /*Verificar si un email es valido*/
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
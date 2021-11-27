package com.example.myassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myassignment.database.UserDao;
import com.example.myassignment.database.UserDatabase;
import com.example.myassignment.database.UserEntity;

public class SignUpActivity extends AppCompatActivity {
    EditText emailBox, passwordBox;
    Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailBox = findViewById(R.id.emailEditText);
        passwordBox = findViewById(R.id.PasswordEditText);


        loginBtn = findViewById(R.id.login_page_btn);
        signupBtn = findViewById(R.id.registerbtn);

        signupBtn.setOnClickListener(view -> {

            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(emailBox.getText().toString());
            userEntity.setPassword(passwordBox.getText().toString());

            if(validateInput(userEntity)){

                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                UserDao userDao = userDatabase.userDao();
                new Thread(() -> {
                    userDao.registerUser(userEntity);
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(),"User Registered!", Toast.LENGTH_SHORT).show());
                }).start();
            }
            else{
                Toast.makeText(getApplicationContext(),"fill all fields carefully", Toast.LENGTH_SHORT).show();
            }
        });

        loginBtn.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, MainActivity.class)));
    }

    private Boolean validateInput(UserEntity userEntity) {
        return !userEntity.getUserId().isEmpty() &&
                !userEntity.getPassword().isEmpty();
    }
}
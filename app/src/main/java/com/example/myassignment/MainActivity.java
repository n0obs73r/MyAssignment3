package com.example.myassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myassignment.database.UserDao;
import com.example.myassignment.database.UserDatabase;
import com.example.myassignment.database.UserEntity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailBox, passwordBox;
    Button loginBtn, signupBtn;
    private SignInButtonImpl signInButton;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButtonImpl signInButton = findViewById(R.id.google);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(view -> {
            String userIdText = emailBox.getText().toString();
            String passwordText = passwordBox.getText().toString();
            if (userIdText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();



            } else {
                startActivity(new Intent(MainActivity.this, CallActivity.class));
                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                UserDao userDao = userDatabase.userDao();
                new Thread(() -> {
                    UserEntity userEntity = userDao.login(userIdText, passwordText);
                    if(userEntity == null)
                    {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show());
                    }else{
                        startActivity(new Intent(MainActivity.this, CallActivity.class).putExtra("email", userIdText));

                    }
                }).start();

            }

        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("903540655909-6sevc93jom2gg5snnm7a3ql7hcpe79p8.apps.googleusercontent.com")
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivity(signInIntent);
            }
        });

        signInButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CallActivity.class)));


        signupBtn = findViewById(R.id.createBtn);

        signupBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SignUpActivity.class)));


    }


}
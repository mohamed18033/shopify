package com.example.shopifygp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopifygp.Database.User;
import com.example.shopifygp.Database.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // Creating Objects
    EditText emailTextBox;
    EditText passwordTextBox;
    TextView signupBtn;
    Button loginBtn;
    ProgressBar progressBar;


    FirebaseDatabase database;
    DatabaseReference userRef;

    FirebaseAuth mAuth;

    User loggedInUser;

    private UserDao userDao = MyApplication.getUserDao();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this, NavHostActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance("https://shopifygp-70897-default-rtdb.firebaseio.com/");
        userRef = database.getReference("User");

        // Getting Views
        emailTextBox = (EditText) findViewById(R.id.emailTextBox);
        passwordTextBox = (EditText) findViewById(R.id.passwordTextBox);
        signupBtn = (TextView) findViewById(R.id.signup);
        loginBtn = (Button) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);

                startActivity(intent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email;
                String password;

                email = emailTextBox.getText().toString();
                password = passwordTextBox.getText().toString();

                // If email box is empty return
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Email is empty",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // If password bos is empty return
                if(TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Password is empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()) {
                                    FirebaseUser firebaseUser = task.getResult().getUser();
                                    userRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            loggedInUser = snapshot.getValue(User.class);
                                            userDao.insert(loggedInUser);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    Intent intent = new Intent(LoginActivity.this, NavHostActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
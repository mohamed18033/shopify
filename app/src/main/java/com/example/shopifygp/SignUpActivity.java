package com.example.shopifygp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopifygp.Database.User;
import com.example.shopifygp.Database.UserDao;
import com.example.shopifygp.Database.UserDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    // Creating Objects
    EditText emailTextBox;
    EditText passwordTextBox;
    EditText fullNameTextBox;
    EditText phoneTextBox;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    Button signupBtn;
    TextView loginBtn;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference userRef;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance("https://shopifygp-70897-default-rtdb.firebaseio.com/");
        userRef = database.getReference("User");

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Getting Views
        emailTextBox = (EditText) findViewById(R.id.emailTextBox);
        passwordTextBox = (EditText) findViewById(R.id.passwordTextBox);
        fullNameTextBox = (EditText) findViewById(R.id.fullNameTextBox);
        phoneTextBox = (EditText) findViewById(R.id.phoneTextBox);
        radioGroupGender = findViewById(R.id.radio_group_gender);
        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);
        signupBtn = (Button) findViewById(R.id.signup);
        loginBtn = (TextView) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Sign Up Button On Click
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email;
                String password;
                String fullName;
                String gender = "";
                String phoneNumber;

                email = emailTextBox.getText().toString();
                password = passwordTextBox.getText().toString();
                fullName = fullNameTextBox.getText().toString();
                phoneNumber = phoneTextBox.getText().toString();

                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedId == radioButtonMale.getId()) {
                    gender = "Male";
                } else if (selectedId == radioButtonFemale.getId()) {
                    gender = "Female";
                }
                // If email box is empty return
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "Email is empty",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // If password box is empty return
                if(TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Password is empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // If Full Name box is empty return
                if(TextUtils.isEmpty(fullName)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Full Name is empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // If gender is empty return
                if(TextUtils.isEmpty(gender)){
                    Toast.makeText(SignUpActivity.this, "Please select your gender",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // If phone is empty return
                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(SignUpActivity.this, "Please enter your phone number",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // Create new user in firebase
                String finalGender = gender;
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(fullName).build();
                                    FirebaseUser firebaseUser = task.getResult().getUser();
                                    firebaseUser.updateProfile(userProfileChangeRequest);
                                    Toast.makeText(SignUpActivity.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();


                                    // Store date locally in ROOM Database
                                    User user = new User(email, fullName, phoneNumber, finalGender);
                                    userRef.child(firebaseUser.getUid()).setValue(user);


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }


        });

        // Login Button On Click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}
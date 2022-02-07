package com.example.bluetoothchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signUp extends AppCompatActivity {

    private Button regButton;
    private EditText regUser,regEmail,regPass;
    private ProgressBar progressBar;
    private ImageButton imageButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regButton = (Button)findViewById(R.id.registerButtonId);
        regUser = (EditText)findViewById(R.id.usernameId);
        regEmail = (EditText)findViewById(R.id.emailId);
        regPass = (EditText)findViewById(R.id.passwordId);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        progressBar = (ProgressBar)findViewById(R.id.progressBarId);


        mAuth = FirebaseAuth.getInstance();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signUp.this,LoginPage.class);
                startActivity(i);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = regUser.getText().toString().trim();
                String Email = regEmail.getText().toString().trim();
                String Pass = regPass.getText().toString().trim();

                if(User.isEmpty()){
                    regUser.setError("Provide your name!");
                    regUser.requestFocus();
                    return;
                }
                if(Email.isEmpty()){
                    regEmail.setError("Enter your email address!");
                    regEmail.requestFocus();
                    return;
                }
                if(Pass.isEmpty()){
                    regPass.setError("Enter your password!");
                    regPass.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    regEmail.setError("Enter your valid email address!");
                    regEmail.requestFocus();
                    return;
                }
                if(Pass.length() < 6){
                    regPass.setError("Your password must be at least 6 digit!");
                    regPass.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(Email, Pass)
                        .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(signUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent i = new Intent(signUp.this,mainChatPage.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(signUp.this, "User is already registered", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(signUp.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }
                        });

            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
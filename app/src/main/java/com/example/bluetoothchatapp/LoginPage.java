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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginPage extends AppCompatActivity {

    TextView signupView,resetView;
    Button loginButton;
    ProgressBar progressBar;
    EditText loginEmail,loginPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signupView = (TextView)findViewById(R.id.textView5);
        resetView = (TextView)findViewById(R.id.textView4);
        loginButton = (Button)findViewById(R.id.loginButtonId);
        progressBar = (ProgressBar)findViewById(R.id.progressBarId2);
        loginEmail = (EditText)findViewById(R.id.loginEmailId);
        loginPass = (EditText)findViewById(R.id.loginPasswordId);

        mAuth = FirebaseAuth.getInstance();

        signupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPage.this,signUp.class);
                startActivity(i);
            }
        });

        resetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPage.this,resetPassword.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Email = loginEmail.getText().toString().trim();
                String Pass = loginPass.getText().toString().trim();

                if(Email.isEmpty()){
                    loginEmail.setError("Enter your email address!");
                    loginEmail.requestFocus();
                    return;
                }

                if(Pass.isEmpty()){
                    loginPass.setError("Enter your password!");
                    loginPass.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    loginEmail.setError("Enter your valid email address!");
                    loginEmail.requestFocus();
                    return;
                }
                if(Pass.length() < 6){
                    loginPass.setError("Your password must be at least 6 digit!");
                    loginPass.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(Email, Pass)
                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPage.this, "You Are Logged In", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent i = new Intent(LoginPage.this,mainChatPage.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                } else {

                                        Toast.makeText(LoginPage.this, "Login not successful", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
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
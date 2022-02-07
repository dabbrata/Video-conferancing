package com.example.bluetoothchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class secretCodeVideoConference extends AppCompatActivity {

    Button joinButton;
    ImageButton imageButton;
    EditText secretCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_code_video_conference);

        imageButton = (ImageButton)findViewById(R.id.imageButton3);
        joinButton = (Button)findViewById(R.id.button);
        secretCode = (EditText)findViewById(R.id.editTextsecretId);

        URL serverURL;
        try{
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = secretCode.getText().toString().trim();
                if(code.isEmpty()){
                    secretCode.setError("Provide secret code with above formated");
                    secretCode.requestFocus();
                    return;
                }
                if(code.length() < 6){
                    secretCode.setError("Length of code must be at least 6!");
                    secretCode.requestFocus();
                    return;
                }

                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretCode.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();
                JitsiMeetActivity.launch(secretCodeVideoConference.this,options);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(secretCodeVideoConference.this,mainChatPage.class);
                startActivity(i);
            }
        });

    }
}
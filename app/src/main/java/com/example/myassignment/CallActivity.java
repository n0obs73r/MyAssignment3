package com.example.myassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class CallActivity extends AppCompatActivity {
    Button button,join;
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        button=findViewById(R.id.button);
        join=findViewById(R.id.joinBtn);
        code=findViewById(R.id.codeBox);
        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }





        join.setOnClickListener(v -> {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(code.getText().toString())
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeetActivity.launch(CallActivity.this, options);
        });
        button.setOnClickListener(v -> {

          finish();
          onDestroy();
            startActivity(new Intent(CallActivity.this, MainActivity.class));

        });
    }
}
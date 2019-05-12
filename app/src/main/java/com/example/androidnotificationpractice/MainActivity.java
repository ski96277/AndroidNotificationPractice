package com.example.androidnotificationpractice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {

    // 1. Notification channel
    // 2. Notification Builder
    // 3. Notification manger

    public static final String CHANNEL_ID = "imran_sk";
    private static final String CHANNEL_NAME = "imran sk";
    private static final String CHANNEL_description = "Notification Test";

    EditText email_ET, pass_ET;
    Button button_login_OR_Signup;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check the version is greater than  oreo or equal Oreo  START
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //check the version is greater than  oreo or equal Oreo END

        mAuth = FirebaseAuth.getInstance();
        email_ET = findViewById(R.id.emailET_ID);
        pass_ET = findViewById(R.id.passET_ID);
        button_login_OR_Signup = findViewById(R.id.button_ID);
        progressBar = findViewById(R.id.progressBar_ID);

        button_login_OR_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = email_ET.getText().toString();
                final String pass = pass_ET.getText().toString();

                if (email.isEmpty()) {
                    email_ET.setError("enter email");
                    email_ET.setFocusable(true);
                    return;
                }
                if (pass.isEmpty()) {
                    pass_ET.setError("enter pass");
                    pass_ET.setFocusable(true);
                    return;
                }
                if (pass.length() < 5) {
                    pass_ET.setError("pass should be 6 charterer ");
                    pass_ET.setFocusable(true);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    start_ProfileActivity();
                                    progressBar.setVisibility(View.GONE);

                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        userLogin(email, pass);

                                    } else {
                                        Toast.makeText(MainActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                    }


                                }

                                // ...
                            }
                        });


            }
        });


    }

    private void userLogin(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            start_ProfileActivity();
                            progressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(MainActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void start_ProfileActivity() {
        Intent intent = new Intent(MainActivity.this, Profile_Activity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            start_ProfileActivity();
        }
    }
    /*public void Notyfy(View view) {
        display_notification();
    }*/


}

package com.example.androidnotificationpractice;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class Profile_Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final String NODE_USERS = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        //get the unique Token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (task.isSuccessful()) {

                            String token = task.getResult().getToken();
                            saveToken(token);
                        } else {

                        }
                    }

                });
        //get the unique Token END
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(Profile_Activity.this, MainActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void saveToken(String token) {
        String email = firebaseAuth.getCurrentUser().getEmail();

        Users users = new Users(email, token);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(NODE_USERS);
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Profile_Activity.this, "Token Save", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

   /* public void send_notification(View view) {
sentToNotification();
    }

    private void sentToNotification() {


        String to = "fKCEcM0jI0U:APA91bFUS1Zwo2-Ce3B8YRZAjCt3t3DXC5tG3FXrI8Bc-UyucJbUvuh-Fx6Ngj0ZaN5Y8T3F6FvEI28atDl1iL48ZvpmUdmb4lAOUnYZ_p55YcGIQ1F068QVqIgnl9-BsnSmTvtawKCR";

        String collapseKey = "green";
        Notification notification = new Notification("Hello bro", "title23");
        Data data = new Data("Hello2", "title2", "key1", "key2");
        Message notificationTask = new Message(to, collapseKey, notification, data);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")//url of FCM message server
                .addConverterFactory(GsonConverterFactory.create())//use for convert JSON file into object
                .build();

        ServiceAPI api = retrofit.create(ServiceAPI.class);

        Call<Message> call = api.sendMessage("key=AIzaSyAJa09OyxZfth5AL2LXZFKQ4W8s8DUzH8o", notificationTask);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                Log.e("TAG", t.getMessage());
            }
        });
    }

    public class Message {

        private String to;
        private String collapseKey;
        private Notification notification;
        private Data data;

        public Message(String to, String collapseKey, Notification notification, Data data) {
            this.to = to;
            this.collapseKey = collapseKey;
            this.notification = notification;
            this.data = data;
        }
    }

    public class Data {

        private String body;
        private String title;
        private String key1;
        private String key2;

        public Data(String body, String title, String key1, String key2) {
            this.body = body;
            this.title = title;
            this.key1 = key1;
            this.key2 = key2;
        }
    }

    public class Notification {

        private String body;
        private String title;

        public Notification(String body, String title) {
            this.body = body;
            this.title = title;
        }
    }*/

}

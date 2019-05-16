package com.example.androidnotificationpractice;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

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

    private List<Users> userList;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressBar = findViewById(R.id.progressbar);
        firebaseAuth = FirebaseAuth.getInstance();
        loadUsers();

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

    private void loadUsers(){
        progressBar.setVisibility(View.VISIBLE);
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if(dataSnapshot.exists()){

                    for(DataSnapshot dsUser: dataSnapshot.getChildren()){

                        Users user = dsUser.getValue(Users.class);
                        userList.add(user);

                    }
                    Log.e("TAG user", "onDataChange: "+userList.size() );

                    UserAdapter adapter = new UserAdapter(Profile_Activity.this, userList);
                    recyclerView.setAdapter(adapter);

                }else{
                    Toast.makeText(Profile_Activity.this, "No user found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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


}

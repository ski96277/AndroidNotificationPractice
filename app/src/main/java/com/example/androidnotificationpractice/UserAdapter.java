package com.example.androidnotificationpractice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<Users> userList;

    public UserAdapter(Context context, List<Users> userList){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users user = userList.get(position);
        holder.textViewEmail.setText(user.email);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView textViewEmail;

        public UserViewHolder(View itemView) {
            super(itemView);

            textViewEmail = itemView.findViewById(R.id.text_view_email_ID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Users users=userList.get(getAdapterPosition());

                    Intent intent = new Intent(context, Send_Notification_Activity.class);
                    intent.putExtra("user", users);
                    context.startActivity(intent);

                }
            });


        }
    }
}

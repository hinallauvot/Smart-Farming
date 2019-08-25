package com.example.hinal.smartfarming;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder> {

    private Context context;
    private List<ModelUserName> users = new ArrayList<>();

    public ChatRecyclerAdapter(Context context, List<ModelUserName> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.carview_chat,viewGroup,false);
        final ChatViewHolder chatViewHolder = new ChatViewHolder(v);


        chatViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = users.get(chatViewHolder.getAdapterPosition()).getUserName();
                String user_Key = users.get(chatViewHolder.getAdapterPosition()).getCustID();
                Intent i = new Intent(context,QueriesActivity.class);
                i.putExtra("user_name",username);
                i.putExtra("user_key",user_Key);
                context.startActivity(i);


            }
        });

        return chatViewHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {


        chatViewHolder.UserName.setText(users.get(i).getUserName());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView UserName;
        CardView linearLayout;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.NameTV);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}

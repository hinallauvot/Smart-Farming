package com.example.hinal.smartfarming;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QueryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private Context context;
    RequestOptions options;
    private DatabaseReference mDatabase;
    private ArrayList<ModelChat> itemList;
    // Constructor of the class


    public QueryRecyclerAdapter(Context context, ArrayList<ModelChat> itemList) {
        this.context = context;
        this.itemList = itemList;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).diskCacheStrategy(DiskCacheStrategy.ALL);

    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        ModelChat item = itemList.get(position);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Messages").child(uid);
        if (item.getUserID().contains(uid)) {
            return TYPE_TWO;
        } else {
            return TYPE_ONE;
        }

    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_bubble, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_bubble, parent, false);
            return new ViewHolderTwo(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((ViewHolderOne)holder, listPosition);
                break;
            case TYPE_TWO:
                initLayoutTwo((ViewHolderTwo) holder, listPosition);
                break;
            default:
                break;
        }
    }

    private void initLayoutOne(ViewHolderOne holder, int pos) {
        holder.item.setText(itemList.get(pos).getMessage());

        if(itemList.get(pos).messageType.equals("text")) {



            holder.item.setText(itemList.get(pos).getMessage());

            holder.messgeImageLeft.setVisibility(View.INVISIBLE);





        } else {



            holder.item.setVisibility(View.INVISIBLE);

            Glide.with(context).load(itemList.get(pos).getMessage()).apply(options).into(holder.messgeImageLeft);



        }
    }

    private void initLayoutTwo(ViewHolderTwo holder, int pos) {

        if(itemList.get(pos).messageType.equals("text")) {



            holder.tvRight.setText(itemList.get(pos).getMessage());

            holder.messageImageRight.setVisibility(View.INVISIBLE);





        } else {



            holder.tvRight.setVisibility(View.INVISIBLE);

            Glide.with(context).load(itemList.get(pos).getMessage()).apply(options).into(holder.messageImageRight);



        }
    }


    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView item;
        ImageView messgeImageLeft;
        public ViewHolderOne(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.left_textView);
            messgeImageLeft = itemView.findViewById(R.id.imageMessageLeft);
        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        public TextView  tvRight;
        ImageView messageImageRight;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            tvRight = (TextView) itemView.findViewById(R.id.right_textView);
            messageImageRight = itemView.findViewById(R.id.imageMessageRight);
        }
    }
}
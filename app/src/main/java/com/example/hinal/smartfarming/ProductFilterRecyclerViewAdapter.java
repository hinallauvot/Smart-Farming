package com.example.hinal.smartfarming;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProductFilterRecyclerViewAdapter extends
        RecyclerView.Adapter<ProductFilterRecyclerViewAdapter.ViewHolder> {

    private List<ModelPlan> filterList;
    private Context context;
    private List<ModelActivatedPlans> activePlansList = new ArrayList<>();


    public ProductFilterRecyclerViewAdapter() {
    }

    public ProductFilterRecyclerViewAdapter(List<ModelPlan> filterModelList
            , Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public ProductFilterRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ModelPlan filterM = filterList.get(position);
        activePlansList = new ArrayList<ModelActivatedPlans>();
        holder.brandName.setText(filterM.getPlanName());
        holder.productCount.setText("" + filterM.getPlanDesc());
        holder.price.setText(filterM.getPlanPrice());
        holder.imageView.setImageResource(filterM.getImagePath());
        holder.chkSelected.setChecked(filterList.get(position).isSelected());

        holder.chkSelected.setTag(filterList.get(position));


        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ModelPlan contact = (ModelPlan) cb.getTag();

                contact.setSelected(cb.isChecked());
                filterList.get(position).setSelected(cb.isChecked());


            }
        });


    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView brandName;
        public TextView productCount;
        public TextView price;
        ImageView imageView;
        public CheckBox chkSelected;
        FoldingCell foldingCell;
        DatabaseReference databaseReference;

        public ViewHolder(View view) {
            super(view);
            brandName = (TextView) view.findViewById(R.id.tvName);
            productCount = (TextView) view.findViewById(R.id.tvDescId);
            price = (TextView) view.findViewById(R.id.tvPrice);
            chkSelected = (CheckBox) view.findViewById(R.id.chkSelected);
            imageView = view.findViewById(R.id.imgCrops);


        }
    }

        public List<ModelPlan> getFilterList() {
            return filterList;
        }



}

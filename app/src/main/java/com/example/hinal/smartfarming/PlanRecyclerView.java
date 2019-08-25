package com.example.hinal.smartfarming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlanRecyclerView extends RecyclerView.Adapter<PlanRecyclerView.ActivatedViewHolder> {

    private Context context;
    private List<ModelActivatedPlans> activePlans = new ArrayList<>();

    public PlanRecyclerView(Context context, List<ModelActivatedPlans> activePlans) {
        this.context = context;
        this.activePlans = activePlans;
    }

    @NonNull
    @Override
    public ActivatedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.cardview_active_plans,viewGroup,false);
        ActivatedViewHolder activatedViewholder = new ActivatedViewHolder(v);
        return activatedViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivatedViewHolder activatedViewHolder, int i) {


        activatedViewHolder.PlanName.setText(activePlans.get(i).getPlanName());
        activatedViewHolder.ActivatedDate.setText(activePlans.get(i).getActivatedDate());
        activatedViewHolder.ExpiredDate.setText(activePlans.get(i).getExpiredDate());



    }

    @Override
    public int getItemCount() {
        return activePlans.size();
    }

    public class ActivatedViewHolder extends RecyclerView.ViewHolder {

        public TextView PlanName;
        public TextView ActivatedDate;
        public TextView ExpiredDate;


        public ActivatedViewHolder(@NonNull View itemView) {
            super(itemView);

            PlanName = itemView.findViewById(R.id.activePlanName);
            ActivatedDate = itemView.findViewById(R.id.activatedDate);
            ExpiredDate = itemView.findViewById(R.id.expiredDate);

        }
    }
}

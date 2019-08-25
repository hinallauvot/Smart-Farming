package com.example.hinal.smartfarming;

import java.io.Serializable;

public class ModelPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String PlanName;
    private String PlanDesc;
    private String PlanPrice;
    private int imagePath;
    private boolean isSelected;
    private int Days;

    public ModelPlan(int imagePath, String planName, String planDesc, String planPrice, boolean isSelected, int days) {

        this.imagePath = imagePath;
        PlanName = planName;
        PlanDesc = planDesc;
        PlanPrice = planPrice;
        this.isSelected = isSelected;
        Days = days;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getPlanDesc() {
        return PlanDesc;
    }

    public void setPlanDesc(String planDesc) {
        PlanDesc = planDesc;
    }

    public String getPlanPrice() {
        return PlanPrice;
    }

    public void setPlanPrice(String planPrice) {
        PlanPrice = planPrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}

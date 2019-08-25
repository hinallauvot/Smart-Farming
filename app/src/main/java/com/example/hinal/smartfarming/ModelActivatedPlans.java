package com.example.hinal.smartfarming;

import java.io.Serializable;
import java.util.Date;

public class ModelActivatedPlans {

    private String PlanName;
    private String  activatedDate;
    private String expiredDate;

    public ModelActivatedPlans() {
    }

    public ModelActivatedPlans(String PlanName, String activatedDate, String expiredDate) {
        this.PlanName = PlanName;
        this.activatedDate = activatedDate;
        this.expiredDate = expiredDate;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(String activatedDate) {
        this.activatedDate = activatedDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}

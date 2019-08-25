package com.example.hinal.smartfarming;

public class ModelUserName {

    String UserName;
    String CustID;

    public ModelUserName() {
    }

    public ModelUserName(String UserName, String CustID) {
        this.UserName = UserName;
        this.CustID = CustID;
    }



    public String getCustID() {
        return CustID;
    }

    public void setCustID(String custID) {
        CustID = custID;
    }

    public void ModelUserName(String userName) {
        UserName = userName;
    }



    public String getUserName() {
        return UserName;
    }


}

package com.example.hinal.smartfarming;

public class ModelChat {

    String UserID;
    String message;
    String messageType;


    public ModelChat() {
    }

    public ModelChat(String userID, String message,String messageType) {
        UserID = userID;
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

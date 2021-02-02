package com.example.capstone;

public class Class_User {
    String  userType, userName;
    int qCount;

    public Class_User(String userType, String userName, int qCount) {
        this.userType = userType;
        this.userName = userName;
        this.qCount= qCount;
    }



    public String getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public int getqCount() {
        return qCount;
    }
}

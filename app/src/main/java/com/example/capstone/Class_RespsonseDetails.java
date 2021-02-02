package com.example.capstone;

public class Class_RespsonseDetails {
    String name, race, nationality, email, suggestion, feedback;
    String birthday, phone;

    public Class_RespsonseDetails( String name, String race, String nationality, String email, String suggestion, String feedback, String birthday, String phone) {
        this.name = name;
        this.race = race;
        this.nationality = nationality;
        this.email = email;
        this.suggestion = suggestion;
        this.feedback = feedback;
        this.birthday = birthday;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public String getNationality() {
        return nationality;
    }

    public String getEmail() {
        return email;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getBirthday() {
        return birthday;
    }

    public String  getPhone() {
        return phone;
    }
}

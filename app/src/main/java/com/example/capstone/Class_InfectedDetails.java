package com.example.capstone;

public class Class_InfectedDetails {
    String  pname, gender, race, nationality, condition, address, dname, hospital;
    String nric, cpn, phone, birthday, drID;

    public Class_InfectedDetails( String pname, String gender, String race, String nationality, String condition, String address, String dname, String hospital, String nric, String cpn, String phone, String birthday, String drID) {
        this.pname = pname;
        this.gender = gender;
        this.race = race;
        this.nationality = nationality;
        this.condition = condition;
        this.address = address;
        this.dname = dname;
        this.hospital = hospital;
        this.nric = nric;
        this.cpn = cpn;
        this.phone = phone;
        this.birthday = birthday;
        this.drID = drID;
    }


    public String getPname() {
        return pname;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCondition() {
        return condition;
    }

    public String getAddress() {
        return address;
    }

    public String getDname() {
        return dname;
    }

    public String getHospital() {
        return hospital;
    }


    public String getNric() {
        return nric;
    }

    public String getCpn() {
        return cpn;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDrID() {
        return drID;
    }
}

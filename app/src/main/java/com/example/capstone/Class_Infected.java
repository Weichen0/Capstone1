package com.example.capstone;

public class Class_Infected {
    int contact, indirect, aerosol;
    int clustering, environmental, touch, hygiene;
    int local, international, visit, religion, sports, restaurant, work;
    int daytest;
    String location;

    public Class_Infected(int indirect, int contact, int aerosol, int clustering, int environmental, int touch, int hygiene, int local, int international, int visit,  int religion, int sports, int restaurant, int work, String location, int daytest) {
        this.indirect = indirect;
        this.contact = contact;
        this.aerosol = aerosol;
        this.clustering = clustering;
        this.environmental = environmental;
        this.touch = touch;
        this.hygiene = hygiene;
        this.local = local;
        this.international = international;
        this.visit = visit;
        this.religion = religion;
        this.sports = sports;
        this.restaurant = restaurant;
        this.work = work;

        this.location = location;
        this.daytest = daytest;
    }
    public int getIndirect() {
        return indirect;
    }

    public int getContact() {
        return contact;
    }

    public int getAerosol() {
        return aerosol;
    }

    public int getClustering() {
        return clustering;
    }

    public int getEnvironmental() {
        return environmental;
    }

    public int getTouch() {
        return touch;
    }

    public int getHygiene() {
        return hygiene;
    }

    public int getLocal() {
        return local;
    }

    public int getInternational() {
        return international;
    }

    public int getVisit() {
        return visit;
    }

    public int getReligion() {
        return religion;
    }

    public int getSports() {
        return sports;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public int getWork() {
        return work;
    }

    public String getLocation() {
        return location;
    }

    public int getDayTest() {
        return daytest;
    }


}

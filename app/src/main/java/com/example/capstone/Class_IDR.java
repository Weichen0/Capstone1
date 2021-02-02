package com.example.capstone;

import java.util.Date;

public class Class_IDR {
    int infected, diseased, recovered;
    int date;


    public Class_IDR(int infected, int diseased, int recovered, int date) {
        this.infected = infected;
        this.diseased = diseased;
        this.recovered = recovered;
        this.date = date;
    }

    public int getInfected() {
        return infected;
    }

    public int getDiseased() {
        return diseased;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getDate() {
        return date;
    }
}

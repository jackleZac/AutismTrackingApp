package com.example.autismtrackingapp;

public class MedicalRecord {
    public String date, doctor_name, reason;
    public MedicalRecord() {}
    public MedicalRecord(String date, String doctor_name, String reason) {
        this.date = date;
        this.doctor_name = doctor_name;
        this.reason = reason;
    }
}

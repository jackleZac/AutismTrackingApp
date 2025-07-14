package com.example.autismtrackingapp;

public class DietaryEntry {
    public String date, meal, preference, notes; // Updated fields
    public DietaryEntry() {}
    public DietaryEntry(String date, String meal, String preference, String notes) { // New constructor
        this.date = date;
        this.meal = meal;
        this.preference = preference;
        this.notes = notes;
    }
}
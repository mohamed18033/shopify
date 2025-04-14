package com.example.shopifygp.Database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @NonNull
    private String email;
    private String gender;
    private String phoneNumber;
    private String fullName;

    public User(@NonNull String email, String fullName, String phoneNumber, String gender) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
    }

    public User() {
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

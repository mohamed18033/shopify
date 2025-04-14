package com.example.shopifygp.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}

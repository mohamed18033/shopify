package com.example.shopifygp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {OrderItemModel.class}, version = 1, exportSchema = false)
public abstract class OrderItemDatabase extends RoomDatabase {
    public abstract OrderItemDao orderItemDao();
}

package com.example.shopifygp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.example.shopifygp.Database.OrderItemDao;
import com.example.shopifygp.Database.OrderItemDatabase;
import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.Database.UserDao;
import com.example.shopifygp.Database.UserDatabase;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderItemDatabase db = Room.databaseBuilder(getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();
        OrderItemDao orderItemDao = db.orderItemDao();
        orderItemDao.clearCart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Display Splash Screen For 2 Seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

}
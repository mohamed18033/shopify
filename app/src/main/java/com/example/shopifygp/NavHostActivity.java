package com.example.shopifygp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopifygp.Database.OrderItemDao;
import com.example.shopifygp.Database.OrderItemDatabase;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class NavHostActivity extends AppCompatActivity {

    // Creating Objects
    ImageView menuIcon;
    NavigationView navigationView;
    NavController navController;
    TextView toolbarTitle;
    TextView userEmail;
    TextView userName;
    MenuItem logout;

    // Firebase
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nav_host);

        // Getting Views
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        menuIcon = (ImageView) findViewById(R.id.menuicon);
        toolbarTitle = findViewById(R.id.titletoolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        navController = Navigation.findNavController(this, R.id.navhostfragment);
        userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

        logout = navigationView.getMenu().findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                drawerLayout.close();
                openLogoutDialog();
                return false;
            }

        });

        // Setting up navigation UI
        navigationView.setItemIconTintList(null);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
        }

        // Open drawer when clicking on menu icon
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                toolbarTitle.setText(navDestination.getLabel());
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(navController.getCurrentDestination().getLabel().toString().equals("Restaurants"))
        {
            openLogoutDialog();
        }
        else
        {
            super.onBackPressed();
        }
    }



    public void openLogoutDialog(){
        AlertDialog.Builder logoutDialogBuilder = new AlertDialog.Builder(NavHostActivity.this);
        logoutDialogBuilder.setTitle("Logout").setMessage("Are you sure you want to logout?").setCancelable(true).setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderItemDatabase db = Room.databaseBuilder(getApplicationContext(), OrderItemDatabase.class, "order_items_database").allowMainThreadQueries().build();
                OrderItemDao orderItemDao = db.orderItemDao();
                orderItemDao.clearCart();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logoutDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Just dismiss logging out
            }
        });

        AlertDialog logoutDialog = logoutDialogBuilder.create();
        logoutDialog.show();
    }
}
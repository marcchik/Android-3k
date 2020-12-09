package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.fragments.DetailsFragment;
import com.smuzdev.lab_05.fragments.ThingsListFragment;
import com.smuzdev.lab_05.helper.MyAdapter;
import com.smuzdev.lab_05.interfaces.Postman;
import com.smuzdev.lab_05.models.Thing;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Postman {

    GridLayoutManager gridLayoutManager;
    ThingsListFragment thingsListFragment;
    RecyclerView mRecyclerView;
    Context context = this;
    private FrameLayout listContainer;
    private FrameLayout detailsContainer;

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("testLog", "Tetsing onCreate");

        thingsListFragment = new ThingsListFragment();
        DetailsFragment detailsFragment = new DetailsFragment();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, thingsListFragment)
                    .addToBackStack(null)
                    .commit();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.things_container, thingsListFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
            finishAffinity();
            startActivity(mIntent);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Drawer
        // <---- ----->
        toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.show_list_view:
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        break;
                    case R.id.show_table_view:
                        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        mRecyclerView.setLayoutManager(gridLayoutManager);
                        break;
                    case R.id.upload_thing:
                        startActivity(new Intent(getApplicationContext(), UploadActivity.class));
                        break;
                }
                return true;
            }
        });
        // <---- ----->
    }

    @Override
    public void fragmentMail(GridLayoutManager fragmentGridLayoutManager) {
        gridLayoutManager = fragmentGridLayoutManager;
    }


}

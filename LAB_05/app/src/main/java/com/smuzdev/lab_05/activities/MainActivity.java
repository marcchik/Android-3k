package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.helper.MyAdapter;
import com.smuzdev.lab_05.models.Dish;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Dish> dishList;
    Dish mDish;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    EditText txt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView  = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_search = findViewById(R.id.txt_searchText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading items...");

        dishList = new ArrayList<>();

        final MyAdapter myAdapter = new MyAdapter(MainActivity.this, dishList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dishList.clear();

                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {

                    Dish dish = itemSnapshot.getValue(Dish.class);
                    dish.setKey(itemSnapshot.getKey());
                    dishList.add(dish);

                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }

            private void filter(String text) {

                ArrayList<Dish> filterList = new ArrayList<>();
                for(Dish dish: dishList) {

                    if(dish.getDishName().toLowerCase().contains(text.toLowerCase())) {

                        filterList.add(dish);

                    }

                }

                myAdapter.filteredList(filterList);

            }
        });

    }

    public void btn_uploadActivity(View view) {

        startActivity(new Intent(this, UploadActivity.class));

    }

}
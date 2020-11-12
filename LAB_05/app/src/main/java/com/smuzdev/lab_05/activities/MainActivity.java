package com.smuzdev.lab_05.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smuzdev.lab_05.R;
import com.smuzdev.lab_05.helper.MyAdapter;
import com.smuzdev.lab_05.models.Thing;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Thing> thingList;
    Thing mThing;
    ProgressDialog progressDialog;
    EditText txt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_search = findViewById(R.id.txt_searchText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading items...");

        thingList = new ArrayList<>();

        final MyAdapter myAdapter = new MyAdapter(MainActivity.this, thingList);
        mRecyclerView.setAdapter(myAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Things");
        progressDialog.show();
        ValueEventListener eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                thingList.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {

                    Thing thing = itemSnapshot.getValue(Thing.class);
                    thing.setKey(itemSnapshot.getKey());
                    thingList.add(thing);
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

                ArrayList<Thing> filterList = new ArrayList<>();
                for (Thing thing : thingList) {

                    if (thing.getThingName().toLowerCase().contains(text.toLowerCase())) {
                        filterList.add(thing);
                    }

                }

                myAdapter.filteredList(filterList);

            }
        });

        registerForContextMenu(mRecyclerView);

    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case 121:
                startActivity(new Intent(getApplicationContext(), UpdateActivity.class)
                        .putExtra("thingNameKey", thingList.get(item.getGroupId()).getThingName())
                        .putExtra("thingDescriptionKey", thingList.get(item.getGroupId()).getThingDescription())
                        .putExtra("thingDiscoveryDateKey", thingList.get(item.getGroupId()).getThingDiscoveryDate())
                        .putExtra("thingDiscoveryPlaceKey", thingList.get(item.getGroupId()).getThingDiscoveryPlace())
                        .putExtra("thingPickupPointKey", thingList.get(item.getGroupId()).getThingPickupPoint())
                        .putExtra("userNameKey", thingList.get(item.getGroupId()).getUserName())
                        .putExtra("userPhoneKey", thingList.get(item.getGroupId()).getUserPhone())
                        .putExtra("userEmailKey", thingList.get(item.getGroupId()).getUserEmail())
                        .putExtra("oldImageUrl", thingList.get(item.getGroupId()).getThingImage())
                        .putExtra("key", thingList.get(item.getGroupId()).getKey())
                );
                return true;
            case 122:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle("Delete")
                        .setMessage("Do you want to delete item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Things");
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageReference = storage.getReferenceFromUrl(thingList.get(item.getGroupId()).getThingImage());

                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        reference.child(thingList.get(item.getGroupId()).getKey()).removeValue();
                                        Toast.makeText(MainActivity.this, "Thing deleted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", null).create().show();
                return true;
            default:
                super.onContextItemSelected(item);
        }
        return false;
    }


    public void btn_uploadActivity(View view) {

        startActivity(new Intent(this, UploadActivity.class));

    }

}

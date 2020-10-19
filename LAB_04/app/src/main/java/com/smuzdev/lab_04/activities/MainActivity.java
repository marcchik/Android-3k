package com.smuzdev.lab_04.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.smuzdev.lab_04.R;
import com.smuzdev.lab_04.activities.RegistrationActivity;
import com.smuzdev.lab_04.auxiliary.DataAdapter;
import com.smuzdev.lab_04.auxiliary.Json;
import com.smuzdev.lab_04.auxiliary.Person;
import com.smuzdev.lab_04.auxiliary.RequestPermissions;
import com.smuzdev.lab_04.auxiliary.Users;

public class MainActivity extends AppCompatActivity implements DataAdapter.OnItemClickListener {
    String TAG = "LAB_04";
    Users users = new Users();
    Context context = this;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
        protected void onStart(){
        super.onStart();

        final RequestPermissions requestPermission = new RequestPermissions();
        if(!requestPermission.permissionGranted) {
            requestPermission.checkPermissions(context, activity);
        }

        users = Json.Deserialize();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DataAdapter adapter = new DataAdapter(this, users, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_user:
                Intent intent= new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, Item.class);
        Person person = users.personArrayList.get(position);

        intent.putExtra("name", person.name);
        intent.putExtra("surname", person.surname);
        intent.putExtra("phone", person.phone);
        intent.putExtra("email", person.email);
        intent.putExtra("twitter", person.twitter);
        intent.putExtra("pathToAvatar", person.pathToAvatar);

        startActivity(intent);
    }

}
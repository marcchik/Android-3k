package com.smuzdev.lab_10.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smuzdev.lab_10.EventsAdapter;
import com.smuzdev.lab_10.R;
import com.smuzdev.lab_10.model.Events;
import com.smuzdev.lab_10.viewmodel.EventsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton add_button;
    private EventsViewModel model;
    private RecyclerView recyclerView;
    EditText et_type, et_place, et_time, et_prize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        model = ViewModelProviders.of(this).get(EventsViewModel.class);

        registerForContextMenu(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        model.getAllEvents().observe(this, new Observer<List<Events>>() {
            @Override
            public void onChanged(List<Events> contacts) {
                final EventsAdapter adapter = new EventsAdapter(MainActivity.this, contacts);
                recyclerView.setAdapter(adapter);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_event, null);

                builder.setView(view)
                        .setPositiveButton("Add", (dialog, which) -> {
                            et_type = view.findViewById(R.id.event_type_input);
                            et_place = view.findViewById(R.id.event_place_input);
                            et_time = view.findViewById(R.id.event_time_input);
                            et_prize = view.findViewById(R.id.event_prize_input);

                            Events events = new Events(
                                    et_type.getText().toString(),
                                    et_place.getText().toString(),
                                    et_time.getText().toString(),
                                    et_prize.getText().toString());
                            model.insert(events);
                            Toast.makeText(MainActivity.this,
                                    "A new event added", Toast.LENGTH_LONG).show();
                        }).setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_event, null);

                et_type = view.findViewById(R.id.event_type_input);
                et_place = view.findViewById(R.id.event_place_input);
                et_time = view.findViewById(R.id.event_time_input);
                et_prize = view.findViewById(R.id.event_prize_input);

                Events events = model.getById(item.getOrder());

                et_type.setText(events.getEventType());
                et_place.setText(events.getPlace());
                et_time.setText(events.getTime());
                et_prize.setText(events.getPrize());

                builder.setView(view)
                        .setPositiveButton("Save", (dialog, which) -> {
                            events.setEventType(et_type.getText().toString());
                            events.setPlace(et_place.getText().toString());
                            events.setTime(et_time.getText().toString());
                            events.setPrize(et_prize.getText().toString());
                            model.update(events);
                            Toast.makeText(MainActivity.this,
                                    "Event information updated.", Toast.LENGTH_LONG).show();
                        }).setNegativeButton("Cancel", null);


                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case 0:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Are you sure?");
                builder1.setPositiveButton("Yes",
                        (dialogInterface, i) -> model.delete(model.getById(item.getOrder())));
                builder1.setNegativeButton("No", null);

                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
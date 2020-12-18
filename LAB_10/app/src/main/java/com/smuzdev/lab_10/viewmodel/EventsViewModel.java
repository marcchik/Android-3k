package com.smuzdev.lab_10.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.smuzdev.lab_10.EventsRepository;
import com.smuzdev.lab_10.model.Events;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {
    private EventsRepository eventsRepository;
    private LiveData<List<Events>> allEvents;

    public EventsViewModel(@NonNull Application application) {
        super(application);
        eventsRepository = new EventsRepository(application);
        allEvents = eventsRepository.getAllEvents();
    }

    public void insert(Events events) {
        eventsRepository.insert(events);
    }

    public void update(Events events) {
        eventsRepository.update(events);
    }

    public void delete(Events events) {
        eventsRepository.delete(events);
    }

    public Events getById(Integer id) {
        return eventsRepository.getById(id);
    }

    public LiveData<List<Events>> getAllEvents() {
        return allEvents;
    }
}

package com.smuzdev.lab_10;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.smuzdev.lab_10.dao.EventsDao;
import com.smuzdev.lab_10.database.EventsDatabase;
import com.smuzdev.lab_10.model.Events;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventsRepository {

    private EventsDao eventsDao;
    private LiveData<List<Events>> allEvents;

    public EventsRepository(Application context) {
        EventsDatabase database = EventsDatabase.getInstance(context);
        eventsDao = database.eventsDao();
        allEvents = eventsDao.getAll();
    }

    public void insert(Events events) {
        new InsertEventsAsyncTask(eventsDao).execute(events);
    }

    public void update(Events events) {
        new UpdateEventsAsyncTask(eventsDao).execute(events);
    }

    public void delete(Events events) {
        new DeleteEventsAsyncTask(eventsDao).execute(events);
    }

    public Events getById(Integer id) {
        try {
            return new GetEventsByIdAsyncTask(eventsDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Events>> getAllContacts() {
        return allEvents;
    }

    private static class InsertEventsAsyncTask extends AsyncTask<Events, Void, Void> {
        private EventsDao eventsDao;
        private InsertEventsAsyncTask(EventsDao eventsDao) {
            this.eventsDao = eventsDao;
        }
        @Override
        protected Void doInBackground(Events... events) {
            eventsDao.insert(events[0]);
            return null;
        }
    }

    private static class UpdateEventsAsyncTask extends AsyncTask<Events, Void, Void> {
        private EventsDao eventsDao;
        private UpdateEventsAsyncTask(EventsDao eventsDao) {
            this.eventsDao = eventsDao;
        }
        @Override
        protected Void doInBackground(Events... events) {
            eventsDao.update(events[0]);
            return null;
        }
    }

    private static class DeleteEventsAsyncTask extends AsyncTask<Events, Void, Void> {
        private EventsDao eventsDao;
        private DeleteEventsAsyncTask(EventsDao eventsDao) {
            this.eventsDao = eventsDao;
        }
        @Override
        protected Void doInBackground(Events... contacts) {
            eventsDao.delete(contacts[0]);
            return null;
        }
    }

    private static class GetEventsByIdAsyncTask extends AsyncTask<Integer, Void, Events> {
        private EventsDao eventsDao;
        private GetEventsByIdAsyncTask(EventsDao eventsDao) {
            this.eventsDao = eventsDao;
        }
        @Override
        protected Events doInBackground(Integer... id) {
            return eventsDao.getById(id[0]);
        }
    }
}

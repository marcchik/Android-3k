package com.smuzdev.lab_08.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.smuzdev.lab_08.DatabaseHelper;
import com.smuzdev.lab_08.Thing;

import java.util.ArrayList;


public class DbAsyncInsertTask extends AsyncTask<Thing, Void, Void> {

    private static final String TAG = "LAB8_DEBUG";
    Context context;
    SQLiteDatabase db;

    public DbAsyncInsertTask(Context context) {
        this.context = context;
    }

    //Код, предшествующий выполнению задачи
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = new DatabaseHelper(context).getWritableDatabase();
        Log.d(TAG, "onPreExecute: TASK WILL START SOON...");
    }

    //Код, выполняемый при завершении задачи
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        db.close();
        Log.d(TAG, "onPostExecute: TASK COMPLETE!");
    }

    //Код, передающий информацию о ходе выполнения задачи
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "onProgressUpdate: TASK IN PROCESS...");
    }

    //Код, выполняемый в фоновом потоке
    @Override
    protected Void doInBackground(Thing... things) {
        Log.d(TAG, "onProgressUpdate: TASK STARTED!");
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, things[0].getThing_title().);
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
        cv.put(DatabaseHelper.COLUMN_DISCOVERED_PLACE, discoveredPlace);
        cv.put(DatabaseHelper.COLUMN_IMAGE, image);

        long result = db.insert(DatabaseHelper.TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed :(", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Successfully added!", Toast.LENGTH_LONG).show();
        }

        return null;
    }
}

package com.smuzdev.lab_05.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableRow;

public class DatabaseHelper extends SQLiteOpenHelper {
        static String DATABASE_NAME = "Lab8_BD.db";
        static int SCHEMA = 1;
        public static String TABLE_THING_NAME = "Things";

        static String THING_COLUMN_ID = "ID";
        static String THING_COLUMN_TITLE = "Title";
        static String THING_COLUMN_DISCOVERY_DATE = "Discovery_date";
        static String THING_COLUMN_DESCRIPTION = "Description";
        static String THING_COLUMN_DISCOVERY_PLACE = "Discovery_place";
        static String THING_COLUMN_PICKUP_POINT = "Pickup_point";
        static String THING_COLUMN_IMAGE = "Image";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, SCHEMA);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createThingsTable(db);
        }

        private void createThingsTable(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_THING_NAME + " ("
                    + THING_COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + THING_COLUMN_TITLE + " TEXT,"
                    + THING_COLUMN_DESCRIPTION + " TEXT,"
                    + THING_COLUMN_DISCOVERY_DATE + " TEXT,"
                    + THING_COLUMN_DISCOVERY_PLACE + " TEXT,"
                    + THING_COLUMN_PICKUP_POINT + " TEXT,"
                    + THING_COLUMN_IMAGE + " TEXT"
                    +");");
            db.execSQL("INSERT INTO " + TABLE_THING_NAME+
                    " VALUES (0, 'Somth', 'Somth', 'Somth', 'Somth', 'Somth', 'Somth');");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void dropThingsTable(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THING_NAME);
            createThingsTable(db);
        }

        public void insertThing(SQLiteDatabase db,int ID, String Title, String Description, String DiscoveryDate,
                                String DiscoveryPlace, String PickupPoint, String Image) {
            db.execSQL("INSERT INTO " + TABLE_THING_NAME +
                    " VALUES ("+ID+", '"+Title+"', '"+Description+"', '"+DiscoveryDate+"', '"+DiscoveryPlace+"'," +
                    " '"+PickupPoint+"', "+Image+");");
        }
        public Cursor selectThing(SQLiteDatabase db, int id){
            return db.rawQuery("select * from " + TABLE_THING_NAME+" where ID=?", new String[]{String.valueOf(id)});
        }

        public void deleteThing(SQLiteDatabase db, int id){
            db.execSQL("Delete FROM " + TABLE_THING_NAME +
                    " Where ID="+id+";");
        }
}

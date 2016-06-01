package com.joincic.joincicapp.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joincic.joincicapp.controllers.Schedule;

/**
 * Created by carla on 26/04/15.
 */
public class WorkTableModel {
    static final String TAG = "Models.WorkTable";
    //Table name
    public static final String TABLE_WORK_TABLE = "work_table";

    //Table name attributes
    public static final String C_ID = "id";
    public static final String C_PRESENTER_ID = "presenter_id";
    public static final String C_SUBJECT = "subject";
    public static final String C_RAFFLED = "raffled";
    public static final String C_SPONSOR_ID = "sponsor_id";
    public static final String C_START_HOUR = "start_hour";
    public static final String C_END_HOUR = "end_hour";
    public static final String C_TITLE = "title";
    public static final String C_LOCATION = "location";
    public static final String C_CAPACITY = "capacity";
    public static final String C_DAY = "day";
    public static final String C_DESCRIPTION = "description";
    public static final String C_PRESENTER_NAME = "requirements";

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_WORK_TABLE
            + "("
            + C_ID + " INTEGER PRIMARY KEY, "
            + C_PRESENTER_ID + " INTEGER, "
            + C_SUBJECT + " TEXT, "
            + C_SPONSOR_ID + " INTEGER, "
            + C_START_HOUR + " TEXT, "
            + C_END_HOUR + " TEXT, "
            + C_RAFFLED + " INTEGER, "
            + C_LOCATION + " TEXT, "
            + C_CAPACITY + " INTEGER, "
            + C_TITLE + " TEXT, "
            + C_DAY + " TEXT, "
            + C_DESCRIPTION + " TEXT, "
            + C_PRESENTER_NAME + " TEXT "

            + ");";

    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(WorkTableModel.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_WORK_TABLE);
        onCreate(database);
    }


    public static ContentValues workTableToValues(Schedule.WorkTable p) {
        ContentValues values = new ContentValues();
        values.put(C_ID, p.getId());
        values.put(C_SUBJECT, p.getTema());
        values.put(C_START_HOUR, p.getHora_ini());
        values.put(C_END_HOUR, p.getHora_fin());
        values.put(C_TITLE, p.getTitulo());
        values.put(C_DAY, p.getDia());
        values.put(C_DESCRIPTION, p.getDescripcion());
        values.put(C_PRESENTER_NAME, p.getPonente().getNombre() + " " + p.getPonente().getApellido());

        values.put(C_CAPACITY, p.getCapacidad());
        values.put(C_LOCATION, p.getLugar());

        if (p.isSorteada()){
            values.put(C_RAFFLED, 1);
        } else {
            values.put(C_RAFFLED, 0);
        }

        Log.d(TAG, "Values WorkTableToValues = " + values);
        return values;
    }
}

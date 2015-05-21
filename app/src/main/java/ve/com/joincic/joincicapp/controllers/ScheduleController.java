package ve.com.joincic.joincicapp.controllers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import ve.com.joincic.joincicapp.adapters.Item;
import ve.com.joincic.joincicapp.adapters.ScheduleItem;
import ve.com.joincic.joincicapp.models.PresentationModel;
import ve.com.joincic.joincicapp.models.WorkTableModel;
import ve.com.joincic.joincicapp.providers.JoincicProvider;

/**
 * Created by Carla Urrea Stabile on 4/23/15.
 */
public class ScheduleController {
    final static String TAG = "ScheduleController";
    static Context context;

    public static void getInstance(Context c) {
        context = c.getApplicationContext();
    }


    /**
     * Inserts the presentation info into the local database of the device
     * @param presentations an array list of presentations
     * */
    public static void insertPresentations(ArrayList<Schedule> presentations) {
        if (presentations != null) {
            for (Schedule p : presentations) {
                context.getContentResolver().insert(JoincicProvider.CONTENT_URI_PRESENTATIONS,
                        PresentationModel.presentationToValues(p.getPonencia()));
            }
        }
    }

    /**
     * Inserts the work tables information into the local database of the device
     * @param workTables an array list of work tables
     * */

    public static void insertWorkTables(ArrayList<Schedule> workTables){

        if (workTables != null){
            for (Schedule wt: workTables){
                context.getContentResolver().insert(JoincicProvider.CONTENT_URI_WORK_TABLES,
                        WorkTableModel.workTableToValues(wt.getMesa_de_trabajo()));
            }
        }

    }

    /**
     * Gets the presentations and work tables for the schedule list
     * @return an array list of items
     * */
    public static ArrayList<Item> getSchedule(){
        ArrayList<Item> items = new ArrayList<Item>();
        Log.d(TAG, "Estoy en getSchedule");
        //Get presentations
        Cursor cursor = context.getContentResolver().query(JoincicProvider.CONTENT_URI_PRESENTATIONS, null, null,
                null, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(PresentationModel.C_ID));
                String title = cursor.getString(cursor.getColumnIndex(PresentationModel.C_TITLE));
                String startDate = cursor.getString(cursor.getColumnIndex(PresentationModel.C_START_HOUR));
                String endDate = cursor.getString(cursor.getColumnIndex(PresentationModel.C_END_HOUR));
                boolean isPresentation = true;

                ScheduleItem s = new ScheduleItem(id, isPresentation, 0, 0, null, title, null,
                        startDate, endDate);
                items.add(s);

                Log.d(TAG, "Agregue a " +title);
            }while (cursor.moveToNext());
        }
        cursor.close();

        Cursor cursorWT = context.getContentResolver().query(JoincicProvider.CONTENT_URI_WORK_TABLES, null, null,
                null, null);

        if (cursorWT.moveToFirst()){
            do {
                int id = cursorWT.getInt(cursorWT.getColumnIndex(WorkTableModel.C_ID));
                String title = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_TITLE));
                String startDate = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_START_HOUR));
                String endDate = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_END_HOUR));
                boolean isPresentation = false;

                ScheduleItem s = new ScheduleItem(id, isPresentation, 0, 0, null, title, null,
                        startDate, endDate);
                items.add(s);
                Log.d(TAG, "Agregue a " +title);

            }while (cursor.moveToNext());
        }
        cursor.close();

        return items;
    }
 }

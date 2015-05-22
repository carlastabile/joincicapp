package ve.com.joincic.joincicapp.controllers;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import ve.com.joincic.joincicapp.adapters.Item;
import ve.com.joincic.joincicapp.adapters.ScheduleItem;
import ve.com.joincic.joincicapp.application.JoincicApp;
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
     *
     * @param presentations an array list of presentations
     */
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
     *
     * @param workTables an array list of work tables
     */

    public static void insertWorkTables(ArrayList<Schedule> workTables) {

        if (workTables != null) {
            for (Schedule wt : workTables) {
                context.getContentResolver().insert(JoincicProvider.CONTENT_URI_WORK_TABLES,
                        WorkTableModel.workTableToValues(wt.getMesa_de_trabajo()));
            }
        }

    }

    /**
     * Gets the presentations and work tables for the schedule list
     *
     * @return an array list of items
     */
    public static ArrayList<Item> getSchedule(String day) {
        ArrayList<Item> items = new ArrayList<Item>();
        //Get presentations
        String selection = PresentationModel.C_DAY + "=?";
        String[] selectionArgs = {day};
        String sortBy = "datetime(" + PresentationModel.C_START_HOUR + ") ASC";
        Cursor cursor = context.getContentResolver().query(JoincicProvider.CONTENT_URI_PRESENTATIONS,
                null, selection, selectionArgs, sortBy);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(PresentationModel.C_ID));
                String title = cursor.getString(cursor.getColumnIndex(PresentationModel.C_TITLE));
                String startDate = cursor.getString(cursor.getColumnIndex(PresentationModel.C_START_HOUR));
                String endDate = cursor.getString(cursor.getColumnIndex(PresentationModel.C_END_HOUR));
                String desc = cursor.getString(cursor.getColumnIndex(PresentationModel.C_DESCRIPTION));
                String dayy = cursor.getString(cursor.getColumnIndex(PresentationModel.C_DAY));

                boolean isPresentation = true;

                ScheduleItem s = new ScheduleItem(id, isPresentation, 0, 0, null, title, desc,
                        startDate, endDate, dayy);
                items.add(s);

            } while (cursor.moveToNext());
        }
        cursor.close();

        selection = WorkTableModel.C_DAY + "=?";
        sortBy = "datetime(" + WorkTableModel.C_START_HOUR + ") ASC";
        ;
        Cursor cursorWT = context.getContentResolver().query(JoincicProvider.CONTENT_URI_WORK_TABLES,
                null, selection, selectionArgs, sortBy);

        if (cursorWT.moveToFirst()) {
            do {
                int id = cursorWT.getInt(cursorWT.getColumnIndex(WorkTableModel.C_ID));
                String title = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_TITLE));
                String startDate = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_START_HOUR));
                String endDate = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_END_HOUR));
                String desc = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_DESCRIPTION));
                String dayy = cursorWT.getString(cursorWT.getColumnIndex(WorkTableModel.C_DAY));

                boolean isPresentation = false;

                ScheduleItem s = new ScheduleItem(id, isPresentation, 0, 0, null, title, desc,
                        startDate, endDate, dayy);
                items.add(s);

            } while (cursorWT.moveToNext());
        }
        cursorWT.close();

        sortItemsByHour(items);

        return items;
    }

    /**
     * Sorts the array list of items by hour
     *
     * @param items the array list to sort
     */
    public static void sortItemsByHour(ArrayList<Item> items) {

        if (items != null) {
            Comparator<Item> comparator = new Comparator<Item>() {
                public int compare(Item c1, Item c2) {
                    ScheduleItem s1 = (ScheduleItem) c1;
                    ScheduleItem s2 = (ScheduleItem) c2;

                    Date date1 = JoincicApp.stringToDate(s1.getStartDate());
                    Date date2 = JoincicApp.stringToDate(s2.getStartDate());

                    if (date1.getTime() < date2.getTime()) {
                        return -1;
                    } else if (date1.getTime() > date2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            };

            Collections.sort(items, comparator);
        }
    }

}
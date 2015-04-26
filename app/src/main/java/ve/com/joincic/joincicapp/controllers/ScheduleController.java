package ve.com.joincic.joincicapp.controllers;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

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
}

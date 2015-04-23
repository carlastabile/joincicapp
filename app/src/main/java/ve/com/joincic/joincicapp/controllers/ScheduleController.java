package ve.com.joincic.joincicapp.controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import ve.com.joincic.joincicapp.models.PresentationModel;
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

    public static void insertPresentations(ArrayList<Ponencia> presentations) {
        if (presentations != null) {
            for (Ponencia p : presentations) {
                context.getContentResolver().insert(JoincicProvider.CONTENT_URI_PRESENTATIONS,
                        PresentationModel.presentationToValues(p));
            }
        }
    }
}

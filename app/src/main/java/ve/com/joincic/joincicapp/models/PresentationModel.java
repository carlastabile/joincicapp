package ve.com.joincic.joincicapp.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ve.com.joincic.joincicapp.controllers.Schedule;

/**
 * Created by Carla Urrea Stabile on 4/23/15.
 */
public class PresentationModel {
    static final String TAG = "Models.Presentation";
    //Table name
    public static final String TABLE_PRESENTATION = "presentation";

    //Table name attributes
    public static final String C_ID = "id";
    public static final String C_PRESENTER_ID = "presenter_id";
    public static final String C_SUBJECT = "subject";
    public static final String C_SPONSOR_ID = "sponsor_id";
    public static final String C_START_HOUR = "start_hour";
    public static final String C_END_HOUR = "end_hour";
    public static final String C_TITLE = "title";
    public static final String C_DAY = "day";
    public static final String C_DESCRIPTION = "description";
    public static final String C_REQUIREMENTS = "requirements";

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_PRESENTATION
            + "("
            + C_ID + " INTEGER PRIMARY KEY, "
            + C_PRESENTER_ID + " INTEGER, "
            + C_SUBJECT + " TEXT, "
            + C_SPONSOR_ID + " INTEGER, "
            + C_START_HOUR + " TEXT, "
            + C_END_HOUR + " TEXT, "
            + C_TITLE + " TEXT, "
            + C_DAY + " TEXT, "
            + C_DESCRIPTION + " TEXT, "
            + C_REQUIREMENTS + " TEXT "

            //Constraints
//            + ", FOREIGN KEY (" + C_COURSE_ID + ") " + "REFERENCES COURSE (id) "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(PresentationModel.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESENTATION);
        onCreate(database);
    }


    public static ContentValues presentationToValues(Schedule.Presentation p) {
        ContentValues values = new ContentValues();
        values.put(C_ID, p.getId());
        values.put(C_SUBJECT, p.getTema());
        values.put(C_START_HOUR, p.getHora_ini());
        values.put(C_END_HOUR, p.getHora_fin());
        values.put(C_TITLE, p.getTitulo());
        values.put(C_DAY, p.getDia());
        values.put(C_DESCRIPTION, p.getDescripcion());
        values.put(C_REQUIREMENTS, p.getDescripcion());
        Log.d(TAG, "Values presentationToValues = " + values);
        return values;
    }
}

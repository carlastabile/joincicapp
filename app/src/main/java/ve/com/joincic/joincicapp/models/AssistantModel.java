package ve.com.joincic.joincicapp.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ve.com.joincic.joincicapp.controllers.Assistant;
import ve.com.joincic.joincicapp.controllers.Schedule;

/**
 * Created by carla on 25/05/15.
 */
public class AssistantModel {
    static final String TAG = "Models.Assistant";
    //Table name
    public static final String TABLE_ASSISTANT = "assistant";

    //Table name attributes
    public static final String C_ID = "id";
    public static final String C_CI = "ci";
    public static final String C_EMAIL = "email";
    public static final String C_FIRST_NAME = "firsT_name";
    public static final String C_LAST_NAME = "last_name";

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_ASSISTANT
            + "("
            + C_ID + " INTEGER PRIMARY KEY, "
            + C_CI + " INTEGER, "
            + C_EMAIL + " TEXT, "
            + C_FIRST_NAME + " TEXT, "
            + C_LAST_NAME + " TEXT "

            //Constraints
//            + ", FOREIGN KEY (" + C_COURSE_ID + ") " + "REFERENCES COURSE (id) "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        Log.d(TAG, DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(AssistantModel.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSISTANT);
        onCreate(database);
    }


    public static ContentValues assistantToValues(Assistant.Participante p) {
        ContentValues values = new ContentValues();
        values.put(C_ID, p.getId());
        values.put(C_CI, p.getCedula());
        values.put(C_EMAIL, p.getCorreo());
        values.put(C_FIRST_NAME, p.getNombre());
        values.put(C_LAST_NAME, p.getApellido());
        Log.d(TAG, "Values assistantToValues = " + values);
        return values;
    }
}

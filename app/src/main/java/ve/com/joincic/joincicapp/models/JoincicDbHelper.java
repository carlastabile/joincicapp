package ve.com.joincic.joincicapp.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carla Urrea Stabile on 4/23/15.
 */
public class JoincicDbHelper extends SQLiteOpenHelper {
    final static String TAG = "JoincicDbHelper";

    public final static String DB_NAME = "joincicapp.db";
    final static int DB_VERSION = 1;

    public JoincicDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //should create all tables and relations here
        PresentationModel.onCreate(db);
        WorkTableModel.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //should update all tables and relations here
        PresentationModel.onUpgrade(db, oldVersion, newVersion);
        WorkTableModel.onUpgrade(db, oldVersion, newVersion);
    }


    public boolean deleteDatabase(Context context){
        close();
        boolean b = context.deleteDatabase(DB_NAME);
        return b;
    }
}

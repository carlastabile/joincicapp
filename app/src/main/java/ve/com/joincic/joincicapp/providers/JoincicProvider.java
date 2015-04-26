package ve.com.joincic.joincicapp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import ve.com.joincic.joincicapp.models.JoincicDbHelper;
import ve.com.joincic.joincicapp.models.PresentationModel;
import ve.com.joincic.joincicapp.models.WorkTableModel;

/**
 * Created by Carla Urrea Stabile on 4/23/15.
 */
public class JoincicProvider extends ContentProvider{
    static final String TAG = "JoincicProvider";

    static JoincicDbHelper dbHelper;
    SQLiteDatabase db;

    private static final String AUTHORITY = "ve.com.joincic.joincicapp.providers";

    // Paths related to the user profile
    private static final String PATH_PRESENTATIONS = "presentations";
    private static final String PATH_WORK_TABLES = "work_tables";

    // Content URIS
    public static final Uri CONTENT_URI_PRESENTATIONS = Uri
            .parse("content://" + AUTHORITY + "/" + PATH_PRESENTATIONS);
    public static final Uri CONTENT_URI_WORK_TABLES = Uri
            .parse("content://" + AUTHORITY + "/" + PATH_WORK_TABLES);

    //Ids
    private static final int PRESENTATIONS = 1;
    private static final int PRESENTATION_ID = 2;
    private static final int WORK_TABLES = 3;
    private static final int WORK_TABLE_ID = 4;

    //UriMatcher
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PATH_PRESENTATIONS, PRESENTATIONS);
        sURIMatcher.addURI(AUTHORITY, PATH_PRESENTATIONS + "/#",
                PRESENTATION_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_WORK_TABLES, WORK_TABLES);
        sURIMatcher.addURI(AUTHORITY, PATH_WORK_TABLES + "/#",
                WORK_TABLE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new JoincicDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case PRESENTATIONS:
                queryBuilder.setTables(PresentationModel.TABLE_PRESENTATION);
                break;
            case PRESENTATION_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(PresentationModel.C_ID + "="
                        + uri.getLastPathSegment());
                break;
            case WORK_TABLES:
                queryBuilder.setTables(WorkTableModel.TABLE_WORK_TABLE);
                break;
            case WORK_TABLE_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(WorkTableModel.C_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sURIMatcher.match(uri);
        db = dbHelper.getWritableDatabase();
        long id = 0;

        switch (uriType) {

            case PRESENTATIONS:
                id = db.insertWithOnConflict(
                        PresentationModel.TABLE_PRESENTATION, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(PATH_PRESENTATIONS + "/" + id);
            case WORK_TABLES:
                id = db.insertWithOnConflict(
                        WorkTableModel.TABLE_WORK_TABLE, null, values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(PATH_WORK_TABLES + "/" + id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        db = dbHelper.getWritableDatabase();
        int count;

        switch (uriType) {
            case PRESENTATIONS:
                count = db.delete(PresentationModel.TABLE_PRESENTATION,
                        selection, selectionArgs);
                break;
            case WORK_TABLES:
                count = db.delete(WorkTableModel.TABLE_WORK_TABLE,
                        selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case PRESENTATIONS:
                rowsUpdated = sqlDB.update(PresentationModel.TABLE_PRESENTATION,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PRESENTATION_ID:
                String converId = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(PresentationModel.TABLE_PRESENTATION,
                            values,
                            PresentationModel.C_ID + "=" + converId,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(PresentationModel.TABLE_PRESENTATION,
                            values,
                            PresentationModel.C_ID + "=" + converId
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    public JoincicDbHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(JoincicDbHelper helper) {
        dbHelper = helper;
    }
}

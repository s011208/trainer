package yhh.bj4.trainer;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by User on 2016/4/21.
 */
public class TrainerProvider extends ContentProvider {
    private static final String TAG = "TrainerProvider";
    private static final boolean DEBUG = Utilities.DEBUG;

    private SQLiteDatabase mDatabase;

    private static final int MATCHER_TABLE_CALENDAR = 0;
    private static final int MATCHER_TABLE_TRAINING_DATA = 1;

    private static final UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sMatcher.addURI(TrainerSettings.PROVIDER_AUTHORITY, TrainerSettings.CalendarSettings.MATCHER_PATTERN, MATCHER_TABLE_CALENDAR);
        sMatcher.addURI(TrainerSettings.PROVIDER_AUTHORITY, TrainerSettings.TrainingDataSettings.MATCHER_PATTERN, MATCHER_TABLE_TRAINING_DATA);
    }

    @Override
    public boolean onCreate() {
        if (DEBUG) Log.d(TAG, "TrainerProvider created");
        mDatabase = new TrainerDatabase(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sMatcher.match(uri)) {
            case MATCHER_TABLE_CALENDAR:
                return mDatabase.query(TrainerSettings.CalendarSettings.TABLE_CALENDAR_SETTINGS, projection, selection, selectionArgs, null, null, sortOrder);
            case MATCHER_TABLE_TRAINING_DATA:
                return mDatabase.query(TrainerSettings.TrainingDataSettings.TABLE_TRAINING_DATA_SETTINGS, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new UnsupportedOperationException("Unknown query URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri rtn;
        switch (sMatcher.match(uri)) {
            case MATCHER_TABLE_CALENDAR:
                rtn = ContentUris.withAppendedId(uri, mDatabase.insert(TrainerSettings.CalendarSettings.TABLE_CALENDAR_SETTINGS, null, values));
                break;
            case MATCHER_TABLE_TRAINING_DATA:
                rtn = ContentUris.withAppendedId(uri, mDatabase.insert(TrainerSettings.TrainingDataSettings.TABLE_TRAINING_DATA_SETTINGS, null, values));
                break;
            default:
                throw new UnsupportedOperationException("Unknown insert URI: " + uri);
        }
        return sendNotify(rtn);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rtn;
        switch (sMatcher.match(uri)) {
            case MATCHER_TABLE_CALENDAR:
                rtn = mDatabase.delete(TrainerSettings.CalendarSettings.TABLE_CALENDAR_SETTINGS, selection, selectionArgs);
                break;
            case MATCHER_TABLE_TRAINING_DATA:
                rtn = mDatabase.delete(TrainerSettings.TrainingDataSettings.TABLE_TRAINING_DATA_SETTINGS, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown delete URI: " + uri);
        }
        sendNotify(uri);
        return rtn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rtn;
        switch (sMatcher.match(uri)) {
            case MATCHER_TABLE_CALENDAR:
                rtn = mDatabase.update(TrainerSettings.CalendarSettings.TABLE_CALENDAR_SETTINGS, values, selection, selectionArgs);
                break;
            case MATCHER_TABLE_TRAINING_DATA:
                rtn = mDatabase.update(TrainerSettings.TrainingDataSettings.TABLE_TRAINING_DATA_SETTINGS, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown update URI: " + uri);
        }
        sendNotify(uri);
        return rtn;
    }

    private Uri sendNotify(Uri uri) {
        String notify = uri.getQueryParameter(TrainerSettings.PARAMETER_NOTIFY);
        if (notify == null || "true".equals(notify)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    private static class TrainerDatabase extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "TrainerDatabase.db";
        private static final int VERSION = 1;

        public TrainerDatabase(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        private final static String CMD_CREATE_TABLE_CALENDAR = "CREATE TABLE IF NOT EXISTS "
                + TrainerSettings.CalendarSettings.TABLE_CALENDAR_SETTINGS + " ("
                + TrainerSettings.CalendarSettings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TrainerSettings.CalendarSettings.COLUMN_YEAR + " INTEGER NOT NULL, "
                + TrainerSettings.CalendarSettings.COLUMN_MONTH + " INTEGER NOT NULL, "
                + TrainerSettings.CalendarSettings.COLUMN_DAY_OF_MONTH + " INTEGER NOT NULL, "
                + TrainerSettings.CalendarSettings.COLUMN_TRAINING_ID + " INTEGER NOT NULL)";

        private static void createTableCalendar(SQLiteDatabase db) {
            if (db == null) return;
            db.execSQL(CMD_CREATE_TABLE_CALENDAR);
        }

        private final static String CMD_CREATE_TABLE_TRAINING_DATA = "CREATE TABLE IF NOT EXISTS "
                + TrainerSettings.TrainingDataSettings.TABLE_TRAINING_DATA_SETTINGS + " ("
                + TrainerSettings.TrainingDataSettings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME + " TEXT NOT NULL, "
                + TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH + " INTEGER NOT NULL, "
                + TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH_UNIT + " TEXT NOT NULL, "
                + TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES + " INTEGER NOT NULL, "
                + TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES_UNIT + " TEXT NOT NULL)";

        private static void createTableTrainingData(SQLiteDatabase db) {
            if (db == null) return;
            db.execSQL(CMD_CREATE_TABLE_TRAINING_DATA);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTableCalendar(db);
            createTableTrainingData(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

package yhh.bj4.trainer.calendar;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import yhh.bj4.trainer.TrainerSettings;
import yhh.bj4.trainer.Utilities;

/**
 * Created by User on 2016/4/24.
 */
public class QueryScheduleHelper extends AsyncTask<Void, Void, HashMap<Date, Integer>> {
    private static final String TAG = "QueryScheduleHelper";
    private static final boolean DEBUG = false;

    public interface Callback {
        void onFinishLoading(HashMap<Date, Integer> dates);
    }

    private final WeakReference<Context> mContext;
    private final WeakReference<Callback> mCallback;

    public QueryScheduleHelper(Context context, Callback cb) {
        super();
        if (DEBUG) {
            Log.e(TAG, "cb == null: " + (cb == null) + ", cb: " + cb);
        }
        mContext = new WeakReference<>(context);
        mCallback = new WeakReference<>(cb);
    }

    @Override
    protected HashMap<Date, Integer> doInBackground(Void... params) {
        final Context context = mContext.get();
        if (context == null) {
            if (DEBUG) {
                Log.i(TAG, "(context == null)");
            }
            return null;
        }
        final HashMap<Date, Integer> rtn = new HashMap<>();
        Cursor data = context.getContentResolver().query(TrainerSettings.CalendarSettings.getUri(false), null, null, null, null, null);
        if (data == null) {
            if (DEBUG) {
                Log.i(TAG, "(data == null)");
            }
            return null;
        }
        try {
            final int indexOfYear = data.getColumnIndex((TrainerSettings.CalendarSettings.COLUMN_YEAR));
            final int indexOfMonth = data.getColumnIndex((TrainerSettings.CalendarSettings.COLUMN_MONTH));
            final int indexOfDay = data.getColumnIndex((TrainerSettings.CalendarSettings.COLUMN_DAY_OF_MONTH));
            Calendar calendar = Calendar.getInstance();
            while (data.moveToNext()) {
                final int year = data.getInt(indexOfYear);
                final int month = data.getInt(indexOfMonth) - 1;
                final int day = data.getInt(indexOfDay);
                calendar.set(year, month, day);
                Date d = calendar.getTime();
                if (rtn.containsKey(d)) {
                    int v = rtn.get(d);
                    rtn.put(d, ++v);
                } else {
                    rtn.put(d, 1);
                }
            }
        } finally {
            data.close();
        }
        return rtn;
    }

    @Override
    protected void onPostExecute(HashMap<Date, Integer> dates) {
        if (dates == null || dates.isEmpty()) {
            if (DEBUG) {
                Log.i(TAG, "(dates == null): " + (dates == null) + ", dates.isEmpty(): " + dates.isEmpty());
            }
            return;
        }
        super.onPostExecute(dates);
        final Callback cb = mCallback.get();
        if (cb == null) {
            if (DEBUG) Log.i(TAG, "cb == null");
            return;
        }
        if (DEBUG) {
            Log.v(TAG, "onPostExecute, dates size: " + dates.size());
        }
        cb.onFinishLoading(dates);
    }
}

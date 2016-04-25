package yhh.bj4.trainer.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.TrainerSettings;
import yhh.bj4.trainer.Utilities;

/**
 * Created by Yen-Hsun_Huang on 2016/4/22.
 */
public class AddScheduleDialogFragment extends DialogFragment {

    private static final String TAG = "AddScheduleDialog";
    private static final boolean DEBUG = Utilities.DEBUG;

    private static final int AUTO_COMPLETE_TEXT_THRESHOLD = 0;

    public static final String KEY_YEAR = "k_y";
    public static final String KEY_MONTH = "k_m";
    public static final String KEY_DAY_OF_MONTH = "k_d_m";

    private int mScheduleYear, mScheduleMonth, mScheduleDayOfMonth;

    private AutoCompleteTextView mTrainingItem, mTrainingStrength, mTrainingStrengthUnit, mTrainingTimes, mTrainingTimesUnit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mScheduleYear = arguments.getInt(KEY_YEAR);
            mScheduleMonth = arguments.getInt(KEY_MONTH);
            mScheduleDayOfMonth = arguments.getInt(KEY_DAY_OF_MONTH);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new AlertDialog.Builder(getActivity()).setCustomTitle(getCustomTitleView(inflater)).setView(getCustomContent(inflater)).setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (saveTrainingData()) {
                            getTargetFragment().onActivityResult(
                                    getTargetRequestCode(), Activity.RESULT_OK, null);
                        } else {
                            Toast.makeText(getActivity(), R.string.dialog_add_schedule_content_add_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private boolean saveTrainingData() {
        if (DEBUG) {
            Log.d(TAG, mScheduleYear + "-" + mScheduleMonth + "-" + mScheduleDayOfMonth + "\n"
                    + "item: " + mTrainingItem.getText() + "\n"
                    + "intensity: " + mTrainingStrength.getText() + ", unit: " + mTrainingStrengthUnit.getText() + "\n"
                    + "times: " + mTrainingTimes.getText() + ", unit: " + mTrainingTimesUnit.getText());
        }
        if (TextUtils.isEmpty(mTrainingItem.getText())) {
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME, mTrainingItem.getText().toString());
        cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH, mTrainingStrength.getText().toString());
        cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH_UNIT, mTrainingStrengthUnit.getText().toString());
        cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES, mTrainingTimes.getText().toString());
        cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES_UNIT, mTrainingTimesUnit.getText().toString());
        cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_ADD_TIME, System.currentTimeMillis());
        // TODO add location

        Uri insertTrainingDataResult = getActivity().getContentResolver().insert(TrainerSettings.TrainingDataSettings.getUri(true), cv);
        long id = ContentUris.parseId(insertTrainingDataResult);
        if (DEBUG) {
            Log.d(TAG, "insertTrainingDataResult id: " + id);
        }
        cv = new ContentValues();
        cv.put(TrainerSettings.CalendarSettings.COLUMN_TRAINING_ID, id);
        cv.put(TrainerSettings.CalendarSettings.COLUMN_YEAR, mScheduleYear);
        cv.put(TrainerSettings.CalendarSettings.COLUMN_MONTH, mScheduleMonth);
        cv.put(TrainerSettings.CalendarSettings.COLUMN_DAY_OF_MONTH, mScheduleDayOfMonth);
        getActivity().getContentResolver().insert(TrainerSettings.CalendarSettings.getUri(true), cv);
        return true;
    }

    private View getCustomContent(LayoutInflater inflater) {
        View rtn = inflater.inflate(R.layout.dialog_add_schedule_content, null);
        mTrainingItem = (AutoCompleteTextView) rtn.findViewById(R.id.train_item);
        mTrainingStrength = (AutoCompleteTextView) rtn.findViewById(R.id.train_strength);
        mTrainingStrengthUnit = (AutoCompleteTextView) rtn.findViewById(R.id.train_strength_unit);
        mTrainingTimes = (AutoCompleteTextView) rtn.findViewById(R.id.train_times);
        mTrainingTimesUnit = (AutoCompleteTextView) rtn.findViewById(R.id.train_times_unit);

        mTrainingItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Cursor c = getActivity().getContentResolver().query(TrainerSettings.TrainingDataSettings.getUri(false), null,
                        TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME + "='" + mTrainingItem.getText() + "'",
                        null, TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_ADD_TIME + " DESC LIMIT 1");
                if (c == null) {
                    return;
                }
                ArrayList<TrainerSettings.TrainingDataSettings> items = TrainerSettings.TrainingDataSettings.from(c);
                if (items.isEmpty()) return;
                TrainerSettings.TrainingDataSettings item = items.get(0);
                if (TextUtils.isEmpty(mTrainingStrength.getText().toString())) {
                    mTrainingStrength.setText(String.valueOf(item.getTrainingStrength()));
                }
                if (TextUtils.isEmpty(mTrainingStrengthUnit.getText().toString())) {
                    mTrainingStrengthUnit.setText(item.getTrainingStrengthUnit());
                }
                if (TextUtils.isEmpty(mTrainingTimes.getText().toString())) {
                    mTrainingTimes.setText(String.valueOf(item.getTrainingTime()));
                }
                if (TextUtils.isEmpty(mTrainingTimesUnit.getText().toString())) {
                    mTrainingTimesUnit.setText(item.getTrainingTimeUnit());
                }
            }
        });

        mTrainingItem.setThreshold(AUTO_COMPLETE_TEXT_THRESHOLD);
        mTrainingStrength.setThreshold(AUTO_COMPLETE_TEXT_THRESHOLD);
        mTrainingStrengthUnit.setThreshold(AUTO_COMPLETE_TEXT_THRESHOLD);
        mTrainingTimes.setThreshold(AUTO_COMPLETE_TEXT_THRESHOLD);
        mTrainingTimesUnit.setThreshold(AUTO_COMPLETE_TEXT_THRESHOLD);

        ArrayList<String> trainingItemData = retrieveAutoCompleteItems(getActivity(), TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME);
        if (trainingItemData != null && !trainingItemData.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, trainingItemData);
            mTrainingItem.setAdapter(adapter);
        } else {
            new RetrieveAutoCompleteItemsHelper(mTrainingItem, getActivity(), TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        new RetrieveAutoCompleteItemsHelper(mTrainingStrength, getActivity(), TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new RetrieveAutoCompleteItemsHelper(mTrainingStrengthUnit, getActivity(), TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH_UNIT).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new RetrieveAutoCompleteItemsHelper(mTrainingTimes, getActivity(), TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new RetrieveAutoCompleteItemsHelper(mTrainingTimesUnit, getActivity(), TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES_UNIT).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return rtn;
    }

    private View getCustomTitleView(LayoutInflater inflater) {
        final View title = inflater.inflate(R.layout.dialog_add_schedule_title, null);
        final TextView currentDate = (TextView) title.findViewById(R.id.current_date);
        final ImageView moveToPrevious = (ImageView) title.findViewById(R.id.previous);
        moveToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDate(currentDate, -1);
            }
        });
        final ImageView moveToNext = (ImageView) title.findViewById(R.id.next);
        moveToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDate(currentDate, 1);
            }
        });
        setTextDate(currentDate, 0);
        return title;
    }

    private void setTextDate(TextView text, int add) {
        Calendar c = Calendar.getInstance();
        c.set(mScheduleYear, mScheduleMonth - 1, mScheduleDayOfMonth);
        if (add != 0) {
            c.add(Calendar.DATE, add);
        }
        mScheduleYear = c.get(Calendar.YEAR);
        mScheduleMonth = c.get(Calendar.MONTH) + 1;
        mScheduleDayOfMonth = c.get(Calendar.DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy.MM.dd");
        text.setText(dateFormat.format(c.getTime()));
    }

    private static ArrayList<String> retrieveAutoCompleteItems(Context context, String column) {
        Cursor data = context.getContentResolver().query(TrainerSettings.TrainingDataSettings.getUri(false), new String[]{column}, column + "!=''", null, null, null);
        HashSet<String> set = new HashSet<>();
        if (data == null) return null;
        try {
            while (data.moveToNext()) {
                set.add(data.getString(0));
            }
        } finally {
            data.close();
        }
        if (set.isEmpty()) return null;
        return new ArrayList<>(set);
    }

    private static class RetrieveAutoCompleteItemsHelper extends AsyncTask<Void, Void, Void> {
        private final AutoCompleteTextView mTarget;
        private final ArrayList<String> mContent = new ArrayList<>();
        private final Context mContext;
        private final String mColumn;

        public RetrieveAutoCompleteItemsHelper(AutoCompleteTextView target, Context context, String column) {
            super();
            mTarget = target;
            mContext = context;
            mColumn = column;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (mContext == null) return null;
            ArrayList<String> data = retrieveAutoCompleteItems(mContext, mColumn);
            if (data == null) return null;
            mContent.addAll(data);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mContent.isEmpty()) return;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mContent);
            mTarget.setAdapter(adapter);
        }
    }
}

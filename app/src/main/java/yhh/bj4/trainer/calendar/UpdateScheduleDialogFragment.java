package yhh.bj4.trainer.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.TrainerSettings;
import yhh.bj4.trainer.Utilities;
import yhh.bj4.trainer.dialog.ConfirmDialog;

/**
 * Created by yenhsunhuang on 2016/4/25.
 */
public class UpdateScheduleDialogFragment extends DialogFragment {
    private static final boolean DEBUG = Utilities.DEBUG;

    private static final String TAG = "UpdateScheduleDialog";
    private static final int AUTO_COMPLETE_TEXT_THRESHOLD = 0;
    public static final String KEY_YEAR = "k_y";
    public static final String KEY_MONTH = "k_m";
    public static final String KEY_DAY_OF_MONTH = "k_d_m";
    public static final String KEY_TRAINING_DATA = "t_d";
    public static final String INTENT_DELETE_ITEM = "delete_item";

    private static final int REQUEST_CONFIRM_DELETE = 0;

    private int mScheduleYear, mScheduleMonth, mScheduleDayOfMonth;
    private AutoCompleteTextView mTrainingItem, mTrainingStrength, mTrainingStrengthUnit, mTrainingTimes, mTrainingTimesUnit;
    private TrainerSettings.TrainingDataSettings mTrainingData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mScheduleYear = arguments.getInt(KEY_YEAR);
            mScheduleMonth = arguments.getInt(KEY_MONTH);
            mScheduleDayOfMonth = arguments.getInt(KEY_DAY_OF_MONTH);
            mTrainingData = new TrainerSettings.TrainingDataSettings(arguments.getString(KEY_TRAINING_DATA));
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
                        ContentValues cv = new ContentValues();
                        if (!TextUtils.isEmpty(mTrainingItem.getText())) {
                            cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME, mTrainingItem.getText().toString());
                        }
                        if (!TextUtils.isEmpty(mTrainingStrength.getText())) {
                            cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH, Integer.valueOf(mTrainingStrength.getText().toString()));
                        }
                        if (!TextUtils.isEmpty(mTrainingStrengthUnit.getText())) {
                            cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH_UNIT, mTrainingStrengthUnit.getText().toString());
                        }
                        if (!TextUtils.isEmpty(mTrainingTimes.getText())) {
                            cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES, Integer.valueOf(mTrainingTimes.getText().toString()));
                        }
                        if (!TextUtils.isEmpty(mTrainingTimesUnit.getText())) {
                            cv.put(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES_UNIT, mTrainingTimesUnit.getText().toString());
                        }
                        if (cv.size() > 0) {
                            getActivity().getContentResolver().update(TrainerSettings.TrainingDataSettings.getUri(true), cv, TrainerSettings.TrainingDataSettings.COLUMN_ID + "=" + mTrainingData.getId(), null);
                            Intent intent = getActivity().getIntent();
                            intent.putExtra(INTENT_DELETE_ITEM, false);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNeutralButton(R.string.dialog_update_schedule_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog == null) return;
        Button neutralButton = (Button) dialog.getButton(Dialog.BUTTON_NEUTRAL);
        if (neutralButton == null) return;
        neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog confirmDialog = new ConfirmDialog();
                Bundle argument = new Bundle();
                argument.putString(ConfirmDialog.KEY_TITLE, getActivity().getResources().getString(R.string.dialog_update_schedule_confirm_delete_title));
                argument.putString(ConfirmDialog.KEY_MESSAGE, getActivity().getResources().getString(R.string.dialog_update_schedule_confirm_delete_message));
                confirmDialog.setArguments(argument);
                confirmDialog.setTargetFragment(UpdateScheduleDialogFragment.this, REQUEST_CONFIRM_DELETE);
                confirmDialog.show(getFragmentManager(), confirmDialog.getClass().getName());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONFIRM_DELETE) {
            if (resultCode == Activity.RESULT_OK) {
                boolean confirmed = data.getBooleanExtra(ConfirmDialog.INTENT_ON_CONFIRM, false);
                if (DEBUG) Log.i(TAG, "confirmed: " + confirmed);
                if (confirmed) {
                    getActivity().getContentResolver().delete(TrainerSettings.TrainingDataSettings.getUri(true), TrainerSettings.TrainingDataSettings.COLUMN_ID + "=" + mTrainingData.getId(), null);
                    getActivity().getContentResolver().delete(TrainerSettings.CalendarSettings.getUri(true), TrainerSettings.CalendarSettings.COLUMN_TRAINING_ID + "=" + mTrainingData.getId(), null);
                    Intent intent = getActivity().getIntent();
                    intent.putExtra(INTENT_DELETE_ITEM, true);
                    intent.putExtra(KEY_YEAR, mScheduleYear);
                    intent.putExtra(KEY_MONTH, mScheduleMonth);
                    intent.putExtra(KEY_DAY_OF_MONTH, mScheduleDayOfMonth);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                    dismiss();
                }
            }
        }
    }

    private View getCustomContent(LayoutInflater inflater) {
        View rtn = inflater.inflate(R.layout.dialog_add_schedule_content, null);
        mTrainingItem = (AutoCompleteTextView) rtn.findViewById(R.id.train_item);
        mTrainingStrength = (AutoCompleteTextView) rtn.findViewById(R.id.train_strength);
        mTrainingStrengthUnit = (AutoCompleteTextView) rtn.findViewById(R.id.train_strength_unit);
        mTrainingTimes = (AutoCompleteTextView) rtn.findViewById(R.id.train_times);
        mTrainingTimesUnit = (AutoCompleteTextView) rtn.findViewById(R.id.train_times_unit);

        mTrainingItem.setHint(mTrainingData.getTrainingName());
        mTrainingStrength.setHint(String.valueOf(mTrainingData.getTrainingStrength()));
        mTrainingStrengthUnit.setHint(mTrainingData.getTrainingStrengthUnit());
        mTrainingTimes.setHint(String.valueOf(mTrainingData.getTrainingTime()));
        mTrainingTimesUnit.setHint(mTrainingData.getTrainingTimeUnit());

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
        currentDate.setText(mScheduleYear + "." + mScheduleMonth + "." + mScheduleDayOfMonth);
        final ImageView moveToPrevious = (ImageView) title.findViewById(R.id.previous);
        moveToPrevious.setVisibility(View.INVISIBLE);
        final ImageView moveToNext = (ImageView) title.findViewById(R.id.next);
        moveToNext.setVisibility(View.INVISIBLE);
        return title;
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

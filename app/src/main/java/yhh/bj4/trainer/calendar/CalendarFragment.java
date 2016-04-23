package yhh.bj4.trainer.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.TrainerSettings;
import yhh.bj4.trainer.Utilities;
import yhh.bj4.trainer.ViewPagerCallbackFragment;

/**
 * Created by User on 2016/4/18.
 */
public class CalendarFragment extends ViewPagerCallbackFragment {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "CalendarFragment";

    private static final int ANIMATION_FLOATING_ACTION_BUTTON_DISTANCE = 150;
    private static final int ANIMATION_FLOATING_ACTION_BUTTON_DURATION = 250;

    private static final int REQUEST_ADD_SCHEDULE = 0;
    private static final int SELECTED_DATE_TEXT_COLOR = android.R.color.holo_blue_dark;
    private static final int SCHEDULED_DATE_BACKGROUND_DRAWABLE_RESOURCE = R.drawable.scheduled_date_color;

    private View mRoot;
    private CaldroidFragment mCaldroidFragment;
    private ListView mScheduleList;
    private FloatingActionButton mAddSchedule;
    private Date mSelectedDate;

    private int mSelectedYear, mSelectedMonth, mSelectedDayOfMonth;
    private final ArrayList<Date> mScheduledDate = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCaldroidFragment = new CaldroidFragment();
        if (savedInstanceState != null) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.calendar_fragment, null);
        initComponents();
        return mRoot;
    }

    private static Date getDate(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.set(y, m, d);
        return c.getTime();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setScheduleAdapter() {
        mScheduleList.setAdapter(null);
        ScheduleAdapter adapter = new ScheduleAdapter(getActivity(), mSelectedYear, mSelectedMonth, mSelectedDayOfMonth);
        mScheduleList.setAdapter(adapter);
    }

    private void initComponents() {
        Bundle args = new Bundle();
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
        mCaldroidFragment.setArguments(args);
        mCaldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onCaldroidViewCreated() {
                super.onCaldroidViewCreated();
                Calendar c = Calendar.getInstance();
                mCaldroidFragment.setSelectedDate(c.getTime());
                mSelectedYear = c.get(Calendar.YEAR);
                mSelectedMonth = c.get(Calendar.MONTH);
                mSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                mSelectedDate = c.getTime();
                mCaldroidFragment.setTextColorForDate(SELECTED_DATE_TEXT_COLOR, mSelectedDate);
                mCaldroidFragment.setBackgroundDrawableForDate(getActivity().getResources().getDrawable(SCHEDULED_DATE_BACKGROUND_DRAWABLE_RESOURCE), mSelectedDate);
                mCaldroidFragment.refreshView();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                super.onChangeMonth(month, year);
            }

            @Override
            public void onSelectDate(Date date, View view) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                if (DEBUG) {
                    Log.d(TAG, "y: " + year + ", m: " + (month + 1) + ", d: " + dayOfMonth);
                }
                mSelectedYear = year;
                mSelectedMonth = month + 1;
                mSelectedDayOfMonth = dayOfMonth;
                setScheduleAdapter();
                mCaldroidFragment.clearTextColorForDate(mSelectedDate);
                mSelectedDate = date;
                mCaldroidFragment.setTextColorForDate(SELECTED_DATE_TEXT_COLOR, mSelectedDate);
                mCaldroidFragment.setBackgroundDrawableForDate(getActivity().getResources().getDrawable(SCHEDULED_DATE_BACKGROUND_DRAWABLE_RESOURCE), mSelectedDate);
                mCaldroidFragment.refreshView();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.calendar_view, mCaldroidFragment).commit();

        mScheduleList = (ListView) mRoot.findViewById(R.id.calendar_plan);
        setScheduleAdapter();

        mAddSchedule = (FloatingActionButton) mRoot.findViewById(R.id.add_schedule);
        mAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) {
                    Log.d(TAG, "click add schedule, y: " + mSelectedYear + ", m: " + mSelectedMonth
                            + ", d: " + mSelectedDayOfMonth);
                }
                AddScheduleDialogFragment dialog = new AddScheduleDialogFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(AddScheduleDialogFragment.KEY_YEAR, mSelectedYear);
                arguments.putInt(AddScheduleDialogFragment.KEY_MONTH, mSelectedMonth);
                arguments.putInt(AddScheduleDialogFragment.KEY_DAY_OF_MONTH, mSelectedDayOfMonth);
                dialog.setArguments(arguments);
                dialog.setTargetFragment(CalendarFragment.this, REQUEST_ADD_SCHEDULE);
                dialog.show(getFragmentManager(), AddScheduleDialogFragment.class.getName());
            }
        });
//        mAddSchedule.setTranslationY(ANIMATION_FLOATING_ACTION_BUTTON_DISTANCE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_SCHEDULE) {
            if (resultCode == Activity.RESULT_OK) {
                if (DEBUG) {
                    Log.d(TAG, "add new schedule");
                }
                setScheduleAdapter();
            }
        }
    }

    @Override
    public void onVisible() {
        if (mAddSchedule != null) {
//            mAddSchedule.animate().translationY(0).setDuration(ANIMATION_FLOATING_ACTION_BUTTON_DURATION).start();
        }
    }

    @Override
    public void onInVisible() {
    }

    private static class ScheduleAdapter extends BaseAdapter {
        private final Context mContext;
        private final LayoutInflater mInflater;
        private final int mYear, mMonth, mDay;
        private final ArrayList<TrainerSettings.TrainingDataSettings> mData = new ArrayList<>();

        public ScheduleAdapter(Context context, int y, int m, int d) {
            mContext = context;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mYear = y;
            mMonth = m;
            mDay = d;
            if (DEBUG) {
                Log.d(TAG, "query date: " + mYear + "-" + mMonth + "-" + mDay);
            }
            new AsyncTask<Void, Void, ArrayList<TrainerSettings.TrainingDataSettings>>() {
                @Override
                protected ArrayList<TrainerSettings.TrainingDataSettings> doInBackground(Void... params) {
                    Cursor today = mContext.getContentResolver()
                            .query(TrainerSettings.CalendarSettings.getUri(false),
                                    new String[]{TrainerSettings.CalendarSettings.COLUMN_TRAINING_ID},
                                    TrainerSettings.CalendarSettings.COLUMN_YEAR + "=" + mYear + " and " +
                                            TrainerSettings.CalendarSettings.COLUMN_MONTH + "=" + mMonth + " and " +
                                            TrainerSettings.CalendarSettings.COLUMN_DAY_OF_MONTH + "=" + mDay, null, null, null);
                    if (today == null) return null;
                    Set todayScheduleIdSet = new HashSet();
                    try {
                        while (today.moveToNext()) {
                            todayScheduleIdSet.add(today.getInt(0));
                        }
                    } finally {
                        today.close();
                    }
                    if (todayScheduleIdSet.isEmpty()) return null;
                    Iterator<Integer> idIterator = todayScheduleIdSet.iterator();
                    String where = "";
                    while (idIterator.hasNext()) {
                        if (!TextUtils.isEmpty(where)) {
                            where += " or ";
                        }
                        where += TrainerSettings.CalendarSettings.COLUMN_ID + "=" + idIterator.next();
                    }
                    if (DEBUG) {
                        Log.d(TAG, "where: " + where);
                    }
                    Cursor schedule = mContext.getContentResolver().query(TrainerSettings.TrainingDataSettings.getUri(false), null, where, null, null, null);
                    if (schedule == null) return null;
                    ArrayList<TrainerSettings.TrainingDataSettings> rtn = new ArrayList<>();
                    try {
                        final int indexOfTrainingName = schedule.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME);
                        final int indexOfTrainingStrength = schedule.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH);
                        final int indexOfTrainingStrengthUnit = schedule.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH_UNIT);
                        final int indexOfTrainingTime = schedule.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES);
                        final int indexOfTrainingTimeUnit = schedule.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES_UNIT);
                        while (schedule.moveToNext()) {
                            rtn.add(new TrainerSettings.TrainingDataSettings(
                                    schedule.getString(indexOfTrainingName),
                                    schedule.getInt(indexOfTrainingTime),
                                    schedule.getString(indexOfTrainingTimeUnit),
                                    schedule.getInt(indexOfTrainingStrength),
                                    schedule.getString(indexOfTrainingStrengthUnit)));
                            if (DEBUG) {
                                Log.v(TAG, "item: " + rtn.get(rtn.size() - 1));
                            }
                        }
                    } finally {
                        schedule.close();
                    }
                    return rtn;
                }

                @Override
                protected void onPostExecute(ArrayList<TrainerSettings.TrainingDataSettings> trainingDataSettings) {
                    super.onPostExecute(trainingDataSettings);
                    mData.clear();
                    if (trainingDataSettings == null) return;
                    if (DEBUG)
                        Log.d(TAG, "onPostExecute trainingDataSettings size: " + trainingDataSettings.size());
                    mData.addAll(trainingDataSettings);
                    notifyDataSetChanged();
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public TrainerSettings.TrainingDataSettings getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.calendar_schedule_list, null);
                convertView.setTag(holder);
                holder.mTrainingName = (TextView) convertView.findViewById(R.id.schedule_training_item);
                holder.mTrainingStrength = (TextView) convertView.findViewById(R.id.schedule_training_strength);
                holder.mTrainingStrengthUnit = (TextView) convertView.findViewById(R.id.schedule_training_strength_unit);
                holder.mTrainingTimes = (TextView) convertView.findViewById(R.id.schedule_training_times);
                holder.mTrainingTimesUnit = (TextView) convertView.findViewById(R.id.schedule_training_times_unit);
                holder.mTrainingStrengthContainer = (LinearLayout) convertView.findViewById(R.id.schedule_training_strength_container);
                holder.mTrainingTimesContainer = (LinearLayout) convertView.findViewById(R.id.schedule_training_times_container);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTrainingName.setText(getItem(position).getTrainingName());
            holder.mTrainingStrength.setText(String.valueOf(getItem(position).getTrainingStrength()));
            holder.mTrainingStrengthUnit.setText(getItem(position).getTrainingStrengthUnit());
            holder.mTrainingTimes.setText(String.valueOf(getItem(position).getTrainingTime()));
            holder.mTrainingTimesUnit.setText(getItem(position).getTrainingTimeUnit());
            holder.mTrainingStrengthContainer.setVisibility(TextUtils.isEmpty(holder.mTrainingStrengthUnit.getText()) ? View.INVISIBLE : View.VISIBLE);
            holder.mTrainingTimesContainer.setVisibility(TextUtils.isEmpty(holder.mTrainingTimesUnit.getText()) ? View.INVISIBLE : View.VISIBLE);
            return convertView;
        }

        private static class ViewHolder {
            private TextView mTrainingName;
            private TextView mTrainingStrength, mTrainingStrengthUnit;
            private TextView mTrainingTimes, mTrainingTimesUnit;
            private LinearLayout mTrainingStrengthContainer, mTrainingTimesContainer;
        }
    }
}

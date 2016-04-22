package yhh.bj4.trainer.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;

import yhh.bj4.trainer.R;
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

    private View mRoot;
    private CalendarView mCalendarView;
    private ListView mScheduleList;
    private FloatingActionButton mAddSchedule;

    private int mSelectedYear, mSelectedMonth, mSelectedDayOfMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.calendar_fragment, null);
        initComponents();
        return mRoot;
    }

    private void initComponents() {
        mCalendarView = (CalendarView) mRoot.findViewById(R.id.calendar_view);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (DEBUG) {
                    Log.d(TAG, "y: " + year + ", m: " + month + ", d: " + dayOfMonth);
                }
                mSelectedYear = year;
                mSelectedMonth = month + 1;
                mSelectedDayOfMonth = dayOfMonth;
            }
        });

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(mCalendarView.getDate()));
        mSelectedYear = c.get(Calendar.YEAR);
        mSelectedMonth = c.get(Calendar.MONTH);
        mSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        mScheduleList = (ListView) mRoot.findViewById(R.id.calendar_plan);
        mAddSchedule = (FloatingActionButton) mRoot.findViewById(R.id.add_schedule);
        mAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG)
                    Log.d(TAG, "click add schedule, y: " + mSelectedYear + ", m: " + mSelectedMonth
                            + ", d: " + mSelectedDayOfMonth);
                AddScheduleDialogFragment dialog = new AddScheduleDialogFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(AddScheduleDialogFragment.KEY_YEAR, mSelectedYear);
                arguments.putInt(AddScheduleDialogFragment.KEY_MONTH, mSelectedMonth);
                arguments.putInt(AddScheduleDialogFragment.KEY_DAY_OF_MONTH, mSelectedDayOfMonth);
                dialog.setArguments(arguments);
                dialog.show(getFragmentManager(), AddScheduleDialogFragment.class.getName());
            }
        });
//        mAddSchedule.setTranslationY(ANIMATION_FLOATING_ACTION_BUTTON_DISTANCE);
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
}

package yhh.bj4.trainer.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.Utilities;

/**
 * Created by User on 2016/4/18.
 */
public class CalendarFragment extends Fragment {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "CalendarFragment";

    private View mRoot;
    private CalendarView mCalendarView;
    private ListView mScheduleList;
    private FloatingActionButton mAddSchedule;

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
            }
        });
        mScheduleList = (ListView) mRoot.findViewById(R.id.calendar_plan);
        mAddSchedule = (FloatingActionButton) mRoot.findViewById(R.id.add_schedule);
        mAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click add schedule");
            }
        });
    }
}

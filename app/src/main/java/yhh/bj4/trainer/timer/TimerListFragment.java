package yhh.bj4.trainer.timer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.Utilities;

/**
 * Created by Yen-Hsun_Huang on 2016/4/18.
 */
public class TimerListFragment extends Fragment {
    private static final String TAG = "TimerListFragment";
    private static final boolean DEBUG = Utilities.DEBUG;

    private static final String TIMER_STATE = "timer_state";
    private static final int TIMER_STATE_NONE = 0;
    private static final int TIMER_STATE_STARTING = 1;
    private static final int TIMER_STATE_STOP = 2;

    private static final String TIME_HOUR = "h";
    private static final String TIME_MINUTE = "m";
    private static final String TIME_SECOND = "s";

    private static final String LAP_TIME_HOUR = "lh";
    private static final String LAP_IME_MINUTE = "lm";
    private static final String LAP_TIME_SECOND = "ls";

    private static final String TIME_LAP_DATA = "time_lap_data";

    private int mTimerState = TIMER_STATE_NONE;

    private View mRoot;
    private TextView mTotalHourView, mTotalMinuteView, mTotalSecondView;
    private ViewSwitcher mButtonSwitcher;
    private TextView mStart, mReset, mStop, mLap;
    private ListView mTimerListView;

    private TimerListAdapter mTimerListAdapter;

    private int mHour, mMinute, mSecond;
    private int mLapHour, mLapMinute, mLapSecond;
    private TimerTask mTimerTask;
    private Timer mTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Log.d(TAG, "onCreate");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(TIME_HOUR, mHour);
        outState.putInt(TIME_MINUTE, mMinute);
        outState.putInt(TIME_SECOND, mSecond);
        outState.putInt(TIMER_STATE, mTimerState);
        outState.putInt(LAP_TIME_HOUR, mLapHour);
        outState.putInt(LAP_IME_MINUTE, mLapMinute);
        outState.putInt(LAP_TIME_SECOND, mLapSecond);

        ArrayList<TimerListData> data = mTimerListAdapter.getData();
        if (!data.isEmpty()) {
            ArrayList<String> jsonData = new ArrayList<>();
            for (TimerListData d : data) {
                jsonData.add(d.toJson().toString());
            }
            outState.putStringArrayList(TIME_LAP_DATA, jsonData);
        }
        super.onSaveInstanceState(outState);
        if (DEBUG) {
            Log.d(TAG, "onSaveInstanceState");
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (DEBUG) {
            Log.d(TAG, "onViewStateRestored");
        }
        if (savedInstanceState == null) return;
        mHour = savedInstanceState.getInt(TIME_HOUR, 0);
        mMinute = savedInstanceState.getInt(TIME_MINUTE, 0);
        mSecond = savedInstanceState.getInt(TIME_SECOND, 0);

        mLapHour = savedInstanceState.getInt(LAP_TIME_HOUR, 0);
        mLapMinute = savedInstanceState.getInt(LAP_IME_MINUTE, 0);
        mLapSecond = savedInstanceState.getInt(LAP_TIME_SECOND, 0);
        updateTimerUi();
        mTimerState = savedInstanceState.getInt(TIMER_STATE, TIMER_STATE_NONE);
        switch (mTimerState) {
            case TIMER_STATE_NONE:
                break;
            case TIMER_STATE_STARTING:
                mButtonSwitcher.setDisplayedChild(1);
                mReset.setVisibility(View.VISIBLE);
                startTimer();
                break;
            case TIMER_STATE_STOP:
                mButtonSwitcher.setDisplayedChild(0);
                mReset.setVisibility(View.VISIBLE);
                break;
        }
        ArrayList<String> jsonData = savedInstanceState.getStringArrayList(TIME_LAP_DATA);
        if (jsonData != null) {
            ArrayList<TimerListData> data = new ArrayList<>();
            for (String json : jsonData) {
                data.add(new TimerListData((json)));
            }
            mTimerListAdapter.setData(data);
            mTimerListAdapter.notifyDataSetChanged();
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                addSecond();
            }
        };
    }

    private void addSecond() {
        ++mSecond;
        if (mSecond == 60) {
            mSecond = 0;
            ++mMinute;
        }
        if (mMinute == 60) {
            mMinute = 0;
            ++mHour;
        }

        if (!mTimerListAdapter.isEmpty()) {
            ++mLapSecond;
            if (mLapSecond == 60) {
                mLapSecond = 0;
                ++mLapMinute;
            }
            if (mLapMinute == 60) {
                mLapMinute = 0;
                ++mLapHour;
            }
        }

        updateTimerUi();
    }

    private void updateTimerUi() {
        final Activity activity = getActivity();
        if (activity == null) return;
        final int second = mSecond;
        final int minute = mMinute;
        final int hour = mHour;

        final int lapSecond = mLapSecond;
        final int lapMinute = mLapMinute;
        final int lapHour = mLapHour;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTotalHourView.setText(hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour));
                mTotalMinuteView.setText(mMinute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute));
                mTotalSecondView.setText(mSecond < 10 ? "0" + String.valueOf(second) : String.valueOf(second));
                mTimerListAdapter.setCurrentTimer(hour, minute, second);
                mTimerListAdapter.setLapTimer(lapHour, lapMinute, lapSecond);
                mTimerListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void resetSecond() {
        mSecond = mMinute = mHour = 0;
        mLapHour = mLapMinute = mLapSecond = 0;
        updateTimerUi();
    }

    private void startTimer() {
        mTimerState = TIMER_STATE_STARTING;
        initTimer();
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private void lapTimer() {
        mLapHour = mLapMinute = mLapSecond = 0;
    }

    private void stopTimer() {
        mTimerState = TIMER_STATE_STOP;
        mTimer.cancel();
    }

    private void resetTimer() {
        mTimerState = TIMER_STATE_NONE;
        resetSecond();
    }

    private void initComponents(View root) {
        mTotalHourView = (TextView) root.findViewById(R.id.total_timer_hour);
        mTotalMinuteView = (TextView) root.findViewById(R.id.total_timer_minute);
        mTotalSecondView = (TextView) root.findViewById(R.id.total_timer_second);

        mButtonSwitcher = (ViewSwitcher) root.findViewById(R.id.button_switcher);
        mStart = (TextView) root.findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonSwitcher.setDisplayedChild(1);
                mReset.setVisibility(View.VISIBLE);
                startTimer();
            }
        });
        mReset = (TextView) root.findViewById(R.id.reset);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReset.setVisibility(View.GONE);
                resetTimer();
                mTimerListAdapter.setData(new ArrayList<TimerListData>());
                mTimerListAdapter.notifyDataSetChanged();
            }
        });
        mStop = (TextView) root.findViewById(R.id.stop);
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonSwitcher.setDisplayedChild(0);
                stopTimer();
            }
        });
        mLap = (TextView) root.findViewById(R.id.lap);
        mLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lapTimer();
                mTimerListAdapter.addData(new TimerListData(mTimerListAdapter.getData().size() + 1, mHour, mMinute, mSecond));
                mTimerListAdapter.notifyDataSetChanged();
            }
        });
        mReset.setVisibility(View.GONE);

        mTimerListView = (ListView) root.findViewById(R.id.lap_listview);
        mTimerListAdapter = new TimerListAdapter(getActivity());
        mTimerListView.setAdapter(mTimerListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (DEBUG) {
            Log.d(TAG, "onCreateView");
        }
        mRoot = inflater.inflate(R.layout.timer_list_fragment, null);
        initComponents(mRoot);
        return mRoot;
    }
}

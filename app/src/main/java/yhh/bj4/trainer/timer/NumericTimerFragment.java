package yhh.bj4.trainer.timer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import yhh.bj4.trainer.R;

/**
 * Created by User on 2016/4/17.
 */
public class NumericTimerFragment extends Fragment {
    private View mRoot;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private TextView mHourView, mMinuteView, mSecondView;
    private int mHour, mMinute, mSecond;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                final Activity activity = getActivity();
                if (mRoot == null) return;
                if (activity == null) return;
                ++mSecond;
                if (mSecond == 60) {
                    mSecond = 0;
                    ++mMinute;
                }
                if (mMinute == 60) {
                    mMinute = 0;
                    ++mHour;
                }
                if (mHour == 24) {
                    mHour = 0;
                }
                final int hour = mHour;
                final int minute = mMinute;
                final int second = mSecond;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHourView.setText(String.valueOf(hour));
                        mMinuteView.setText(String.valueOf(minute));
                        mSecondView.setText(String.valueOf(second));
                    }
                });

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.numeric_timer_fragment, null);
        mHourView = (TextView) mRoot.findViewById(R.id.hour);
        mMinuteView = (TextView) mRoot.findViewById(R.id.minute);
        mSecondView = (TextView) mRoot.findViewById(R.id.second);
        mTimer.schedule(mTimerTask, 0, 1000);
        return mRoot;
    }
}

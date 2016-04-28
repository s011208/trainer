package yhh.bj4.trainer.job.recycleview.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import yhh.bj4.trainer.R;

/**
 * Created by User on 2016/4/25.
 */
public class ImageJobViewHolder extends BaseViewHolder {
    private ImageView mImage, mStart, mPause, mDone, mBreakTime;
    private TextView mIntensity, mUndoneTimes, mDoneTimes;
    private TextView mRestTimer, mWorkoutTimer;

    public ImageJobViewHolder(View baseView, View contentView) {
        super(baseView, contentView);
        mImage = (ImageView) contentView.findViewById(R.id.image);
        mIntensity = (TextView) contentView.findViewById(R.id.intensity);
        mDoneTimes = (TextView) contentView.findViewById(R.id.done_times);
        mUndoneTimes = (TextView) contentView.findViewById(R.id.undone_times);
        mRestTimer = (TextView) contentView.findViewById(R.id.rest_timer);
        mWorkoutTimer = (TextView) contentView.findViewById(R.id.workout_timer);

        mStart = (ImageView) contentView.findViewById(R.id.start);
        mPause = (ImageView) contentView.findViewById(R.id.pause);
        mDone = (ImageView) contentView.findViewById(R.id.done);
        mBreakTime = (ImageView) contentView.findViewById(R.id.break_time);
    }

    public ImageView getImage() {
        return mImage;
    }

    public TextView getIntensity() {
        return mIntensity;
    }

    public TextView getDoneTimes() {
        return mDoneTimes;
    }

    public TextView getUndoneTimes() {
        return mUndoneTimes;
    }

    public TextView getRestTimer() {
        return mRestTimer;
    }

    public TextView getWorkoutTimer() {
        return mWorkoutTimer;
    }

    public ImageView getStart() {
        return mStart;
    }

    public ImageView getPause() {
        return mPause;
    }

    public ImageView getBreakTime() {
        return mBreakTime;
    }

    public ImageView getDone() {
        return mDone;
    }
}

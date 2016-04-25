package yhh.bj4.trainer;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import yhh.bj4.trainer.calendar.CalendarFragment;
import yhh.bj4.trainer.job.JobFragment;
import yhh.bj4.trainer.timer.TimerListFragment;

/**
 * Created by Yen-Hsun_Huang on 2016/4/19.
 */
public class TrainerPagerAdapter extends FragmentStatePagerAdapter {
    private ViewPagerCallbackFragment[] mFragments = new ViewPagerCallbackFragment[3];

    public TrainerPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments[0] = new JobFragment();
        mFragments[1] = new CalendarFragment();
        mFragments[2] = new TimerListFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public ViewPagerCallbackFragment getItem(int position) {
        return mFragments[position];
    }
}

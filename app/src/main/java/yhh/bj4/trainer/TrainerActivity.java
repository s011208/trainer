package yhh.bj4.trainer;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import yhh.bj4.trainer.calendar.CalendarFragment;
import yhh.bj4.trainer.timer.TimerListFragment;

public class TrainerActivity extends TransparentActivity {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "QQQQ";

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private PagerTitleStrip mPagerTitleStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction().replace(R.id.container, new CalendarFragment()).commitAllowingStateLoss();
//        }
    }

    private void initComponents() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
    }

    private static class TrainerPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}

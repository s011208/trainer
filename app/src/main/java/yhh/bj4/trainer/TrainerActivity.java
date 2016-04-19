package yhh.bj4.trainer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import yhh.bj4.trainer.timer.TimerListFragment;

public class TrainerActivity extends TransparentActivity {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "QQQQ";

    private ViewPager mViewPager;
    private TrainerPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        initComponents();
        initFragments();
    }

    private void initFragments() {
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction().replace(R.id.container, new CalendarFragment()).commitAllowingStateLoss();
//        }
    }

    private void initComponents() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TrainerPagerAdapter(TrainerActivity.this);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private static class TrainerPagerAdapter extends PagerAdapter {
        private Activity mActivity;

        public TrainerPagerAdapter(Activity activity) {
            mActivity = activity;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "hi" + position;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout root = new FrameLayout(mActivity);
            root.setId(position + 1);
            mActivity.getFragmentManager().beginTransaction().replace(root.getId(), new TimerListFragment()).commitAllowingStateLoss();
            container.addView(root);
            return root;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}

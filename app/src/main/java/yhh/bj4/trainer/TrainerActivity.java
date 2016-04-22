package yhh.bj4.trainer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class TrainerActivity extends TransparentActivity {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "QQQQ";

    private ViewPager mViewPager;
    private TrainerPagerAdapter mPagerAdapter;
    private TrainerActivityTabLayout mTopTabLayout;

    private ImageView mSchedule, mCalendar, mTimer;

    private int mCurrentPagerItem;

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
        mTopTabLayout = (TrainerActivityTabLayout) findViewById(R.id.top_tab_container);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTopTabLayout.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (mCurrentPagerItem == position) return;
                mPagerAdapter.getItem(position).onVisible();
                mPagerAdapter.getItem(mCurrentPagerItem).onVisible();
                mCurrentPagerItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mTopTabLayout.onPageScrollStateChanged(state);
            }
        });
        mPagerAdapter = new TrainerPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mCurrentPagerItem = mViewPager.getCurrentItem();
        mSchedule = (ImageView) findViewById(R.id.pager_schedule);
        mSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });
        mCalendar = (ImageView) findViewById(R.id.pager_calendar);
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
            }
        });
        mTimer = (ImageView) findViewById(R.id.pager_timer);
        mTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2, true);
            }
        });
    }
}

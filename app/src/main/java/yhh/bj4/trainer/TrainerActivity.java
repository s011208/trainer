package yhh.bj4.trainer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class TrainerActivity extends TransparentActivity {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "QQQQ";

    private ViewPager mViewPager;
    private TrainerPagerAdapter mPagerAdapter;
    private TrainerActivityTabLayout mTopTabLayout;

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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("QQQQ", "position: " + position + ", positionOffset: " + positionOffset + ", positionOffsetPixels: " + positionOffsetPixels);
                mTopTabLayout.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerAdapter = new TrainerPagerAdapter(TrainerActivity.this);
        mViewPager.setAdapter(mPagerAdapter);
        mTopTabLayout = (TrainerActivityTabLayout) findViewById(R.id.top_tab_container);
    }
}

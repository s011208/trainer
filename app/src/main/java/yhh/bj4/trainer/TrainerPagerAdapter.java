package yhh.bj4.trainer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import yhh.bj4.trainer.timer.TimerListFragment;

/**
 * Created by Yen-Hsun_Huang on 2016/4/19.
 */
public class TrainerPagerAdapter extends PagerAdapter {
    private Activity mActivity;

    public TrainerPagerAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "hi" + position;
    }

    public int getPageTitleDrawableResource(int position) {
        return R.mipmap.ic_launcher;
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

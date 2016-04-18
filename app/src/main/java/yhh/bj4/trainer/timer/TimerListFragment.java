package yhh.bj4.trainer.timer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yhh.bj4.trainer.R;

/**
 * Created by Yen-Hsun_Huang on 2016/4/18.
 */
public class TimerListFragment extends Fragment {
    private View mRoot;
    private TextView mTotalHourView, mTotalMinuteView, mTotalSecondView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.timer_list_fragment, null);
        return mRoot;
    }
}

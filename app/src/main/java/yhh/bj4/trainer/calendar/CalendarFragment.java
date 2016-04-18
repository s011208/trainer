package yhh.bj4.trainer.calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.Utilities;

/**
 * Created by User on 2016/4/18.
 */
public class CalendarFragment extends Fragment {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "CalendarFragment";

    private View mRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.calendar_fragment, null);
        return mRoot;
    }
}

package yhh.bj4.trainer;

import android.os.Bundle;

import yhh.bj4.trainer.timer.TimerListFragment;

public class TrainerActivity extends TransparentActivity {
    private static final boolean DEBUG = true;
    private static final String TAG = "QQQQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.container, new TimerListFragment()).commitAllowingStateLoss();
        }
    }
}

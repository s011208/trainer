package yhh.bj4.trainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

import yhh.bj4.trainer.firebase.FirebaseUtilities;
import yhh.bj4.trainer.timer.NumericTimerFragment;
import yhh.bj4.trainer.timer.TimerListFragment;

public class TrainerActivity extends TransparentActivity {
    private static final boolean DEBUG = true;
    private static final String TAG = "QQQQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        getFragmentManager().beginTransaction().replace(R.id.container, new TimerListFragment()).commitAllowingStateLoss();
    }
}

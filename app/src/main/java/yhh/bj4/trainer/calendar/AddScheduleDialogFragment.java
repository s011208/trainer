package yhh.bj4.trainer.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import yhh.bj4.trainer.Utilities;

/**
 * Created by Yen-Hsun_Huang on 2016/4/22.
 */
public class AddScheduleDialogFragment extends DialogFragment {
    private static final String TAG = "AddScheduleDialogFragment";
    private static final boolean DEBUG = Utilities.DEBUG;
    public static final String KEY_YEAR = "k_y";
    public static final String KEY_MONTH = "k_m";
    public static final String KEY_DAY_OF_MONTH = "k_d_m";

    private int mScheduleYear, mScheduleMonth, mScheduleDayOfMonth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mScheduleYear = arguments.getInt(KEY_YEAR);
            mScheduleMonth = arguments.getInt(KEY_MONTH);
            mScheduleDayOfMonth = arguments.getInt(KEY_DAY_OF_MONTH);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle("123").setMessage("a-lo-ha" + mScheduleYear + ", "
                + mScheduleMonth + ", " + mScheduleDayOfMonth).create();
    }
}

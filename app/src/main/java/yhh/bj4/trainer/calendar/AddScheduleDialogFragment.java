package yhh.bj4.trainer.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import yhh.bj4.trainer.R;
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
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new AlertDialog.Builder(getActivity()).setCustomTitle(getCustomTitleView(inflater)).setMessage("a-lo-ha" + mScheduleYear + ", "
                + mScheduleMonth + ", " + mScheduleDayOfMonth).create();
    }

    private View getCustomTitleView(LayoutInflater inflater) {
        final View title = inflater.inflate(R.layout.dialog_add_schedule_title, null);
        final TextView currentDate = (TextView) title.findViewById(R.id.current_date);
        final ImageView moveToPrevious = (ImageView) title.findViewById(R.id.previous);
        moveToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDate(currentDate, -1);
            }
        });
        final ImageView moveToNext = (ImageView) title.findViewById(R.id.next);
        moveToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextDate(currentDate, 1);
            }
        });
        setTextDate(currentDate, 0);
        return title;
    }

    private void setTextDate(TextView text, int add) {
        Calendar c = Calendar.getInstance();
        c.set(mScheduleYear, mScheduleMonth, mScheduleDayOfMonth);
        if (add != 0) {
            c.add(Calendar.DATE, add);
        }
        mScheduleYear = c.get(Calendar.YEAR);
        mScheduleMonth = c.get(Calendar.MONTH);
        mScheduleDayOfMonth = c.get(Calendar.DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy.MM.dd");
        text.setText(dateFormat.format(c.getTime()));
    }
}

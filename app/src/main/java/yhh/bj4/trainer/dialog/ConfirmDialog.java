package yhh.bj4.trainer.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import yhh.bj4.trainer.Utilities;

/**
 * Created by yenhsunhuang on 2016/4/25.
 */
public class ConfirmDialog extends DialogFragment {
    private static final boolean DEBUG = Utilities.DEBUG;

    private static final String TAG = "ConfirmDialog";

    public interface Callback {
        void onConfirm();

        void onCancel();
    }

    public static final String INTENT_ON_CONFIRM = "intent_on_confirm";
    public static final String KEY_TITLE = "m_title";
    public static final String KEY_MESSAGE = "m_msg";
    private Callback mCallback;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getActivity() instanceof Callback) {
            mCallback = (Callback) getActivity();
        }
        Bundle argument = getArguments();
        if (argument == null) {
            throw new RuntimeException("failed to create confirm dialog");
        }
        final String title = argument.getString(KEY_TITLE);
        final String msg = argument.getString(KEY_MESSAGE);

        return new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(msg).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DEBUG) Log.v(TAG, "Negative");
                if (mCallback != null) {
                    mCallback.onCancel();
                }
                if (getTargetFragment() != null) {
                    Intent intent = getActivity().getIntent();
                    intent.putExtra(INTENT_ON_CONFIRM, false);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                }
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DEBUG) Log.v(TAG, "Positive");
                if (mCallback != null) {
                    mCallback.onConfirm();
                }
                if (getTargetFragment() != null) {
                    Intent intent = getActivity().getIntent();
                    intent.putExtra(INTENT_ON_CONFIRM, true);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                }
            }
        }).create();
    }
}

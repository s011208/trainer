package yhh.bj4.trainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

import yhh.bj4.trainer.firebase.FirebaseUtilities;

public class TrainerActivity extends AppCompatActivity {
    private static final boolean DEBUG = true;
    private static final String TAG = "QQQQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        FirebaseUtilities.getFirebaseRoot().child("message").setValue("Do you have data? You'll love Firebase.", new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    if (DEBUG) {
                        Log.d(TAG, "setValue successfully");
                    }
                } else {
                    if (DEBUG) {
                        Log.d(TAG, "failed to setValue, message: " + firebaseError.getMessage());
                    }
                }
            }
        });
    }
}

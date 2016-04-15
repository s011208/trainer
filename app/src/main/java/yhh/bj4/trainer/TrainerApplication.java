package yhh.bj4.trainer;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by yenhsunhuang on 2016/4/16.
 */
public class TrainerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

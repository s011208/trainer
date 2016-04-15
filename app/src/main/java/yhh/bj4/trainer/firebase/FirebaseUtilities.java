package yhh.bj4.trainer.firebase;

import com.firebase.client.Firebase;

/**
 * Created by yenhsunhuang on 2016/4/16.
 */
public class FirebaseUtilities {
    private static final String FIREBASE_URL = "https://blazing-inferno-5039.firebaseio.com/";

    public static Firebase getFirebaseRoot() {
        return new Firebase(FIREBASE_URL);
    }


}

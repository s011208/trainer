package yhh.bj4.trainer;

import android.database.Cursor;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yen-Hsun_Huang on 2016/4/22.
 */
public class TrainerSettings {
    public static final String PROVIDER_AUTHORITY = "yhh.bj4.trainer.TrainerProvider";

    public static final String PARAMETER_NOTIFY = "notify";

    public static class BaseSettings {
        /**
         * V1
         */
        public static final String COLUMN_ID = "_id";
    }

    public static class CalendarSettings extends BaseSettings {
        public static final Uri getUri(boolean notify) {
            return Uri.parse("content://" + PROVIDER_AUTHORITY + "/" + MATCHER_PATTERN + "?" + PARAMETER_NOTIFY + "=" + notify);
        }

        /**
         * V1
         */
        public static final String TABLE_CALENDAR_SETTINGS = "calendar_settings";

        /**
         * V1
         */
        public static final String MATCHER_PATTERN = TABLE_CALENDAR_SETTINGS;
        /**
         * V1
         */
        public static final String COLUMN_YEAR = "_y";
        /**
         * V1
         */
        public static final String COLUMN_MONTH = "_m";
        /**
         * V1
         */
        public static final String COLUMN_DAY_OF_MONTH = "_d";
        /**
         * V1
         */
        public static final String COLUMN_TRAINING_ID = "train_id";
    }

    public static class TrainingDataSettings extends BaseSettings {
        public static final Uri getUri(boolean notify) {
            return Uri.parse("content://" + PROVIDER_AUTHORITY + "/" + MATCHER_PATTERN + "?" + PARAMETER_NOTIFY + "=" + notify);
        }

        /**
         * V1
         */
        public static final String TABLE_TRAINING_DATA_SETTINGS = "training_data_settings";

        public static final String MATCHER_PATTERN = TABLE_TRAINING_DATA_SETTINGS;
        /**
         * V1
         */
        public static final String COLUMN_TRAINING_NAME = "t_name";
        /**
         * V1
         */
        public static final String COLUMN_TRAINING_TIMES = "t_times";
        /**
         * V1
         */
        public static final String COLUMN_TRAINING_TIMES_UNIT = "t_times_unit";
        /**
         * V1
         */
        public static final String COLUMN_TRAINING_STRENGTH = "t_strength";
        /**
         * V1
         */
        public static final String COLUMN_TRAINING_STRENGTH_UNIT = "t_strength_unit";
        /**
         * V2
         */
        public static final String COLUMN_TRAINING_ADD_TIME = "t_add_time";
        /**
         * V2
         */
        public static final String COLUMN_TRAINING_LOCATION = "t_location";


        private long mId;
        private String mTrainingName;
        private String mTrainingTimes;
        private String mTrainingTimesUnit;
        private String mTrainingStrength;
        private String mTrainingStrengthUnit;
        private long mAddTime;
        private String mLocation;

        public TrainingDataSettings(long id, String name, String time, String timeUnit, String strength, String strengthUnit, long addTime, String location) {
            mId = id;
            mTrainingName = name;
            mTrainingStrength = strength;
            mTrainingStrengthUnit = strengthUnit;
            mTrainingTimes = time;
            mTrainingTimesUnit = timeUnit;
            mAddTime = addTime;
            mLocation = location;
        }

        public TrainingDataSettings(String fromJson) {
            try {
                JSONObject json = new JSONObject(fromJson);
                mId = json.getLong(COLUMN_ID);
                mTrainingName = json.getString(COLUMN_TRAINING_NAME);
                mTrainingStrength = json.getString(COLUMN_TRAINING_STRENGTH);
                mTrainingStrengthUnit = json.getString(COLUMN_TRAINING_STRENGTH_UNIT);
                mTrainingTimes = json.getString(COLUMN_TRAINING_TIMES);
                mTrainingTimesUnit = json.getString(COLUMN_TRAINING_TIMES_UNIT);
                if (json.has(COLUMN_TRAINING_ADD_TIME))
                    mAddTime = json.getLong(COLUMN_TRAINING_ADD_TIME);
                if (json.has(COLUMN_TRAINING_LOCATION))
                    mLocation = json.getString(COLUMN_TRAINING_LOCATION);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private long getAddTime() {
            return mAddTime;
        }

        private String getLocation() {
            return mLocation;
        }

        public long getId() {
            return mId;
        }

        public String getTrainingStrengthUnit() {
            return mTrainingStrengthUnit;
        }

        public String getTrainingStrength() {
            return mTrainingStrength;
        }

        public String getTrainingTimeUnit() {
            return mTrainingTimesUnit;
        }

        public String getTrainingTime() {
            return mTrainingTimes;
        }

        public String getTrainingName() {
            return mTrainingName;
        }

        public JSONObject toJson() {
            JSONObject json = new JSONObject();
            try {
                json.put(COLUMN_ID, getId());
                json.put(COLUMN_TRAINING_NAME, getTrainingName());
                json.put(COLUMN_TRAINING_ADD_TIME, getAddTime());
                json.put(COLUMN_TRAINING_LOCATION, getLocation());
                json.put(COLUMN_TRAINING_STRENGTH, getTrainingStrength());
                json.put(COLUMN_TRAINING_STRENGTH_UNIT, getTrainingStrengthUnit());
                json.put(COLUMN_TRAINING_TIMES, getTrainingTime());
                json.put(COLUMN_TRAINING_TIMES_UNIT, getTrainingTimeUnit());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        public String toString() {
            return toJson().toString();
        }

        public static ArrayList<TrainingDataSettings> from(Cursor cursor) {
            ArrayList<TrainingDataSettings> rtn = new ArrayList<>();
            if (cursor == null) return rtn;
            try {
                final int indexOfId = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_ID);
                final int indexOfTrainingName = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_NAME);
                final int indexOfTrainingStrength = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH);
                final int indexOfTrainingStrengthUnit = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_STRENGTH_UNIT);
                final int indexOfTrainingTime = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES);
                final int indexOfTrainingTimeUnit = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_TIMES_UNIT);
                final int indexOfTrainingAddTime = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_ADD_TIME);
                final int indexOfTrainingLocation = cursor.getColumnIndex(TrainerSettings.TrainingDataSettings.COLUMN_TRAINING_LOCATION);
                while (cursor.moveToNext()) {
                    rtn.add(new TrainerSettings.TrainingDataSettings(
                            cursor.getLong(indexOfId),
                            cursor.getString(indexOfTrainingName),
                            cursor.getString(indexOfTrainingTime),
                            cursor.getString(indexOfTrainingTimeUnit),
                            cursor.getString(indexOfTrainingStrength),
                            cursor.getString(indexOfTrainingStrengthUnit),
                            cursor.getLong(indexOfTrainingAddTime),
                            cursor.getString(indexOfTrainingLocation)));
                }
            } finally {
                cursor.close();
            }
            return rtn;
        }
    }
}

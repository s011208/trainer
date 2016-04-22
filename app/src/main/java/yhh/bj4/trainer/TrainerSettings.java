package yhh.bj4.trainer;

/**
 * Created by Yen-Hsun_Huang on 2016/4/22.
 */
public class TrainerSettings {
    public static final String PROVIDER_AUTHORITY = "yhh.bj4.trainer.TrainerProvider";

    public static final String PARAMETER_NOTIFY = "notify";

    public static class BaseSettings {
        public static final String COLUMN_ID = "_id";
    }

    public static class CalendarSettings extends BaseSettings {
        public static final String TABLE_CALENDAR_SETTINGS = "calendar_settings";

        public static final String MATCHER_PATTERN = TABLE_CALENDAR_SETTINGS;

        public static final String COLUMN_YEAR = "_y";
        public static final String COLUMN_MONTH = "_m";
        public static final String COLUMN_DAY_OF_MONTH = "_d";
        public static final String COLUMN_TRAINING_ID = "train_id";
    }

    public static class TrainingDataSettings extends BaseSettings {
        public static final String TABLE_TRAINING_DATA_SETTINGS = "training_data_settings";

        public static final String MATCHER_PATTERN = TABLE_TRAINING_DATA_SETTINGS;

        public static final String COLUMN_TRAINING_NAME = "t_name";
        public static final String COLUMN_TRAINING_TIMES = "t_times";
        public static final String COLUMN_TRAINING_TIMES_UNIT = "t_times_unit";
        public static final String COLUMN_TRAINING_STRENGTH = "t_strength";
        public static final String COLUMN_TRAINING_STRENGTH_UNIT = "t_strength_unit";
    }
}

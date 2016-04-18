package yhh.bj4.trainer.timer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yen-Hsun_Huang on 2016/4/18.
 */
public class TimerListData {
    private static final String LAP = "lap";
    private static final String STOP_HOUR = "sh";
    private static final String STOP_MINUTE = "sm";
    private static final String STOP_SECOND = "ss";
    private static final String LAP_HOUR = "lh";
    private static final String LAP_MINUTE = "lm";
    private static final String LAP_SECOND = "ls";

    private int mLap;
    private int mStopHour, mStopMinute, mStopSecond;
    private int mLapHour, mLapMinute, mLapSecond;

    public int getLap() {
        return mLap;
    }

    public int getStopHour() {
        return mStopHour;
    }

    public int getStopMinute() {
        return mStopMinute;
    }

    public int getStopSecond() {
        return mStopSecond;
    }

    public int getLapHour() {
        return mLapHour;
    }

    public int getLapMinute() {
        return mLapMinute;
    }

    public int getLapSecond() {
        return mLapSecond;
    }

    public void setLapHour(int h) {
        mLapHour = h;
    }

    public void setLapMinute(int m) {
        mLapMinute = m;
    }

    public void setLapSecond(int s) {
        mLapSecond = s;
    }

    public void setStopHour(int h) {
        mStopHour = h;
    }

    public void setStopMinute(int m) {
        mStopMinute = m;
    }

    public void setStopSecond(int s) {
        mStopSecond = s;
    }

    public TimerListData(int lap, int stopHour, int stopMinute, int stopSecond) {
        mLap = lap;
        mStopHour = stopHour;
        mStopMinute = stopMinute;
        mStopSecond = stopSecond;
        mLapHour = mLapMinute = mLapSecond = 0;
    }

    public TimerListData(String rawJson) {
        try {
            JSONObject json = new JSONObject(rawJson);
            mLap = json.getInt(LAP);
            mStopHour = json.getInt(STOP_HOUR);
            mStopMinute = json.getInt(STOP_MINUTE);
            mStopSecond = json.getInt(STOP_SECOND);
            mLapHour = json.getInt(LAP_HOUR);
            mLapMinute = json.getInt(LAP_MINUTE);
            mLapSecond = json.getInt(LAP_SECOND);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(LAP, mLap);
            json.put(STOP_HOUR, mStopHour);
            json.put(STOP_MINUTE, mStopMinute);
            json.put(STOP_SECOND, mStopSecond);
            json.put(LAP_HOUR, mLapHour);
            json.put(LAP_MINUTE, mLapMinute);
            json.put(LAP_SECOND, mLapSecond);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}

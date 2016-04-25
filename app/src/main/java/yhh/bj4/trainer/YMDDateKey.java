package yhh.bj4.trainer;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yenhsunhuang on 2016/4/25.
 */
public class YMDDateKey {
    private int mY, mM, mD;

    private Calendar mC;

    private Date mDate;

    public YMDDateKey(Calendar c) {
        this(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    public YMDDateKey(int y, int m, int d) {
        mY = y;
        mM = m;
        mD = d;
        Calendar c = Calendar.getInstance();
        c.set(getYear(), getMonth(), getDay());
        mDate = c.getTime();
    }

    public int getYear() {
        return mY;
    }

    public int getMonth() {
        return mM;
    }

    public int getDay() {
        return mD;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = (getYear() / 11) * getMonth() * getDay() + 15 * 37;
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof YMDDateKey)) return false;
        return getYear() == ((YMDDateKey) object).getYear() &&
                getMonth() == ((YMDDateKey) object).getMonth() &&
                getDay() == ((YMDDateKey) object).getDay();
    }

    public Date getDate() {
        return mDate;
    }

    @Override
    public String toString() {
        return "getYear(): " + getYear() + ", getMonth(): " + getMonth() + ", getDay(): " + getDay();
    }
}

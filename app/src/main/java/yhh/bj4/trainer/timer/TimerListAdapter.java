package yhh.bj4.trainer.timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import yhh.bj4.trainer.R;

/**
 * Created by Yen-Hsun_Huang on 2016/4/18.
 */
public class TimerListAdapter extends BaseAdapter {
    private final ArrayList<TimerListData> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public TimerListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setCurrentTimer(int hour, int minute, int second) {
        if (mData.isEmpty()) return;
        mData.get(0).setStopHour(hour);
        mData.get(0).setStopMinute(minute);
        mData.get(0).setStopSecond(second);
    }

    public void setLapTimer(int h, int m, int s) {
        if (mData.isEmpty()) return;
        mData.get(0).setLapHour(h);
        mData.get(0).setLapMinute(m);
        mData.get(0).setLapSecond(s);
    }

    public void setData(ArrayList<TimerListData> data) {
        mData.clear();
        mData.addAll(data);
    }

    public void addData(TimerListData data) {
        mData.add(0, data);
    }

    public ArrayList<TimerListData> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public TimerListData getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.timer_list_row, null);
            holder.mLap = (TextView) convertView.findViewById(R.id.lap);
            holder.mLapHour = (TextView) convertView.findViewById(R.id.lap_hour);
            holder.mLapMinute = (TextView) convertView.findViewById(R.id.lap_minute);
            holder.mLapSecond = (TextView) convertView.findViewById(R.id.lap_second);
            holder.mStopHour = (TextView) convertView.findViewById(R.id.stop_hour);
            holder.mStopMinute = (TextView) convertView.findViewById(R.id.stop_minute);
            holder.mStopSecond = (TextView) convertView.findViewById(R.id.stop_second);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TimerListData data = getItem(position);
        holder.mLap.setText(mContext.getResources().getString(R.string.timer_list_adapter_lap, getTimeFormat(data.getLap())));
        holder.mLapHour.setText(getTimeFormat(data.getLapHour()));
        holder.mLapMinute.setText(getTimeFormat(data.getLapMinute()));
        holder.mLapSecond.setText(getTimeFormat(data.getLapSecond()));
        holder.mStopHour.setText(getTimeFormat(data.getStopHour()));
        holder.mStopMinute.setText(getTimeFormat(data.getStopMinute()));
        holder.mStopSecond.setText(getTimeFormat(data.getStopSecond()));
        return convertView;
    }

    private static String getTimeFormat(int time) {
        if (time < 10) {
            return "0" + String.valueOf(time);
        }
        return String.valueOf(time);
    }

    private static class ViewHolder {
        TextView mLap, mLapHour, mLapMinute, mLapSecond, mStopHour, mStopMinute, mStopSecond;
    }
}

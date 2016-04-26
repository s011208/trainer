package yhh.bj4.trainer.job.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.TrainerSettings;
import yhh.bj4.trainer.Utilities;
import yhh.bj4.trainer.job.recycleview.holder.BaseViewHolder;
import yhh.bj4.trainer.job.recycleview.holder.JobViewHolder;

/**
 * Created by User on 2016/4/25.
 */
public class JobRecycleAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final boolean DEBUG = Utilities.DEBUG;
    private static final String TAG = "JobRecycleAdapter";

    private static final int VIEW_TYPE_JOB = 1;

    private Context mContext;
    private ArrayList<TrainerSettings.TrainingDataSettings> mData = new ArrayList<>();

    public JobRecycleAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        switch (viewType) {
            case VIEW_TYPE_JOB:
                return new JobViewHolder(inflater.inflate(R.layout.base_view_holder, null), inflater.inflate(R.layout.job_view_holder, null));
        }
        return null;
    }

    private void bindJobViewHolder(BaseViewHolder holder, int position) {
        JobViewHolder jobViewHolder = (JobViewHolder) holder;
        jobViewHolder.getBaseTitle().setText("12333333");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_JOB:
                bindJobViewHolder(holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_JOB;
    }
}

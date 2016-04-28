package yhh.bj4.trainer.job;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yhh.bj4.trainer.R;
import yhh.bj4.trainer.Utilities;
import yhh.bj4.trainer.ViewPagerCallbackFragment;
import yhh.bj4.trainer.job.recycleview.JobRecycleAdapter;

/**
 * Created by yenhsunhuang on 2016/4/25.
 */
public class JobFragment extends ViewPagerCallbackFragment {
    private static final String TAG = "JobFragment";
    private static final boolean DEBUG = Utilities.DEBUG;

    private View mRoot;
    private RecyclerView mJobRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.job_fragment, null);
        initComponents();
        return mRoot;
    }

    private void initComponents() {
        mJobRecycleView = (RecyclerView) mRoot.findViewById(R.id.jobs_recycle_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mJobRecycleView.setLayoutManager(llm);
        mJobRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int itemSet = getActivity().getResources().getDimensionPixelSize(R.dimen.job_fragment_recycle_view_inset);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(itemSet, itemSet, itemSet, itemSet);
            }
        });
        mJobRecycleView.setAdapter(new JobRecycleAdapter(getActivity()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInVisible() {

    }
}

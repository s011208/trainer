package yhh.bj4.trainer.job.recycleview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import yhh.bj4.trainer.R;

/**
 * Created by User on 2016/4/25.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private TextView mBaseTitle;
    private ImageView mFunctionButton;
    private FrameLayout mContentContainer;
    private View mBaseView;

    public BaseViewHolder(View baseView, View contentView) {
        super(baseView);
        mBaseView = baseView.findViewById(R.id.base_view);
        mBaseTitle = (TextView) baseView.findViewById(R.id.base_title);
        mFunctionButton = (ImageView) baseView.findViewById(R.id.base_function_button);
        mContentContainer = (FrameLayout) baseView.findViewById(R.id.base_content_container);
        mContentContainer.addView(contentView);
    }

    public TextView getBaseTitle() {
        return mBaseTitle;
    }

    public ImageView getFunctionButton() {
        return mFunctionButton;
    }

    public FrameLayout getContentContainer() {
        return mContentContainer;
    }

    public View getBaseView() {
        return mBaseView;
    }
}

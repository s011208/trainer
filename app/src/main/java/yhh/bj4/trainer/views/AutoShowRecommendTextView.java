package yhh.bj4.trainer.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by User on 2016/4/23.
 */
public class AutoShowRecommendTextView extends AutoCompleteTextView {
    public AutoShowRecommendTextView(Context context) {
        super(context);
    }

    public AutoShowRecommendTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoShowRecommendTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getAdapter() != null) {
            performFiltering(getText(), 0);
            showDropDown();
        }
    }
}

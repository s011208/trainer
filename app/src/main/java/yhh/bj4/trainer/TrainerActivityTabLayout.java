package yhh.bj4.trainer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Yen-Hsun_Huang on 2016/4/19.
 */
public class TrainerActivityTabLayout extends LinearLayout {
    private final Paint mPagePaint = new Paint();
    private int mCurrentPosition = 0;

    public TrainerActivityTabLayout(Context context) {
        this(context, null);
    }

    public TrainerActivityTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainerActivityTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPagePaint.setColor(Color.RED);
        mPagePaint.setStrokeWidth(20);
        mPagePaint.setStyle(Paint.Style.FILL);
        mPagePaint.setAntiAlias(true);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPosition = position;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getChildCount() == 0) return;
        canvas.drawLine(0, 0, getChildAt(mCurrentPosition).getWidth(), 0, mPagePaint);
    }
}

package yhh.bj4.trainer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Yen-Hsun_Huang on 2016/4/19.
 */
public class TrainerActivityTabLayout extends LinearLayout {
    private static final int POST_TIME = 30;

    private final Paint mPagePaint = new Paint();
    private final Paint mFocusedPaint = new Paint();
    private int mCurrentPosition = 0;
    private float mCurrentPositionOffset = 0;

    private int mPostPosition = 0;
    private float mPostPositionOffset = 0;
    private final int mTabLayoutPaddingBottom;

    private static final float THRESHOLD_OFFSET = 0.5f;
    private boolean mScrollStarted, mCheckDirection;
    private boolean mScrollToLeft = false;

    private Handler mHandler = new Handler();

    public TrainerActivityTabLayout(Context context) {
        this(context, null);
    }

    public TrainerActivityTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrainerActivityTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTabLayoutPaddingBottom = context.getResources().getDimensionPixelSize(R.dimen.trainer_activity_top_tab_container_padding_bottom);
        mPagePaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        mPagePaint.setStrokeWidth(mTabLayoutPaddingBottom);
        mPagePaint.setStyle(Paint.Style.FILL);
        mPagePaint.setAntiAlias(true);

        mFocusedPaint.setColor(Color.argb(30, 150, 150, 150));
        mFocusedPaint.setAntiAlias(true);
    }

    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        if (mCheckDirection) {
            if (THRESHOLD_OFFSET > positionOffset) {
                mScrollToLeft = true;
            } else {
                mScrollToLeft = false;
            }
            mPostPosition = position;
            mPostPositionOffset = positionOffset;
            mCheckDirection = false;
        }
        mCurrentPosition = position;
        mCurrentPositionOffset = positionOffset;
        invalidate();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPostPosition = position;
                mPostPositionOffset = positionOffset;
                invalidate();
            }
        }, POST_TIME);
    }

    public void onPageScrollStateChanged(int state) {
        if (!mScrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
            mScrollStarted = true;
            mCheckDirection = true;
        } else {
            mScrollStarted = false;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (getChildCount() == 0) return;
        int width = getWidth() / getChildCount();

        int postStartX = getWidth() / getChildCount() * mPostPosition + (int) (width * mPostPositionOffset);
        int postFinalX = postStartX + width;
        int currentStartX = getWidth() / getChildCount() * mCurrentPosition + (int) (width * mCurrentPositionOffset);
        int currentFinalX = currentStartX + width;
        canvas.drawRect(postStartX, 0, postFinalX, getHeight(), mFocusedPaint);
        canvas.drawLine(currentStartX, getHeight() - mTabLayoutPaddingBottom, currentFinalX, getHeight() - mTabLayoutPaddingBottom, mPagePaint);
    }
}

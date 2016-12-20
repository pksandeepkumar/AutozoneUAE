package texus.autozoneuaenew.controls;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by sandeep on 31/5/16.
 */
public class SquareViewPager extends ViewPager {
    public SquareViewPager(Context context) {
        super(context);
    }
    public SquareViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//    public SquareViewPager(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = width;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}


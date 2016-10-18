package texus.autozoneuae.controls;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import texus.autozoneuae.utility.LOG;

/**
 * Created by sandeep on 29/09/16.
 */
public class SquareLayout  extends RelativeLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec );

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        LOG.log("XXXX","parentWidth:" + parentWidth);
        LOG.log("XXXX","parentHeight:" + parentHeight);

        this.setMeasuredDimension(
                parentWidth / 2, parentHeight);

        float floatHeight = parentWidth;
        floatHeight = (floatHeight * 60)/100;

        int height = (int)floatHeight;
        LOG.log("XXXX","XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        LOG.log("XXXX","Height:" + height);
        LOG.log("XXXX","widthMeasureSpec:" + widthMeasureSpec);
        // Set a square layout.
        this.setMeasuredDimension(
                parentWidth, height);
//        super.onMeasure(widthMeasureSpec, height );
    }
}

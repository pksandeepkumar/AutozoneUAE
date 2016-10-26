package texus.autozoneuae.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import texus.autozoneuae.ApplicationClass;

/**
 * Created by sandeep on 10/26/2016.
 */

public class AutozoneTextView  extends TextView {



    public AutozoneTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AutozoneTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutozoneTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(ApplicationClass.getInstance().appFont);
    }
}

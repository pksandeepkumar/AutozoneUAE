package texus.autozoneuae.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import texus.autozoneuae.ApplicationClass;

/**
 * Created by sandeep on 10/26/2016.
 */

public class AutozoneButton extends Button {



    public AutozoneButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AutozoneButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutozoneButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(ApplicationClass.getInstance().appFont);
    }
}

package texus.autozoneuaenew.controls;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import texus.autozoneuaenew.R;

/**
 * Created by sandeep on 22/10/16.
 */

public class ContactBlanRow extends TextView {

    Context context;

    public ContactBlanRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    public ContactBlanRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContactBlanRow(Context context) {
        super(context);
        init(context, null);

    }

    private void init(Context context, AttributeSet attrs) {

        if (Build.VERSION.SDK_INT < 23) {
            setTextAppearance( context , R.style.contact_us_main_text);
        } else {
            setTextAppearance(R.style.contact_us_main_text);
        }
        setText("");

    }



}

package texus.autozoneuae.controls;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;

import texus.autozoneuae.R;

/**
 * Created by sandeep on 22/10/16.
 */

public class ContactMainText  extends TextView {

    Context context;

    public ContactMainText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    public ContactMainText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContactMainText(Context context, String name, ArrayList<String> colors) {
        super(context);
        init(context, null);

    }

    private void init(Context context, AttributeSet attrs) {

        if (Build.VERSION.SDK_INT < 23) {
            setTextAppearance( context , R.style.contact_us_main_text);
        } else {
            setTextAppearance(R.style.contact_us_main_text);
        }

    }



}

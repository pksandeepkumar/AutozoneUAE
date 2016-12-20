package texus.autozoneuaenew.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import texus.autozoneuaenew.R;

/**
 * Created by sandeep on 22/10/16.
 */

public class ServiceTextLine extends RelativeLayout {

    Context context;

    public ServiceTextLine(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_service_sub_text_line, this);

    }



}

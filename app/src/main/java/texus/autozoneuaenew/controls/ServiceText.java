package texus.autozoneuaenew.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import texus.autozoneuaenew.R;

/**
 * Created by sandeep on 22/10/16.
 */

public class ServiceText extends RelativeLayout {

    Context context;

    public ServiceText(Context context, String text ) {
        super(context);
        init(context, text);
    }

    private void init(Context context, String text) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_service_sub_text, this);

        TextView tvText = (TextView) child.findViewById(R.id.tvText);

        tvText.setText(text);

    }



}

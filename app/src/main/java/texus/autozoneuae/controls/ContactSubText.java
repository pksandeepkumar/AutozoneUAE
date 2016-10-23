package texus.autozoneuae.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import texus.autozoneuae.R;

/**
 * Created by sandeep on 22/10/16.
 */

public class ContactSubText extends RelativeLayout {

    public static final int TYPE_PHONE = 1;
    public static final int TYPE_EMAIL = 2;
    public static final int TYPE_NONE = 3;

    Context context;

    public ContactSubText(Context context, String text, int type ) {
        super(context);
        init(context, text, type);
    }

    private void init(Context context, String text, int type) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_sub_text, this);

        ImageView imIcon = (ImageView) child.findViewById(R.id.imIcon);
        TextView tvText = (TextView) child.findViewById(R.id.tvText);

        if( type == TYPE_NONE ) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imIcon.getLayoutParams();
            lp.width = 0;
            lp.height = 0;
            imIcon.setLayoutParams(lp);
        }

        if(type == TYPE_PHONE) {
            imIcon.setImageResource(R.drawable.ic_watsapp);
        }

        if( type == TYPE_EMAIL) {
            imIcon.setImageResource(R.drawable.ic_email);
        }

        tvText.setText(text);

    }



}

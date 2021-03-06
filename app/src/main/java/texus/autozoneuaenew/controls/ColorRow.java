package texus.autozoneuaenew.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import texus.autozoneuaenew.R;

/**
 * 
 */
public class ColorRow extends RelativeLayout {

	Context context;
    TextView tvText;
    LinearLayout llColorHolder;

	public ColorRow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public ColorRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ColorRow(Context context, String name, ArrayList<String> colors) {
		super(context);
		init(context, null);
        setValues(name, colors);
	}

	private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_color_row, this);

        tvText     = (TextView)    child.findViewById(R.id.tvText);
        llColorHolder     = (LinearLayout)    child.findViewById(R.id.llColorHolder);
	}

    public void setValues( String name, ArrayList<String> colors) {
        tvText.setText(name );
        for(String color: colors) {
            if(color.trim().length() == 0) continue;
            try {
                LinearLayout btnHolder = new LinearLayout(this.getContext());
                btnHolder.setBackgroundColor(Color.WHITE);
                int padding = (int) getResources().getDimension(R.dimen.color_padding);
                btnHolder.setPadding(padding, padding, padding, padding);

                LinearLayout btn = new LinearLayout(this.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        (int) getResources().getDimension(R.dimen.color_size),
                        (int) getResources().getDimension(R.dimen.color_size));
                lp.setMargins(0,0,0,0);
                btn.setBackgroundColor(Color.parseColor(color));
                btnHolder.addView(btn,lp);

                llColorHolder.addView(btnHolder);
            } catch ( Exception e) {
                e.printStackTrace();
            }

        }

    }

}

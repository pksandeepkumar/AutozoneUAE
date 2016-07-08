package texus.autozoneuae.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import texus.autozoneuae.R;

/**
 *
 */
public class HeadderRow extends RelativeLayout {

	Context context;
    TextView tvText;

	public HeadderRow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public HeadderRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public HeadderRow(Context context, String title) {
		super(context);
		init(context, null);
        setTitle(title);
	}

	private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_headder, this);

        tvText     = (TextView)    child.findViewById(R.id.tvText);
	}

    public void setTitle( String title) {
        tvText.setText(title);
    }

}

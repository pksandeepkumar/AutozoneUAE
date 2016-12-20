package texus.autozoneuaenew.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import texus.autozoneuaenew.R;

/**
 * 
 */
public class LineRow extends RelativeLayout {

	Context context;

	public LineRow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public LineRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public LineRow(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_line_row, this);

	}

}

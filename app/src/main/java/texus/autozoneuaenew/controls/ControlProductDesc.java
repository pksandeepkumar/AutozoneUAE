package texus.autozoneuaenew.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import texus.autozoneuaenew.R;

/**
 *
 */
public class ControlProductDesc extends RelativeLayout {

	Context context;
    TextView tvText;

	public ControlProductDesc(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public ControlProductDesc(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ControlProductDesc(Context context, String title) {
		super(context);
		init(context, null);
        setTitle(title);
	}

	private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_product_desc, this);

        tvText     = (TextView)    child.findViewById(R.id.tvText);
	}

    public void setTitle( String title) {
        tvText.setText(title);
    }

}

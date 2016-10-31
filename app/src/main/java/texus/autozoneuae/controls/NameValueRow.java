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
public class NameValueRow extends RelativeLayout {

	Context context;
    TextView tvText;
    TextView tvTextRight;

	public NameValueRow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public NameValueRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public NameValueRow(Context context, String name, String value) {
		super(context);
		init(context, null);
        setValues(name, value);
	}

	private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_name_value, this);

        tvText     = (TextView)    child.findViewById(R.id.tvText);
        tvTextRight     = (TextView)    child.findViewById(R.id.tvTextRight);
	}

    public void setValues( String name, String value) {
        tvText.setText(name + "");
        tvTextRight.setText(value);
    }

}

package texus.autozoneuae.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import texus.autozoneuae.MainActivity;

/**
 * 
 */
public class HalfRelativeLayout extends RelativeLayout {

	Context context;

	public HalfRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public HalfRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public HalfRelativeLayout(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {

		LayoutParams lp = (LayoutParams) getLayoutParams();
		lp.height = (int) MainActivity.width/2;
		setLayoutParams(lp);

	}

}

package texus.autozoneuae.controls;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * 
 */
public class SquirePagerView extends RelativeLayout {

	Context context;
    public ViewPager viewPager = null;


	public SquirePagerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public SquirePagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SquirePagerView(Context context, String name, ArrayList<String> colors) {
		super(context);
		init(context, null);

	}

	private void init(Context context, AttributeSet attrs) {

        viewPager = new ViewPager(context);
        RelativeLayout.LayoutParams rlp = new LayoutParams(200,200);
        addView(viewPager, rlp);
	}



}

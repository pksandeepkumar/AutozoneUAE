package texus.autozoneuae.controls;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import texus.autozoneuae.ApplicationClass;

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
        setGravity(Gravity.CENTER);
        viewPager = new ViewPager(context);
        int width = (int) ((ApplicationClass.getInstance().width ) * 1f);
		int height = (int) ((ApplicationClass.getInstance().width ) * 0.7f);
        RelativeLayout.LayoutParams rlp = new LayoutParams(width,height);
        addView(viewPager, rlp);
	}



}

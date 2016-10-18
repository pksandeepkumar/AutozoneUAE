package texus.autozoneuae.controls;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import texus.autozoneuae.R;

/**
 *
 */
public class BottomTabControl extends RelativeLayout implements View.OnClickListener {

	public static final int TAB_HOME  = 1;
	public static final int TAB_ABOUT_US  = 2;
	public static final int TAB_TAB_SERVICE  = 3;
	public static final int TAB_CONTACT_US  = 4;

	public interface OnTabClickedListener {
		public void onTabClicked(int whichTab);
	}

	OnTabClickedListener listener = null;

	Context context;
	TextView tvTabHome;
	TextView tvAboutUS;
	TextView tvTabService;
	TextView tvTabContactUs;

	public BottomTabControl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public BottomTabControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public BottomTabControl(Context context, String title) {
		super(context);
		init(context, null);

	}

	private void init(Context context, AttributeSet attrs) {
		this.context = context;

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_bottom_tab_control, this);
		tvTabHome = (TextView) child.findViewById(R.id.tvTabHome);
		tvAboutUS = (TextView) child.findViewById(R.id.tvAboutUS);
		tvTabService = (TextView) child.findViewById(R.id.tvTabService);
		tvTabContactUs = (TextView) child.findViewById(R.id.tvTabContactUs);

		tvTabHome.setOnClickListener(this);
		tvAboutUS.setOnClickListener(this);
		tvTabService.setOnClickListener(this);
		tvTabContactUs.setOnClickListener(this);

	}

	public void setOnTabClickedListener( OnTabClickedListener listener ) {
		this.listener = listener;
	}

	private void resetButtonHover() {
		tvTabHome.setTextColor(ContextCompat.getColor(context, R.color.tab_color_text_default));
		tvAboutUS.setTextColor(ContextCompat.getColor(context, R.color.tab_color_text_default));
		tvTabService.setTextColor(ContextCompat.getColor(context, R.color.tab_color_text_default));
		tvTabContactUs.setTextColor(ContextCompat.getColor(context, R.color.tab_color_text_default));
	}

	private void resetButtonHover( TextView tv) {
		tv.setTextColor(ContextCompat.getColor(context, R.color.tab_color_text_hover));
	}

	public void setTab(int tab) {
		switch ( tab) {
			case TAB_HOME:  setTab(tvTabHome);break;
			case TAB_ABOUT_US:  setTab(tvAboutUS);break;
			case TAB_TAB_SERVICE:  setTab(tvTabService);break;
			case TAB_CONTACT_US:  setTab(tvTabContactUs);break;
		}
	}


	@Override
	public void onClick(View view) {

		if(view.getId() == tvTabHome.getId()) {
			raiseCallBack(TAB_HOME);
		}

		if(view.getId() == tvAboutUS.getId()) {
			raiseCallBack(TAB_ABOUT_US);
		}

		if(view.getId() == tvTabService.getId()) {
			raiseCallBack(TAB_TAB_SERVICE);
		}

		if(view.getId() == tvTabContactUs.getId()) {
			raiseCallBack(TAB_CONTACT_US);
		}

		TextView tv = (TextView) view;
		setTab(tv);
	}

	private void setTab( TextView tv) {
		resetButtonHover();
		resetButtonHover(tv);
	}

	private void raiseCallBack( int whichTab) {
		if(listener == null) return;
		listener.onTabClicked(whichTab);
	}
}

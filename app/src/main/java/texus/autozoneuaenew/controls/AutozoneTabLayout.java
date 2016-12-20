package texus.autozoneuaenew.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import texus.autozoneuaenew.ApplicationClass;

/**
 * Created by sandeep on 10/26/2016.
 */


public class AutozoneTabLayout extends TabLayout {



    public AutozoneTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AutozoneTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutozoneTabLayout(Context context) {
        super(context);
        init();
    }

    @Override
    public void setupWithViewPager(ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        if (ApplicationClass.getInstance().appFont != null) {
            this.removeAllTabs();
            ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
            PagerAdapter adapter = viewPager.getAdapter();
            for (int i = 0, count = adapter.getCount(); i < count; i++) {
                Tab tab = this.newTab();
                this.addTab(tab.setText(adapter.getPageTitle(i)));
                AppCompatTextView view = (AppCompatTextView)
                        ((ViewGroup) slidingTabStrip.getChildAt(i)).getChildAt(1);
                view.setTypeface(ApplicationClass.getInstance().appFont, Typeface.NORMAL);
            }
        }
    }


    private void init() {
    }


}

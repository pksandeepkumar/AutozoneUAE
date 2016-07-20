package texus.autozoneuae.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sbl.lafeef.datamodel.FourCategory;
import texus.autozoneuae.R;

/**
 * Created by sandeep on 8/7/16.
 */
public class SlidePagerAdapter extends PagerAdapter {

    Context context;

    ArrayList<String> imageList;

    @Override
    public int getCount() {
        if( imageList != null ) {
            return imageList.size();
        }
        return 0;

    }

    public String getURL( int pos) {
        if(imageList != null) {
            return imageList.get(pos);
        }
        return null;

    }

    public SlidePagerAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        LinearLayout layout = new LinearLayout(context);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child =  inflater.inflate(getLayoutID(),layout);

        ImageView imageView = (ImageView) child.findViewById(R.id.imImage);

        Glide.with(context)
                .load(url)
                .error(R.drawable.no_preview)
                .into(imImage);

        FourCategory fourCategory = getURL(position);
        Log.e("HexCatAdapter","instantiateItem");
        if(fourCategory != null) {
            Log.e("HexCatAdapter","instantiateItem1111");
            if(fourCategory.category1 != null) {
                Log.e("HexCatAdapter","fourCategory.category1 name:" + fourCategory.category1.name_english);
                cat1.setVisibility(View.VISIBLE);
                cat1.setValues(fourCategory.category1, fourCategory.category1.getName(),
                        fourCategory.category1.image);
                cat1.setVisibility(View.VISIBLE);
            } else {

            }

            if(fourCategory.category2 != null) {
//                cat2.setValues(fourCategory.category2);
                cat2.setVisibility(View.VISIBLE);
                cat2.setValues(fourCategory.category2, fourCategory.category2.getName(),
                        fourCategory.category2.image);
            }

            if(fourCategory.category3 != null) {
//                cat3.setValues(fourCategory.category3);
                cat3.setVisibility(View.VISIBLE);
                cat3.setValues(fourCategory.category3, fourCategory.category3.getName(),
                        fourCategory.category3.image);
            }

            if(fourCategory.category4 != null) {
//                cat4.setValues(fourCategory.category4);
                cat4.setVisibility(View.VISIBLE);
                cat4.setValues(fourCategory.category4, fourCategory.category4.getName(),
                        fourCategory.category4.image);
            }
        }

//        Log.e("ImageAdapter","URL:" + url);

//        Glide.with(context)
//                .load(url)
//                .error(R.drawable.no_preview)
//                .into(imImage);
        //Glide is not working so picasso used


//        Picasso.with(context).load(url).placeholder(R.drawable.no_preview)
//                .error(R.drawable.no_preview).into(imImage);


        ((ViewPager) collection).addView(layout, 0);

        return layout;
    }




    public int getLayoutID() {
        return R.layout.pager_slides;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((LinearLayout) view);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }


}

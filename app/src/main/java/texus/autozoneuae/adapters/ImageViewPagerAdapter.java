package texus.autozoneuae.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> origin/master
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import texus.autozoneuae.R;

/**
 * Created by sandeep on 8/7/16.
 */
public class ImageViewPagerAdapter  extends PagerAdapter {

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

    public ImageViewPagerAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public Object instantiateItem(View collection, int position) {


        LinearLayout layout = new LinearLayout(context);
<<<<<<< HEAD
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
=======
        LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
>>>>>>> origin/master
        View child =  inflater.inflate(getLayoutID(),layout);

        ImageView imImage = (ImageView) child.findViewById(R.id.imImage);

        String url = getURL(position);
<<<<<<< HEAD
        Log.e("ImageAdapter","URL:" + url);

        Glide.with(context)
                .load(url)
                .error(R.drawable.no_preview)
                .placeholder(R.drawable.cover_image)
                .into(imImage);
        //Glide is not working so picasso used


//        Picasso.with(context).load(url).placeholder(R.drawable.no_preview)
//                .error(R.drawable.no_preview).into(imImage);
=======

        Glide.with(context)
                .load(url)
                .into(imImage);
>>>>>>> origin/master

        ((ViewPager) collection).addView(layout, 0);

        return layout;
    }




    public int getLayoutID() {
        return R.layout.pager_item_product_image;
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

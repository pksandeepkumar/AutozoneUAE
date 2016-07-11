package texus.autozoneuae.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import texus.autozoneuae.R;
import texus.autozoneuae.adapters.ImageViewPagerAdapter;
import texus.autozoneuae.controls.ColorRow;
import texus.autozoneuae.controls.HeadderRow;
import texus.autozoneuae.controls.LineRow;
import texus.autozoneuae.controls.NameValueRow;
import texus.autozoneuae.controls.SquirePagerView;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.datamodels.SpecData;
import texus.autozoneuae.utility.Utility;


/**
 * Created by sandeep on 8/4/16.
 */
public class ProductDetailsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    Product product;



    class ProductImageViewHoder extends RecyclerView.ViewHolder {

        ViewPager viewPager;
        SquirePagerView pagerView = null;
        CirclePageIndicator indicator;

        public ProductImageViewHoder(View itemView) {
            super(itemView);
            pagerView = (SquirePagerView) itemView.findViewById(R.id.viewpager);
            viewPager = pagerView.viewPager;
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
            setValues();
        }

        public void setValues() {

            ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(context, product.image_urls);

            viewPager.setAdapter(adapter);

            indicator.setFillColor(Color.parseColor("#7e7e7e"));
            indicator.setStrokeColor(Color.parseColor("#7e7e7e"));
            indicator.setRadius(indicator.getRadius() * 3f);
            indicator.setViewPager(viewPager);

        }
    }

    class ProdcutSpecViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llSpecHolder;

        public ProdcutSpecViewHolder(View itemView) {
            super(itemView);
            llSpecHolder = (LinearLayout) itemView.findViewById(R.id.llSpecHolder);
            setValues();
        }

        public void setValues() {
            ArrayList<SpecData> specDatas
                    = SpecData.getParesed(Utility.readFromAssets("getProductSpec.json", context));

            HeadderRow headderRow = new HeadderRow(context,"Specifications");
            llSpecHolder.addView(headderRow);

            int count = 0;
            for(SpecData specData : specDatas) {
                if(specData.type == SpecData.SPEC_TYPE_NORMAL) {
                    NameValueRow row = new NameValueRow(context, specData.name, specData.value);
                    llSpecHolder.addView(row);
                } else if(specData.type == SpecData.SPEC_TYPE_COLOR) {
                    ColorRow row = new ColorRow(context, specData.name, specData.colors);
                    llSpecHolder.addView(row);
                }
                count++;
                if(count< specDatas.size()) {
                    LineRow row = new LineRow(context);
                    llSpecHolder.addView(row);
                }
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("Adapter","Returning view type....................................................");
        return position;

    }



    public ProductDetailsRecyclerAdapter(Context context, Product product) {
        this.context = context;
        this.product = product;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: View layoutViewProductImage = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product_images, null);
                Log.e("Adapter","Returning image holder...........");
                return new ProductImageViewHoder(layoutViewProductImage);

            case 1:View layoutViewProductSpec = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product_spec, null);
                Log.e("Adapter","Returning spec holder...........");
                return new ProdcutSpecViewHolder(layoutViewProductSpec);

            default:
                Log.e("Adapter","Returning default image holder...........");
                layoutViewProductImage = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_product_images, null);
                return new ProductImageViewHoder(layoutViewProductImage);
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        Log.e("Adapter","Bind view position:" + position);
//        if(position == 0) {
//            Log.e("Adapter","On values Image Holder");
//            ProductImageViewHoder productImageViewHoder = (ProductImageViewHoder) holder;
//            productImageViewHoder.setValues();
//        } else if( position == 1) {
//            Log.e("Adapter","On values Spec Holder");
//            ProdcutSpecViewHolder prodcutSpecViewHolder = (ProdcutSpecViewHolder) holder;
//            prodcutSpecViewHolder.setValues();
//        }

    }



    @Override
    public int getItemCount() {
        return 2;
    }
}
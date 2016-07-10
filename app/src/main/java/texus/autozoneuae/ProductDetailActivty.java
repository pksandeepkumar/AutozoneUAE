package texus.autozoneuae;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import texus.autozoneuae.adapters.ImageViewPagerAdapter;
import texus.autozoneuae.controls.ColorRow;
import texus.autozoneuae.controls.HeadderRow;
import texus.autozoneuae.controls.LineRow;
import texus.autozoneuae.controls.NameValueRow;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.datamodels.SpecData;
import texus.autozoneuae.utility.Utility;

/**
 * Created by sandeep on 7/7/16.
 */
public class ProductDetailActivty  extends AppCompatActivity {

    public static final String PARAM_PRODUCT = "ParamProduct";

    ViewPager viewPager;
    Product product;

    LinearLayout llSpecHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ScrollView not works effectively
        setContentView(R.layout.activity_product_details);

        product = getIntent().getParcelableExtra(PARAM_PRODUCT);
        init();

    }

    private void init() {
        if(product == null) {
            Log.e("XXXXX","Product Is Null");
        } else {
            if( product.image_urls == null) {
                Log.e("XXXXX","Product Image URL Is Null");
            } else {
                Log.e("XXXXX","Product Image URL SIZE:" + product.image_urls.size() );
            }

        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(this, product.image_urls);
        viewPager.setAdapter(adapter);


        CirclePageIndicator  indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setFillColor(Color.parseColor("#7e7e7e"));
        indicator.setStrokeColor(Color.parseColor("#7e7e7e"));
        indicator.setRadius(indicator.getRadius() * 3f);
        indicator.setViewPager(viewPager);

        llSpecHolder = (LinearLayout) findViewById(R.id.llSpecHolder);

        ArrayList<SpecData> specDatas
                = SpecData.getParesed(Utility.readFromAssets("getProductSpec.json", this));

        HeadderRow headderRow = new HeadderRow(this,"Specifications");
        llSpecHolder.addView(headderRow);

        int count = 0;
        for(SpecData specData : specDatas) {
            if(specData.type == SpecData.SPEC_TYPE_NORMAL) {
                NameValueRow row = new NameValueRow(this, specData.name, specData.value);
                llSpecHolder.addView(row);
            } else if(specData.type == SpecData.SPEC_TYPE_COLOR) {
                ColorRow row = new ColorRow(this, specData.name, specData.colors);
                llSpecHolder.addView(row);
            }
            count++;
            if(count< specDatas.size()) {
                LineRow row = new LineRow(this);
                llSpecHolder.addView(row);
            }



        }



    }

}

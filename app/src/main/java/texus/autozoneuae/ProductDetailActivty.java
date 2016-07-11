package texus.autozoneuae;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import texus.autozoneuae.adapter.ImageViewPagerAdapter;
import texus.autozoneuae.controls.ColorRow;
import texus.autozoneuae.controls.HeadderRow;
import texus.autozoneuae.controls.LineRow;
import texus.autozoneuae.controls.NameValueRow;
import texus.autozoneuae.controls.SquirePagerView;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.datamodels.SpecData;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.utility.Utility;

/**
 * Created by sandeep on 7/7/16.
 */
public class ProductDetailActivty  extends AppCompatActivity {

    public static final String PARAM_PRODUCT = "ParamProduct";

    SquirePagerView squirePagerView;
    ViewPager viewPager;
    Product product;

    LinearLayout llSpecHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        product = getIntent().getParcelableExtra(PARAM_PRODUCT);

        if(product != null) {
            ActionBar actionBar = getSupportActionBar();
            if( actionBar != null) {
                actionBar.setTitle(product.product_name);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        init();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        if(product == null) {
            return;
        }
        squirePagerView = (SquirePagerView) findViewById(R.id.squirePagerView);
        //In landscape mode, we use squire viewpager, in portrait mode we use SquirePagerView
        if(squirePagerView != null) {
            viewPager = squirePagerView.viewPager;
        } else {
            viewPager = (ViewPager) findViewById(R.id.viewpager);
        }

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

        LoadProductSpecData task = new LoadProductSpecData(this, headderRow);
        task.execute();
    }

    public void publishSpec(ArrayList<SpecData> specDatas) {
        if(specDatas == null){
            Log.e("ProductDetailActivity","Specs Is null");
            return;
        }

        Log.e("ProductDetailActivity","Spec Data Size:" + specDatas.size());

        llSpecHolder.removeAllViews();

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


    public class LoadProductSpecData extends AsyncTask<Void, Void, Void> {

        Context context;
        ArrayList<SpecData> specDatas = null;
        HeadderRow headderRow;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            headderRow.setTitle("Specifications" + "( Loading....)");
        }

        public LoadProductSpecData(Context context, HeadderRow headderRow) {
            this.context = context;
            this.headderRow = headderRow;
        }

        @Override
        protected Void doInBackground(Void... params) {

            String resp = Utility.getData(SpecData.FILE_NAME_START + product.product_id);
            if(resp.length() != 0) {
                Log.e("ProductDetailActivity","OFFLINE DATA");
                specDatas = SpecData.getParesed(resp);
                publishProgress();

            }

            NetworkService.getAndSave(ApplicationClass.URL_GET_PRODUCT_SPEC +product.product_id,
                    SpecData.FILE_NAME_START + product.product_id);

            String resp2 = Utility.getData(SpecData.FILE_NAME_START + product.product_id);

            if(!resp.equals(resp2)) {
                specDatas = SpecData.getParesed(resp2);
                publishProgress();
            } else{
                Log.e("ProductDetailActivity","Response are same!!!!");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.e("ProductDetailActivity","Calling publish!!!!");
            publishSpec(specDatas);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }



    }

}

package texus.autozoneuaenew;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import texus.autozoneuaenew.adapter.ImageViewPagerAdapter;
import texus.autozoneuaenew.controls.ColorRow;
import texus.autozoneuaenew.controls.HeadderRow;
import texus.autozoneuaenew.controls.LineRow;
import texus.autozoneuaenew.controls.NameValueRow;
import texus.autozoneuaenew.controls.SquirePagerView;
import texus.autozoneuaenew.datamodels.Product;
import texus.autozoneuaenew.datamodels.SpecData;
import texus.autozoneuaenew.dialogs.ProgressDialog;
import texus.autozoneuaenew.network.Downloader;
import texus.autozoneuaenew.network.NetworkService;
import texus.autozoneuaenew.utility.Utility;

/**
 * Created by sandeep on 7/7/16.
 * <p>
 * Link:http://stackoverflow.com/questions/13257990/android-webview-inside-scrollview-scrolls-only-scrollview
 */
public class ProductDetailActivty extends AppCompatActivity {

    public static final String PARAM_PRODUCT = "ParamProduct";

    SquirePagerView squirePagerView;
    ViewPager viewPager;
    Product product;

    LinearLayout llSpecHolder;
    LinearLayout llDesccHolder;
    String pdfFileName = "";
    WebView description_web;
    CardView cardWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        product = getIntent().getParcelableExtra(PARAM_PRODUCT);

        if (product != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(product.product_name);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            pdfFileName = "Brochure" + product.product_id + ".pdf";
        }

        init();

    }

    public void getAQuote(View view) {
        Intent intent = new Intent(this, EnquiryFormActivity.class);
        intent.putExtra(EnquiryFormActivity.PARAM_PRODUCT_NAME, product.product_name);
        startActivity(intent);
    }

    public void downloadPDF(View view) {
        LoadAndViewPDF task = new LoadAndViewPDF(this);
        task.execute();
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
        if (product == null) {
            return;
        }
        squirePagerView = (SquirePagerView) findViewById(R.id.squirePagerView);
        //In landscape mode, we use squire viewpager, in portrait mode we use SquirePagerView
        if (squirePagerView != null) {
            viewPager = squirePagerView.viewPager;
        } else {
            viewPager = (ViewPager) findViewById(R.id.viewpager);
        }

        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(this, product.image_urls);
        viewPager.setAdapter(adapter);


        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setFillColor(Color.parseColor("#7e7e7e"));
        indicator.setStrokeColor(Color.parseColor("#7e7e7e"));
        indicator.setRadius(indicator.getRadius() * 3f);
        indicator.setViewPager(viewPager);

        llSpecHolder = (LinearLayout) findViewById(R.id.llSpecHolder);
        llDesccHolder = (LinearLayout) findViewById(R.id.llDesccHolder);
        description_web = (WebView) findViewById(R.id.description_web);

//        ArrayList<SpecData> specDatas
//                = SpecData.getParesed(Utility.readFromAssets("getProductSpec.json", this));

        HeadderRow headderRow = new HeadderRow(this, "Specifications");
        llSpecHolder.addView(headderRow);

        LoadProductSpecData task = new LoadProductSpecData(this, headderRow);
        task.execute();

//        loadProductDetails();
    }

    private void loadProductDetails() {

        description_web.getSettings().setLoadsImagesAutomatically(true);
        description_web.getSettings().setJavaScriptEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        description_web.loadUrl(ApplicationClass.URL_GET_PRODUCT_DESC + product.product_id);

//        LoadProductDesc task = new LoadProductDesc(this);
//        task.execute();
    }

    public void publishSpec(ArrayList<SpecData> specDatas) {
        if (specDatas == null) {
            Log.e("ProductDetailActivity", "Specs Is null");
            return;
        }

        Log.e("ProductDetailActivity", "Spec Data Size:" + specDatas.size());

        llSpecHolder.removeAllViews();

        HeadderRow headderRow = new HeadderRow(this, "Specifications");
        llSpecHolder.addView(headderRow);

        int count = 0;
        for (SpecData specData : specDatas) {
            if (specData.type == SpecData.SPEC_TYPE_NORMAL) {
                NameValueRow row = new NameValueRow(this, specData.name, specData.value);
                llSpecHolder.addView(row);
            } else if (specData.type == SpecData.SPEC_TYPE_COLOR) {
                ColorRow row = new ColorRow(this, specData.name, specData.colors);
                llSpecHolder.addView(row);
            }
            count++;
            if (count < specDatas.size()) {
                LineRow row = new LineRow(this);
                llSpecHolder.addView(row);
            }
        }

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.element_buttons, null);
        llSpecHolder.addView(child);
    }


    public class LoadProductDesc extends AsyncTask<Void, Void, Void> {

        Context context;
        String resp;
        StringBuilder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public LoadProductDesc(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            builder = new StringBuilder();

            builder.append(Utility.readFromAssets("html_start.txt", context));

            resp = NetworkService.get(ApplicationClass.URL_GET_PRODUCT_DESC
                    + product.product_id);

            builder.append(resp);

            builder.append("</body></html>");

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

//            description_web.loadDataWithBaseURL("", builder.toString(), "text/html", "UTF-8", "");
            description_web.loadDataWithBaseURL("fake://not/needed",
                    builder.toString(), "text/html", "utf-8", "");

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
            if (resp.length() != 0) {
                Log.e("ProductDetailActivity", "OFFLINE DATA");
                specDatas = SpecData.getParesed(resp);
                publishProgress();

            }

            NetworkService.getAndSave(ApplicationClass.URL_GET_PRODUCT_SPEC + product.product_id,
                    SpecData.FILE_NAME_START + product.product_id);

            String resp2 = Utility.getData(SpecData.FILE_NAME_START + product.product_id);

            if (!resp.equals(resp2)) {
                specDatas = SpecData.getParesed(resp2);
                publishProgress();
            } else {
                Log.e("ProductDetailActivity", "Response are same!!!!");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.e("ProductDetailActivity", "Calling publish!!!!");
            publishSpec(specDatas);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }


    }


    public class LoadAndViewPDF extends AsyncTask<Void, Void, Void> {

        Context context;

        boolean status;

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.show();

        }

        public LoadAndViewPDF(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            File folder = new File(ApplicationClass.PDF_FOLDER);
            File file = new File(folder, pdfFileName);
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String pdfURL = NetworkService.get(ApplicationClass.URL_GET_PRODUCT_PDF + product.product_id);
            if (pdfURL.trim().length() != 0) {
                status = Downloader.DownloadFile(pdfURL, file);
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.hide();
            if (status) {
                showPdf();
            } else {
                Toast.makeText(context, "No pdf available!!", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void showPdf() {
        File file = new File(ApplicationClass.PDF_FOLDER + File.separator + pdfFileName);
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

}

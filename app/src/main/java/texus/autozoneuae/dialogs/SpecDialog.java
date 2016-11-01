package texus.autozoneuae.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

import texus.autozoneuae.ApplicationClass;
import texus.autozoneuae.R;
import texus.autozoneuae.controls.AVLoadingIndicatorView;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.utility.Utility;

/**
 * Created by sandeep on 21/4/16.
 */
public class SpecDialog extends Dialog {


    Product productData;

    WebView description_web;

    AVLoadingIndicatorView avlProgress;


    public Context mContext = null;

    public SpecDialog(Context context, Product productData) {
        super(context);
        mContext = context;
        this.productData = productData;
        init();
    }

    private void initViews() {
        description_web = (WebView) this.findViewById(R.id.description_web) ;
        avlProgress = (AVLoadingIndicatorView) this.findViewById(R.id.avlProgress);
    }

    private void loadProductDetails() {
        if( productData == null ) return;
        description_web.getSettings().setLoadsImagesAutomatically(true);
        description_web.getSettings().setJavaScriptEnabled(true);

        LoadProductDesc task = new LoadProductDesc(mContext);
        task.execute();

//        description_web.loadUrl(ApplicationClass.URL_GET_PRODUCT_DESC + productData.product_id);
    }

    private void setValues() {
        loadProductDetails();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_specification);

        initViews();
        setCanceledOnTouchOutside(false);
        setValues();
    }

    public class LoadProductDesc extends AsyncTask<Void, Void, Void> {
        Context context;
        String resp;
        StringBuilder builder;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avlProgress.setVisibility(View.VISIBLE);
        }

        public LoadProductDesc(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            builder = new StringBuilder();

            builder.append(Utility.readFromAssets("html_start.txt", context));

            resp = NetworkService.get(ApplicationClass.URL_GET_PRODUCT_DESC
                    + productData.product_id);
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
            avlProgress.setVisibility(View.INVISIBLE);

//            description_web.loadDataWithBaseURL("", builder.toString(), "text/html", "UTF-8", "");
            description_web.loadDataWithBaseURL("fake://not/needed",
                    builder.toString(), "text/html", "utf-8", "");

        }


    }


}

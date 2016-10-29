package texus.autozoneuae.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.webkit.WebView;

import texus.autozoneuae.ApplicationClass;
import texus.autozoneuae.R;
import texus.autozoneuae.datamodels.Product;

/**
 * Created by sandeep on 21/4/16.
 */
public class SpecDialog extends Dialog {


    Product productData;

    WebView description_web;


    public Context mContext = null;

    public SpecDialog(Context context, Product productData) {
        super(context);
        mContext = context;
        this.productData = productData;
        init();
    }

    private void initViews() {
        description_web = (WebView) this.findViewById(R.id.description_web) ;


    }

    private void loadProductDetails() {
        if( productData == null ) return;
        description_web.getSettings().setLoadsImagesAutomatically(true);
        description_web.getSettings().setJavaScriptEnabled(true);
        description_web.loadUrl(ApplicationClass.URL_GET_PRODUCT_DESC + productData.product_id);
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


}

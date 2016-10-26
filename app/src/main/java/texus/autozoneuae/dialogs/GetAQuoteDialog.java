package texus.autozoneuae.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import orange.sbl.com.edinette.EdinetteApplication;
import orange.sbl.com.edinette.R;
import orange.sbl.com.edinette.controls.BillItemControll;
import orange.sbl.com.edinette.datamodels.BillData;
import orange.sbl.com.edinette.datamodels.BillOrder;
import orange.sbl.com.edinette.datamodels.BillTransaction;
import orange.sbl.com.edinette.networks.WebServiceCalls;
import orange.sbl.com.edinette.utility.Utility;
import texus.autozoneuae.R;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.preferance.SavedPreferance;

/**
 * Created by sandeep on 21/4/16.
 */
public class GetAQuoteDialog extends Dialog {


    Product productData;

    EditText etSubject;
    EditText etName;
    EditText etEmail;
    EditText etMessage;


    public Context mContext = null;

    public GetAQuoteDialog(Context context, Product productData) {
        super(context);
        mContext = context;
        this.productData = productData;
        init();
    }

    private void initViews() {
        etSubject = (EditText) this.findViewById(R.id.etSubject) ;
        etName = (EditText) this.findViewById(R.id.etName) ;
        etName.setText(SavedPreferance.getUserName(mContext));
        etEmail = (EditText) this.findViewById(R.id.etEmail) ;
        etEmail.setText(SavedPreferance.getEmailID(mContext));
        etMessage = (EditText) this.findViewById(R.id.etMessage) ;
    }

    private void setValues() {

    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_enguiry);

        initViews();
        setCanceledOnTouchOutside(false);
        setValues();
    }

    public void sendMail( View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","vipizone@gmail.com ", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
        mContext.startActivity(Intent.createChooser(emailIntent, "Send email to AutozoneUAE"));

        SavedPreferance.setUserName(mContext,etName.getText().toString());
        SavedPreferance.setEmailID(mContext,etEmail.getText().toString());
    }
}

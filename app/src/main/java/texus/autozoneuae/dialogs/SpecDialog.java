package texus.autozoneuae.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import texus.autozoneuae.R;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.preferance.SavedPreferance;
import texus.autozoneuae.utility.AppToast;

/**
 * Created by sandeep on 21/4/16.
 */
public class SpecDialog extends Dialog {


    Product productData;

    EditText etSubject;
    EditText etName;
    EditText etEmail;
    EditText etMessage;

    Button btnSendMail;


    public Context mContext = null;

    public SpecDialog(Context context, Product productData) {
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
        btnSendMail = (Button) this.findViewById(R.id.btnSendMail);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
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

    public void sendMail( ) {
        if(etName.getText().toString().trim().length() == 0) {
            AppToast.showMessage("Please enter name");
            return;
        }

        if(etEmail.getText().toString().trim().length() == 0) {
            AppToast.showMessage("Please enter e mail");
            return;
        }

        if(etMessage.getText().toString().trim().length() == 0) {
            AppToast.showMessage("Please enter your message");
            return;
        }


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","vipizone@gmail.com ", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
        mContext.startActivity(Intent.createChooser(emailIntent, "Send email to AutozoneUAE"));

        SavedPreferance.setUserName(mContext,etName.getText().toString());
        SavedPreferance.setEmailID(mContext,etEmail.getText().toString());
    }
}

package texus.autozoneuaenew;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import texus.autozoneuaenew.preferance.SavedPreferance;

/**
 * Created by sandeep on 01/08/16.
 */
public class EnquiryFormActivity extends AppCompatActivity {

    public static final String PARAM_PRODUCT_NAME = "product_name";

    Context context;

    EditText etSubject;
    EditText etName;
    EditText etEmail;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_enguiry);

        context = this;

//        setActionBar();

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.enquiry_form));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViews();

    }

    public void sendMail( View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","vipizone@gmail.com ", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
        startActivity(Intent.createChooser(emailIntent, "Send email to AutozoneUAE"));

        SavedPreferance.setUserName(this,etName.getText().toString());
        SavedPreferance.setEmailID(this,etEmail.getText().toString());
    }

    private void initViews() {
        etSubject = (EditText) this.findViewById(R.id.etSubject) ;
        etName = (EditText) this.findViewById(R.id.etName) ;
        etName.setText(SavedPreferance.getUserName(this));
        etEmail = (EditText) this.findViewById(R.id.etEmail) ;
        etEmail.setText(SavedPreferance.getEmailID(this));
        etMessage = (EditText) this.findViewById(R.id.etMessage) ;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString(PARAM_PRODUCT_NAME);
            etSubject.setText("Get a quote -" + value);
        }
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle(getResources().getString(R.string.enquiry_form));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
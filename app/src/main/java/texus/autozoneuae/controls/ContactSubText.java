package texus.autozoneuae.controls;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import texus.autozoneuae.R;

/**
 * Created by sandeep on 22/10/16.
 */

public class ContactSubText extends RelativeLayout {

    public static final int TYPE_PHONE = 1;
    public static final int TYPE_EMAIL = 2;
    public static final int TYPE_NONE = 3;
    public static final int TYPE_FAX = 4;
    public static final int TYPE_WATSAPP = 5;

    String paddingText = "";

    Context mContext;

    public ContactSubText(Context context, String text, int type ) {
        super(context);
        init(context, text, type);
    }

    private void init(Context context, final String text, int type) {
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.control_sub_text, this);

        ImageView imIcon = (ImageView) child.findViewById(R.id.imIcon);
        TextView tvText = (TextView) child.findViewById(R.id.tvText);

        if( type == TYPE_NONE ) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imIcon.getLayoutParams();
            lp.width = 0;
            lp.height = 0;
            lp.setMargins(0,0,0,0);
            imIcon.setLayoutParams(lp);
        } else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvText.getLayoutParams();
            lp.setMargins(getResources()
                    .getDimensionPixelSize(R.dimen.padding_two_dp),0,0,0);
            tvText.setLayoutParams(lp);
        }

        if(type == TYPE_PHONE || type == TYPE_WATSAPP) {
            if(type == TYPE_PHONE) {
                imIcon.setImageResource(R.drawable.ic_phone);
            } else {
                imIcon.setImageResource(R.drawable.ic_watsapp);
            }

            tvText.setClickable(true);
            tvText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Opening call app..", Toast.LENGTH_LONG).show();
                    dialNumber(text);
                    paddingText = "  ";
                }
            });
        }

        if( type == TYPE_EMAIL) {
            imIcon.setImageResource(R.drawable.ic_email);
            tvText.setClickable(true);
            tvText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Opening eMail app..", Toast.LENGTH_LONG).show();
                    sendMail(text);
                    paddingText = "  ";
                }
            });

        }

        if( type == TYPE_FAX) {
            imIcon.setImageResource(R.drawable.ic_fax);
            tvText.setClickable(true);
            tvText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "Opening eMail app..", Toast.LENGTH_LONG).show();
//                    dialNumber(text);
                    paddingText = "  ";
                }
            });

        }

        tvText.setText( paddingText + text);



    }

    private void dialNumber( String number ) {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            String uri = "tel:" + number ;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            mContext.startActivity(intent);
        }
    }

    private void sendMail( String eMail) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, eMail);
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        mContext.startActivity(Intent.createChooser(intent, "Send Email"));
    }



}

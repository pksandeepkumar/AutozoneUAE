package texus.autozoneuae.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import texus.autozoneuae.R;

/**
 * Created by sandeep on 21/4/16.
 */
public class ProgressDialog extends Dialog {

    private TextView mTvMessage = null;

    private String message = "";

    public ProgressDialog(Context context, String message) {
        super(context);
        this.message = message;
        init();
        setMessage(this.message);
    }

    public ProgressDialog(Context context) {
        super(context);
        init();
    }

    private void init() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_progress_dialog);

        setCanceledOnTouchOutside(false);

        setCancelable(false);

    }

    public void setMessage( String message) {

        this.message = message;

        mTvMessage.setText(this.message);
    }
}

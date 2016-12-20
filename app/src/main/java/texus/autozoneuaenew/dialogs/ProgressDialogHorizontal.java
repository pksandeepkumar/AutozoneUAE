package texus.autozoneuaenew.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import texus.autozoneuaenew.R;


/**
 * Created by sandeep on 21/4/16.
 */
public class ProgressDialogHorizontal extends Dialog {

    private TextView mTvMessage = null;

    private String message = "";

    public ProgressDialogHorizontal(Context context, String message) {

        super(context);

        this.message = message;

        init();

        setMessage(this.message);
    }

    public ProgressDialogHorizontal(Context context) {
        super(context);
        init();
    }

    private void init() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_progress_dialog);

        mTvMessage = (TextView) findViewById(R.id.tvMessage);

        setCanceledOnTouchOutside(false);

        setCancelable(false);

    }

    public void hideDialog() {
        if(isShowing()) {
            hide();
        }
    }

    public void setMessage( String message) {

        this.message = message;

        mTvMessage.setText(this.message);
    }
}

package texus.autozoneuaenew.utility;

import android.widget.Toast;

import texus.autozoneuaenew.ApplicationClass;

/**
 * Created by sandeep on 25/10/16.
 */

public class AppToast {

    public static void showMessage( String message) {
        Toast.makeText(ApplicationClass.getInstance().getApplicationContext(),
                message, Toast.LENGTH_LONG).show();
    }

    public static void showDebug( String message) {
        Toast.makeText(ApplicationClass.getInstance().getApplicationContext(),
                message, Toast.LENGTH_LONG).show();
    }
}

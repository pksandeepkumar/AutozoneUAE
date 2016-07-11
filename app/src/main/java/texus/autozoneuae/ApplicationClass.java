package texus.autozoneuae;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by sandeep on 11/06/16.
 */
public class ApplicationClass extends Application {

    public static final String BASE_URL  = "http://autozoneuae.com/test";
    public static final String URL_GET_ALL_CATEGORIES  = BASE_URL + "/getAllCategoryData.php";
    public static final String URL_GET_PRODUCT_SPEC  = BASE_URL + "/getProductSpec.php?product_id=";
    public static String INTERNAL_RESPONSE_FOLDER = "FOLDER_RESPONSE";

    public int width = 0;
    public int height = 0;


    private static ApplicationClass ourInstance = new ApplicationClass();

    public static ApplicationClass getInstance() {
        return ourInstance;
    }

    public void makeDir( String folderPath) {
        File eDinetteDir =  new File(folderPath);
        if (null != eDinetteDir) {
            if (!eDinetteDir.exists()) {
                eDinetteDir.mkdir();
            }
        }
    }

    public void deleteDir( String folderPath) {
        File eDinetteDir =  new File(folderPath);
        if (null != eDinetteDir) {
            if (!eDinetteDir.exists()) {
                eDinetteDir.delete();
            }
        }
    }

    public void setWidthAndHeight(int width, int height) {

        Display display = (WindowManager) getSystemService(Context.WINDOW_SERVICE).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        if(width > height) {
            this.width = width/2;
            this.height = width;
        } else {
            this.width = width;
            this.height = height;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        loadConfiguration();
    }

    private void loadConfiguration() {
        File rootDir =  getFilesDir();
        INTERNAL_RESPONSE_FOLDER  = rootDir + "/" + "ResponseFolder";
        makeDir(INTERNAL_RESPONSE_FOLDER);


    }

}

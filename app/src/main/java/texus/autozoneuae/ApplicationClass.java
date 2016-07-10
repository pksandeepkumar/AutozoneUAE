package texus.autozoneuae;

import android.app.Application;
import android.graphics.Point;
import android.view.Display;

import java.io.File;

/**
 * Created by sandeep on 11/06/16.
 */
public class ApplicationClass extends Application {

    public static final String BASE_URL  = "http://autozoneuae.com/test";
    public static final String URL_GET_ALL_CATEGORIES  = BASE_URL + "/getAllCategoryData.php";
    public static final String URL_GET_PRODUCT_SPEC  = BASE_URL + "/getProductSpec.php?product_id=";
    public static String INTERNAL_RESPONSE_FOLDER = "FOLDER_RESPONSE";


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

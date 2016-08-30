package texus.autozoneuae;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.util.Random;

/**
 * Created by sandeep on 11/06/16.
 */
public class ApplicationClass extends Application {

    public static final String BASE_URL  = "http://autozoneuae.com/test";
    public static final String URL_GET_ALL_CATEGORIES  = BASE_URL
            + "/getAllCategoryData.php";
    public static final String URL_GET_PRODUCT_SPEC  = BASE_URL
            + "/getProductSpec.php?product_id=";
    public static final String URL_GET_PRODUCT_DESC  = BASE_URL
            + "/getProductDescription.php?product_id=";
    public static final String URL_GET_SLIDER_IMAGE  = BASE_URL
            + "/splash_screen/splash_screen_resp_json.json";

    public static final long REFRESH_TIME_IN_MILLISECONDS = 5000;

    public static final String TEMP_PDF = "http://autozoneuae.com/demo/wp-content/uploads/2016/06/1dummy.pdf";

    public static String INTERNAL_RESPONSE_FOLDER = "FOLDER_RESPONSE";
    public static String PDF_FOLDER = "AutozoneUAE";

    public int width = 0;
    public int height = 0;

    private int minHeight;
    private int maxHeight;

    //IN search page search start if the keyword has following length
    public static final int SEARCH_MIN_COUNT = 3;


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
        PDF_FOLDER = Environment.getExternalStorageDirectory() + File.separator
                + "AutoZoneUAE";
        makeDir(PDF_FOLDER);

        minHeight = getResources()
                .getDimensionPixelSize(R.dimen.item_card_image_min_width);
        maxHeight = getResources()
                .getDimensionPixelSize(R.dimen.item_card_image_max_width);


    }

    public int getHeight() {
        Random random = new Random();
        int height = minHeight + random.nextInt(maxHeight - minHeight);
        return height;
    }

}

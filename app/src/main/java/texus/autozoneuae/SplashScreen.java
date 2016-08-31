package texus.autozoneuae;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.datamodels.SlideData;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.preferance.SavedPreferance;
import texus.autozoneuae.utility.Utility;

/**
 * Created by sandeep on 12/07/16.
 */
public class SplashScreen  extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);

        Glide.with(this)
                .load(R.drawable.splash)
                .into(imgLogo);

//        LoadInitialData task = new LoadInitialData(this);
//        task.execute();

//        if(SavedPreferance.getAlreadyLoaded(this)) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }, SPLASH_TIME_OUT);
//        } else {
            LoadInitialData task = new LoadInitialData(this);
            task.execute();
//        }

    }


    public class LoadInitialData extends AsyncTask<Void, Void, Void> {

        Context context;

        boolean status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public LoadInitialData(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            status = NetworkService.getAndSave(
                    ApplicationClass.URL_GET_SLIDER_IMAGE, SlideData.FILENAME);

            status = NetworkService.getAndSave(
                    ApplicationClass.URL_GET_ALL_CATEGORIES, CatData.FILENAME);

            ArrayList<CatData> catDatas = CatData.getParesed(Utility.getData(CatData.FILENAME));

            CatData.insertDatas(catDatas, context);

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }



    }

}

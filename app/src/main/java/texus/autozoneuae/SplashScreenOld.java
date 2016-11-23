package texus.autozoneuae;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.ArrayList;

import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.utility.Utility;

/**
 * Created by sandeep on 12/07/16.
 */
public class SplashScreenOld  extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    private final int MY_PERMISSIONS_REQUEST_VIBRATE = 1231;

    ImageButton imPressToStart;

    Animation shake;
    MediaPlayer player;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoadInitialData task = new LoadInitialData(this);
        task.execute();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_VIBRATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enablePressToStart();
                } else {
//                    enablePressToStart();
                }
                return;
            }
        }
    }

    public void enablePressToStart() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) { ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.VIBRATE},
                MY_PERMISSIONS_REQUEST_VIBRATE);
            return;
        }

        player = new MediaPlayer();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shake = AnimationUtils.loadAnimation(this, R.anim.vibrate);

        imPressToStart = (ImageButton) findViewById(R.id.imPressToStart);
        imPressToStart.setEnabled(true);
        imPressToStart.setVisibility(View.VISIBLE);
        imPressToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDelayAndGotoDashboard();
                vibrator.vibrate((long)1000);
                v.startAnimation(shake);
                startSound();
            }
        });




    }

    private void startDelayAndGotoDashboard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                shake.cancel();
                Animation fade = AnimationUtils.loadAnimation(SplashScreenOld.this, R.anim.fade_out);;
                imPressToStart.startAnimation(fade);
                start();
            }
        }, SPLASH_TIME_OUT);
    }

    private void startSound( ){
        try {
            AssetFileDescriptor afd = getAssets().openFd("start.mp3");
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
//                    start();
//                    imPressToStart.clearAnimation();
                    vibrator.cancel();


                }
            });

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Intent i = new Intent(SplashScreenOld.this, MainActivityNew.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
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

//            status = NetworkService.getAndSave(
//                    ApplicationClass.URL_GET_SLIDER_IMAGE, SlideData.FILENAME);

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

//            start();
            enablePressToStart();


        }



    }

}

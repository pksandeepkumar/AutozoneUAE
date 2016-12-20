package texus.autozoneuaenew;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import texus.autozoneuaenew.controls.BottomTabControl;
import texus.autozoneuaenew.datamodels.CatData;
import texus.autozoneuaenew.fragments.FragmentAboutUS;
import texus.autozoneuaenew.fragments.FragmentContactUs;
import texus.autozoneuaenew.fragments.FragmentHome;
import texus.autozoneuaenew.fragments.FragmentServices;
import texus.autozoneuaenew.fragments.ProductsFragment;
import texus.autozoneuaenew.fragments.ProductsFragmentList;
import texus.autozoneuaenew.network.NetworkService;
import texus.autozoneuaenew.preferance.SavedPreferance;
import texus.autozoneuaenew.utility.Utility;

import static texus.autozoneuaenew.R.id.idBottomControl;
import static texus.autozoneuaenew.controls.BottomTabControl.TAB_ABOUT_US;
import static texus.autozoneuaenew.controls.BottomTabControl.TAB_CONTACT_US;
import static texus.autozoneuaenew.controls.BottomTabControl.TAB_HOME;
import static texus.autozoneuaenew.controls.BottomTabControl.TAB_TAB_SERVICE;

public class SplashProductScreensActivity extends AppCompatActivity implements BottomTabControl.OnTabClickedListener{


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;
    private final int MY_PERMISSIONS_REQUEST_VIBRATE = 1231;

    ImageButton imPressToStart;

    Animation shake;
    MediaPlayer player;
    Vibrator vibrator;

    BottomTabControl bottomControl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_product);



        LoadInitialData task = new LoadInitialData(this);
        task.execute();

        setWidthAndHeight();


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
            enablePressToStart();
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
                vibrator.vibrate((long)10000);
                v.startAnimation(shake);
                startSound();
            }
        });
    }

    private void startDelayAndGotoDashboard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                    imPressToStart.clearAnimation();
                    vibrator.cancel();
                }
            });

        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        LinearLayout llSplashScreenHolder = (LinearLayout) this.findViewById(R.id.llSplashScreenHolder);
        llSplashScreenHolder.removeAllViews();
        initViews();
    }



    private void initViews() {

        bottomControl = (BottomTabControl) findViewById(idBottomControl);
        bottomControl.setOnTabClickedListener(this);
        bottomControl.setTab(TAB_HOME);

    }

    @Override
    public void onTabClicked(int whichTab) {
        switch ( whichTab) {
            case TAB_HOME: addFragment(new FragmentHome());break;
            case TAB_ABOUT_US:  addFragment(new FragmentAboutUS());break;
            case TAB_TAB_SERVICE:  addFragment(new FragmentServices());break;
            case TAB_CONTACT_US:  addFragment(new FragmentContactUs());break;
        }
    }

    public void addFragment( Fragment fragment) {
        Log.e("MainActivityNew","addFragment");
        if(fragment == null) return;

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frFragmentContainer, fragment);
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Log.e("MainActivityNew","Fragment Added");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addFragment2( android.app.Fragment fragment) {
        Log.e("FoodMenuActivity","Adding Fragment................................");
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void setWidthAndHeight() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ApplicationClass.getInstance().setWidthAndHeight(width, height);
//        Toast.makeText(this,"Dime:" + width + ":" + height, Toast.LENGTH_LONG).show();
    }




    public void addFragment( Adapter adapter, CatData object ) {
        if(object == null) return;

        ProductsFragmentList fragment = new ProductsFragmentList();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ProductsFragment.PARAM_CAT_DATA, object);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, object.cat_name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public class LoadSlideShow extends AsyncTask<Void, Void, Void> {

        Context context;
        ArrayList<String> images = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public LoadSlideShow(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }


    public class LoadProducts extends AsyncTask<Void, Void, Void> {

        texus.autozoneuaenew.dialogs.ProgressDialog dialog = null;
        Context context;
        ArrayList<CatData> catDatas = null;
        boolean status = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new texus.autozoneuaenew.dialogs.ProgressDialog(context);
            dialog.show();
            Log.e("XXXXX"," ON LoadProducts....................................");

        }

        public LoadProducts(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            if(SavedPreferance.getAlreadyLoaded(context)) {
                catDatas = CatData.getParesed(Utility.getData(CatData.FILENAME));
                if(catDatas != null && catDatas.size() > 0) {
                    publishProgress();
                }

            }

            // The network call called here because we for every execution we need this cod eto be
            //executed
            status = NetworkService.getAndSave(
                    ApplicationClass.URL_GET_ALL_CATEGORIES, CatData.FILENAME);

            if(!status) return  null;

            CatData.insertDatas(catDatas, context);

            if(!SavedPreferance.getAlreadyLoaded(context)) {
                catDatas = CatData.getParesed(Utility.getData(CatData.FILENAME));
                publishProgress();
                SavedPreferance.setAlreadyLoaded(context, true);
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            hideDialog();
//            populateProductList(catDatas);
        }

        private void hideDialog(){
            if(dialog.isShowing()) {
                dialog.hide();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(!status) {
                hideDialog();
                Toast.makeText(context,"Something went wrong. Please try later!!", Toast.LENGTH_LONG).show();
            }
        }



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}

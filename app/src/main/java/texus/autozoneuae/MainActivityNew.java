package texus.autozoneuae;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import texus.autozoneuae.controls.BottomTabControl;
import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.fragments.FragmentAboutUS;
import texus.autozoneuae.fragments.FragmentHome;
import texus.autozoneuae.fragments.FragmentServices;
import texus.autozoneuae.fragments.ProductsFragment;
import texus.autozoneuae.fragments.ProductsFragmentList;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.preferance.SavedPreferance;
import texus.autozoneuae.utility.Utility;

import static texus.autozoneuae.R.id.idBottomControl;
import static texus.autozoneuae.controls.BottomTabControl.TAB_ABOUT_US;
import static texus.autozoneuae.controls.BottomTabControl.TAB_CONTACT_US;
import static texus.autozoneuae.controls.BottomTabControl.TAB_HOME;
import static texus.autozoneuae.controls.BottomTabControl.TAB_TAB_SERVICE;

public class MainActivityNew extends AppCompatActivity implements BottomTabControl.OnTabClickedListener{


    BottomTabControl bottomControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new2);
        initViews();

    }

    private void initViews() {

        bottomControl = (BottomTabControl) findViewById(idBottomControl);
        bottomControl.setOnTabClickedListener(this);
        onTabClicked(TAB_HOME);
    }

    @Override
    public void onTabClicked(int whichTab) {
        switch ( whichTab) {
            case TAB_HOME: addFragment(new FragmentHome());break;
            case TAB_ABOUT_US:  addFragment(new FragmentAboutUS());break;
            case TAB_TAB_SERVICE:  addFragment(new FragmentServices());break;
            case TAB_CONTACT_US:  addFragment(new FragmentAboutUS());break;
        }
    }

    public void addFragment( Fragment fragment) {
        Log.e("MainActivityNew","addFragment");
        if(fragment == null) return;

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frFragmentContainer, fragment);
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
        fragmentTransaction.add(R.id.frFragmentContainer, fragment);
        fragmentTransaction.commit();
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

        texus.autozoneuae.dialogs.ProgressDialog dialog = null;
        Context context;
        ArrayList<CatData> catDatas = null;
        boolean status = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new texus.autozoneuae.dialogs.ProgressDialog(context);
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
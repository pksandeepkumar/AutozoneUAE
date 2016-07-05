package texus.autozoneuae;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.fragments.ProductsFragment;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.preferance.SavedPreferance;
import texus.autozoneuae.utility.Utility;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);



        LoadProducts task = new LoadProducts(this);
        task.execute();

        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);

//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
//        collapsingToolbar.setTitle("");


    }

    public void populateProductList( ArrayList<CatData> catDatas) {
        Log.e("XXXXX"," ON populateProductList..............................");
        if( catDatas == null) return;
        if( catDatas.size() == 0) return;

        Adapter adapter = new Adapter(getSupportFragmentManager());

        for(CatData catData : catDatas) {
            addFragment(adapter, catData);
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void addFragment( Adapter adapter, CatData object ) {
        if(object == null) return;

        ProductsFragment fragment = new ProductsFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ProductsFragment.PARAM_CAT_DATA, object);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, object.cat_name);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clic ks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public class LoadProducts extends AsyncTask<Void, Void, Void> {

        texus.autozoneuae.dialogs.ProgressDialog dialog = null;
        Context context;
        ArrayList<CatData> catDatas = null;

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

            NetworkService.getAndSave(ApplicationClass.URL_GET_ALL_CATEGORIES, CatData.FILENAME);

            if(!SavedPreferance.getAlreadyLoaded(context)) {
                publishProgress();
                SavedPreferance.setAlreadyLoaded(context, true);
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            dialog.hide();
            populateProductList(catDatas);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
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

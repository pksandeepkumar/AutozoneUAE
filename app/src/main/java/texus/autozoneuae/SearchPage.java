package texus.autozoneuae;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import texus.autozoneuae.adapter.ProductRecycleAdapter;
import texus.autozoneuae.datamodels.Product;
import texus.autozoneuae.db.Databases;

/**
 * Created by sandeep on 14/7/16.
 */
public class SearchPage extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText etSearch;
    ImageView imSearch;
    Context context;

    LinearLayout llHolder;


    ProductRecycleAdapter adapter;

    ArrayList<Product> items = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;

        setActionBar();

        initViews();



    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar =getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle(getResources().getString(R.string.search_vehicle));
        }
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        imSearch = (ImageView) findViewById(R.id.imSearch);
        llHolder = (LinearLayout) findViewById(R.id.llHolder);

        setUpSearch();

//        adapter = new ItemRecyclerViewAdapter( context, items);
//        recyclerView.setAdapter(adapter);
    }

    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }


    private void setUpSearch() {


        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String text = etSearch.getText().toString().trim();
                    //To move cursor at end of the edittext
                    etSearch.setText("");
                    etSearch.append(text);
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = s.toString();
                Log.e("Search","Keyword:" + keyWord);
                if(keyWord.trim().length() == 0) {
//                    imSearch.setImageResource(R.drawable.ic_search);
                } else {
//                    imSearch.setImageResource(R.drawable.ic_close);
                    if(keyWord.trim().length() < ApplicationClass.SEARCH_MIN_COUNT) {
                        llHolder.removeAllViews();
                        return;
                    }


                    SearchItemTask task = new SearchItemTask(ApplicationClass.getInstance()
                            .getApplicationContext(), keyWord );
                    task.execute();

//                    Databases db = new Databases(ApplicationClass.getInstance()
//                            .getApplicationContext());
//
//                    llHolder.removeAllViews();
//                    LayoutInflater inflater = (LayoutInflater) context
//                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View child = inflater.inflate(R.layout.control_recycler_view, llHolder);
//                    recyclerView = (RecyclerView) child.findViewById(R.id.recycler_view);
////                    ItemData.searchItem(db,keyWord);
////                    recyclerView.setAdapter(null);
////                    recyclerView.removeAllViews();
//                    adapter = new ItemRecyclerViewAdapter( context, ItemData.searchItem(db,keyWord));
//                    recyclerView.setAdapter(adapter);
////                    recyclerView.requestLayout();
//
////                    adapter.swap(ItemData.searchItem(db,keyWord));
//
////            Log.e("doInBackground","Items size:" + items.size());
//
//                    db.close();

//

                }

            }
        });
    }

    public class SearchItemTask extends AsyncTask<Void, Void, Void> {

        Context context = null;

        ArrayList<Product> products = null;
        String keyWord;

        public SearchItemTask(Context context, String keyWord ) {
            this.context = context;
            this.keyWord = keyWord;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            Databases db = new Databases(ApplicationClass.getInstance()
                    .getApplicationContext());
            products = Product.searchProduct(db,keyWord);
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            if(products != null) {
                //Create recyclerview for every search -  because notifydatasetchange not working,
                llHolder.removeAllViews();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View child = inflater.inflate(R.layout.control_recycler_view_list, llHolder);
                recyclerView = (RecyclerView) child.findViewById(R.id.recycler_view);

                ProductRecycleAdapter mAdapter = new ProductRecycleAdapter(context, products);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                        ApplicationClass.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);


//                adapter = new ProductRecycleAdapter( context, products);
//                recyclerView.setAdapter(adapter);

            } else {
                Log.e("SearchPage","Items null.........");
            }
        }
    }

}

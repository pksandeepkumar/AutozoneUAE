package texus.autozoneuae;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import texus.autozoneuae.adapter.ProductDetailsRecyclerAdapter;
import texus.autozoneuae.datamodels.Product;

/**
 * Created by sandeep on 7/7/16.
 */
public class ProductDetailListActivty extends AppCompatActivity {

    public static final String PARAM_PRODUCT = "ParamProduct";

    RecyclerView recycler_view;

    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list_details);

        product = getIntent().getParcelableExtra(PARAM_PRODUCT);

        init();

    }

    private void init() {

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);

        ProductDetailsRecyclerAdapter adapter = new ProductDetailsRecyclerAdapter(this, product);

        recycler_view.setAdapter(adapter);

    }

}

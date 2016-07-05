/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package texus.autozoneuae.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import texus.autozoneuae.R;
import texus.autozoneuae.controls.MarginDecoration;
import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.datamodels.Product;

public class ProductsFragment extends Fragment {

    CatData catData = null;

    public static final String PARAM_CAT_DATA = "ParamCategoryData";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_product, container, false);

        catData = ( CatData ) getArguments().get(PARAM_CAT_DATA);


        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        recyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);

        if( catData != null) {
            ProductRecycleAdapter adapter = new ProductRecycleAdapter(getActivity(), catData.products);
            recyclerView.setAdapter(adapter);
        }


    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class ProductRecycleAdapter
            extends RecyclerView.Adapter<ProductRecycleAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<Product> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;



            public final View mView;
            public final ImageView imImage;
            public final TextView tvProductTiltle;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                imImage = (ImageView) view.findViewById(R.id.imImage);
                tvProductTiltle = (TextView) view.findViewById(R.id.tvProductTiltle);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + tvProductTiltle.getText();
            }
        }

        public Product getValueAt(int position) {
            return mValues.get(position);
        }

        public ProductRecycleAdapter(Context context, ArrayList<Product> products) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = products;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);
            //view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Product product = mValues.get(position);

            holder.tvProductTiltle.setText(product.product_name);

            if(product.image_urls != null) {
                if( product.image_urls.size() > 1) {
                    String url = product.image_urls.get(0);

                    Glide.with(holder.imImage.getContext())
                            .load(url)
                            .into(holder.imImage);
                }
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CheeseDetailActivity.class);
//                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}

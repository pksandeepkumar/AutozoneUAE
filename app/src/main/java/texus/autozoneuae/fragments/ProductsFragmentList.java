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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import texus.autozoneuae.ApplicationClass;
import texus.autozoneuae.ProductDetailActivty;
import texus.autozoneuae.R;
import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.datamodels.Product;

public class ProductsFragmentList extends Fragment {

    CatData catData = null;

    public static final String PARAM_CAT_DATA = "ParamCategoryData";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_list, container, false);

        catData = ( CatData ) getArguments().get(PARAM_CAT_DATA);


        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {



//        recyclerView.addItemDecoration(new MarginDecoration(getActivity()));
//        recyclerView.setHasFixedSize(true);

        if( catData != null) {
            ProductRecycleAdapter mAdapter = new ProductRecycleAdapter(getActivity(), catData.products);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                    ApplicationClass.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);


//            ProductRecycleAdapter adapter = new ProductRecycleAdapter(getActivity(), catData.products);
//            recyclerView.setAdapter(adapter);
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

        private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();

        private  final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
        private  final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
        private  final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;



            public final View mView;
            public final ImageView imImage;
            public final TextView tvProductTiltle;
            public final CardView card_view;

            public final View vBgLike;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                imImage = (ImageView) view.findViewById(R.id.imImage);
                tvProductTiltle = (TextView) view.findViewById(R.id.tvProductTiltle);
                vBgLike  = (View) view.findViewById(R.id.vBgLike);
                card_view  = (CardView) view.findViewById(R.id.card_view);
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
                    .inflate(R.layout.item_product_holder_sq2, parent, false);
            //view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Product product = mValues.get(position);

            holder.tvProductTiltle.setText(product.product_name);

//            LinearLayout.LayoutParams rlp = (LinearLayout.LayoutParams)
//                    holder.card_view.getLayoutParams();
////            rlp.height = product.card_height;
//            rlp.height =   ApplicationClass.getInstance().getHeight();;
//            holder.card_view.setLayoutParams(rlp);


//            Log.e("Adapeter","----------------1-----------------" + rlp.height);
            Log.e("Adapeter","Product ID:" + product.product_id);
            Log.e("Adapeter","Product Name:" + product.product_name);
            if(product.image_urls != null) {
                if( product.image_urls.size() >= 1) {
                    String url = product.image_urls.get(0);

                    Log.e("Adapeter","URL" + url);



                    Glide.with(holder.imImage.getContext())
                            .load(url)
                            .placeholder(R.drawable.loading_image)
                            .into(holder.imImage);
                }
            } else {
                Log.e("Adapeter","Images are NULL");
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateRipple(holder, product);
                }
            });


        }

        //This will show ripple animation and navigate to product detail activity after
        // animation complete
        private void animateRipple(final ViewHolder holder, final Product product) {
            if (!likeAnimations.containsKey(holder)) {
                holder.vBgLike.setVisibility(View.VISIBLE);

                holder.vBgLike.setScaleY(0.1f);
                holder.vBgLike.setScaleX(0.1f);
                holder.vBgLike.setAlpha(1f);

                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(holder, animatorSet);

                ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(holder.vBgLike, "scaleY", 0.1f, 1f);
                bgScaleYAnim.setDuration(200);
                bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
                ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(holder.vBgLike, "scaleX", 0.1f, 1f);
                bgScaleXAnim.setDuration(200);
                bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
                ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(holder.vBgLike, "alpha", 1f, 0f);
                bgAlphaAnim.setDuration(200);
                bgAlphaAnim.setStartDelay(150);
                bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

                animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resetLikeAnimationState(holder, product);
                    }
                });
                animatorSet.start();
            }
        }

        private void resetLikeAnimationState(ViewHolder holder, Product product) {
            likeAnimations.remove(holder);
            holder.vBgLike.setVisibility(View.GONE);
            callProductDetailActivity(holder,product);

        }

        private void callProductDetailActivity(ViewHolder holder, Product product ) {
            Context context = holder.mView.getContext();
            Intent intent = new Intent(context, ProductDetailActivty.class);
            if(product != null)
            intent.putExtra(ProductDetailActivty.PARAM_PRODUCT,product);
            context.startActivity(intent);
        }



        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}

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
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

        private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();

        private  final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
        private  final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
        private  final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;



            public final View mView;
            public final ImageView imImage;
            public final TextView tvProductTiltle;

            public final View vBgLike;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                imImage = (ImageView) view.findViewById(R.id.imImage);
                tvProductTiltle = (TextView) view.findViewById(R.id.tvProductTiltle);
                vBgLike  = (View) view.findViewById(R.id.vBgLike);
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
                    .inflate(R.layout.item_product_holder, parent, false);
            //view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Product product = mValues.get(position);

            holder.tvProductTiltle.setText(product.product_name);
            Log.e("Adapeter","---------------------------------");
            Log.e("Adapeter","Product ID:" + product.product_id);
            Log.e("Adapeter","Product Name:" + product.product_name);
            if(product.image_urls != null) {
                if( product.image_urls.size() >= 1) {
                    String url = product.image_urls.get(0);

                    Log.e("Adapeter","URL" + url);

                    Glide.with(holder.imImage.getContext())
                            .load(url)
                            .into(holder.imImage);
                }
            } else {
                Log.e("Adapeter","Images are NULL");
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateRipple(holder);
//                    Toast.makeText(v.getContext(),"Clicked!!", Toast.LENGTH_LONG).show();
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CheeseDetailActivity.class);
//                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);
//                    onClickAnimation(v);

                }
            });


        }

        private void animateRipple(final ViewHolder holder) {
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
                        resetLikeAnimationState(holder);
                    }
                });
                animatorSet.start();
            }
        }

        private void resetLikeAnimationState(ViewHolder holder) {
            likeAnimations.remove(holder);
            holder.vBgLike.setVisibility(View.GONE);
        }


        public void onClickAnimation( final View view) {

            int originalHeight = 0;
            boolean mIsViewExpanded = false;
            // If the originalHeight is 0 then find the height of the View being used
            // This would be the height of the cardview
            if (originalHeight == 0) {
                originalHeight = view.getHeight();
            }

            // Declare a ValueAnimator object
            ValueAnimator valueAnimator;
            if (!mIsViewExpanded) {
                view.setVisibility(View.VISIBLE);
                view.setEnabled(true);
                mIsViewExpanded = true;
                valueAnimator = ValueAnimator.ofInt(originalHeight, originalHeight + (int) (originalHeight * 2.0)); // These values in this method can be changed to expand however much you like
            } else {
                mIsViewExpanded = false;
                valueAnimator = ValueAnimator.ofInt(originalHeight + (int) (originalHeight * 2.0), originalHeight);

                Animation a = new AlphaAnimation(1.00f, 0.00f); // Fade out

                a.setDuration(200);
                // Set a listener to the animation and configure onAnimationEnd
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(View.INVISIBLE);
                        view.setEnabled(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // Set the animation on the custom view
                view.startAnimation(a);
            }
            valueAnimator.setDuration(200);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    view.getLayoutParams().height = value.intValue();
                    view.requestLayout();
                }
            });


            valueAnimator.start();
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}

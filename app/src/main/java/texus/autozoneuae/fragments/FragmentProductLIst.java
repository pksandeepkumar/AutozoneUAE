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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import texus.autozoneuae.ApplicationClass;
import texus.autozoneuae.R;
import texus.autozoneuae.datamodels.CatData;
import texus.autozoneuae.network.NetworkService;
import texus.autozoneuae.preferance.SavedPreferance;
import texus.autozoneuae.utility.Utility;

public class FragmentProductLIst extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    CatData catData = null;

    public static final String PARAM_CAT_DATA = "ParamCategoryData";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(
                R.layout.fragment_product_list, container, false);

//        catData = ( CatData ) getArguments().get(PARAM_CAT_DATA);
        initViews(view);

        LoadProducts task = new LoadProducts(getActivity());
        task.execute();



        return view;
    }

    private void initViews(View view) {

        viewPager       = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout       = (TabLayout) view.findViewById(R.id.tabs);


    }

    public void populateProductList( ArrayList<CatData> catDatas) {
        Log.e("XXXXX"," ON populateProductList..............................");
        if( catDatas == null) return;
        if( catDatas.size() == 0) return;

        Adapter adapter = new Adapter(getFragmentManager());

        for(CatData catData : catDatas) {
            addFragment(adapter, catData);
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void addFragment(Adapter adapter, CatData object ) {
        if(object == null) return;

        ProductsFragmentList fragment = new ProductsFragmentList();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ProductsFragment.PARAM_CAT_DATA, object);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, object.cat_name);

    }

    class Adapter extends FragmentPagerAdapter {
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
            populateProductList(catDatas);
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
                Toast.makeText(context,"Something went wrong. " +
                        "Please try later!!", Toast.LENGTH_LONG).show();
            }
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

//    public static class ProductRecycleAdapter
//            extends RecyclerView.Adapter<ProductRecycleAdapter.ViewHolder> {
//
//        private final TypedValue mTypedValue = new TypedValue();
//        private int mBackground;
//        private ArrayList<Product> mValues;
//
//        private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
//
//        private  final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
//        private  final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
//        private  final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
//
//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            public String mBoundString;
//
//
//
//            public final View mView;
//            public final ImageView imImage;
//            public final TextView tvProductTiltle;
//            public final CardView card_view;
//
//            public final View vBgLike;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                imImage = (ImageView) view.findViewById(R.id.imImage);
//                tvProductTiltle = (TextView) view.findViewById(R.id.tvProductTiltle);
//                vBgLike  = (View) view.findViewById(R.id.vBgLike);
//                card_view  = (CardView) view.findViewById(R.id.card_view);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + tvProductTiltle.getText();
//            }
//        }
//
//        public Product getValueAt(int position) {
//            return mValues.get(position);
//        }
//
//        public ProductRecycleAdapter(Context context, ArrayList<Product> products) {
//            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//            mBackground = mTypedValue.resourceId;
//            mValues = products;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_product_holder, parent, false);
//            //view.setBackgroundResource(mBackground);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//
//            final Product product = mValues.get(position);
//
//            holder.tvProductTiltle.setText(product.product_name);
//
//            LinearLayout.LayoutParams rlp = (LinearLayout.LayoutParams)
//                    holder.card_view.getLayoutParams();
////            rlp.height = product.card_height;
//            rlp.height =   ApplicationClass.getInstance().getHeight();;
//            holder.card_view.setLayoutParams(rlp);
//
//
//            Log.e("Adapeter","----------------1-----------------" + rlp.height);
//            Log.e("Adapeter","Product ID:" + product.product_id);
//            Log.e("Adapeter","Product Name:" + product.product_name);
//            if(product.image_urls != null) {
//                if( product.image_urls.size() >= 1) {
//                    String url = product.image_urls.get(0);
//
//                    Log.e("Adapeter","URL" + url);
//
//
//
//                    Glide.with(holder.imImage.getContext())
//                            .load(url)
//                            .placeholder(R.drawable.loading_image)
//                            .into(holder.imImage);
//                }
//            } else {
//                Log.e("Adapeter","Images are NULL");
//            }
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    animateRipple(holder, product);
//                }
//            });
//
//
//        }
//
//        //This will show ripple animation and navigate to product detail activity after
//        // animation complete
//        private void animateRipple(final ViewHolder holder, final Product product) {
//            if (!likeAnimations.containsKey(holder)) {
//                holder.vBgLike.setVisibility(View.VISIBLE);
//
//                holder.vBgLike.setScaleY(0.1f);
//                holder.vBgLike.setScaleX(0.1f);
//                holder.vBgLike.setAlpha(1f);
//
//                AnimatorSet animatorSet = new AnimatorSet();
//                likeAnimations.put(holder, animatorSet);
//
//                ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(holder.vBgLike, "scaleY", 0.1f, 1f);
//                bgScaleYAnim.setDuration(200);
//                bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//                ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(holder.vBgLike, "scaleX", 0.1f, 1f);
//                bgScaleXAnim.setDuration(200);
//                bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//                ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(holder.vBgLike, "alpha", 1f, 0f);
//                bgAlphaAnim.setDuration(200);
//                bgAlphaAnim.setStartDelay(150);
//                bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
//
//                animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim);
//
//                animatorSet.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        resetLikeAnimationState(holder, product);
//                    }
//                });
//                animatorSet.start();
//            }
//        }
//
//        private void resetLikeAnimationState(ViewHolder holder, Product product) {
//            likeAnimations.remove(holder);
//            holder.vBgLike.setVisibility(View.GONE);
//            callProductDetailActivity(holder,product);
//
//        }
//
//        private void callProductDetailActivity(ViewHolder holder, Product product ) {
//            Context context = holder.mView.getContext();
//            Intent intent = new Intent(context, ProductDetailActivty.class);
//            if(product != null)
//            intent.putExtra(ProductDetailActivty.PARAM_PRODUCT,product);
//            context.startActivity(intent);
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//    }
}

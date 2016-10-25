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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import texus.autozoneuae.R;
import texus.autozoneuae.adapter.ProductRecycleAdapter;
import texus.autozoneuae.datamodels.Product;

public class FragmentHome extends BaseFragment implements ProductRecycleAdapter.OnProductClickListener{

    public static FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_home, container, false);
        fragmentManager = getChildFragmentManager();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        addFragment(new FragmentProductLIst());
    }

    public static void addFragment( Fragment fragment) {
        Log.e("BaseFragment","addFragment");
        if(fragment == null) return;
        try {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frFragmentContainerHome, fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onProductClick(Product product) {
        if(product == null) return;
        FragmentProductDetails fragment = new FragmentProductDetails();
        Bundle bundle=new Bundle();
        bundle.putParcelable(FragmentProductDetails.PARAM_PRODUCT_DATA, product);
        fragment.setArguments(bundle);
        addFragment(fragment);

    }
}

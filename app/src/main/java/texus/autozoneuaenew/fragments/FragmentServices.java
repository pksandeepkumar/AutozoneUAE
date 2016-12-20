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

package texus.autozoneuaenew.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import texus.autozoneuaenew.R;
import texus.autozoneuaenew.controls.ServiceText;
import texus.autozoneuaenew.controls.ServiceTextLine;

public class FragmentServices extends Fragment {

    LinearLayout llHolder;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_services, container, false);

        context = getActivity();

        llHolder = (LinearLayout) view.findViewById(R.id.llHolder);

        llHolder.addView(new ServiceText( context, "Armored Vehicle Manufacturing"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Ambulance & Mobile Clinic Conversion"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Humanitarian Mission Vehicle Conversion"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Mining Vehicle Conversion"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Military & Police Vehicle Modifications"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Hunter & Adventure Vehicle Modifications"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Special Purpose Customized Vehicles"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Brand New Cars"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Armored Vehicle Parts"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Genuine Automobile Spare Parts"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "4X4 Equipment"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "On & Off Road Accessories"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Camping & Recovery Products"));
        llHolder.addView( new ServiceTextLine(context));
        llHolder.addView(new ServiceText( context, "Ambulance Équipment"));


//        setupRecyclerView(rv);
        return view;
    }


}
